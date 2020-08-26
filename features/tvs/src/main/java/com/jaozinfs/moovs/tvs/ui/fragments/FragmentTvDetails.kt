package com.jaozinfs.moovs.tvs.ui.fragments

import android.animation.ObjectAnimator
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.animation.doOnStart
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionManager
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen
import com.ethanhua.skeleton.Skeleton
import com.jaozinfs.moovs.extensions.loadImageCoil
import com.jaozinfs.moovs.tvs.R
import com.jaozinfs.moovs.tvs.data.BASE_BACKDROP_IMAGE_PATTER
import com.jaozinfs.moovs.tvs.databinding.FragmentTvDetailsBinding
import com.jaozinfs.moovs.tvs.domain.model.TvDetailsUI
import com.jaozinfs.moovs.tvs.ui.adapter.SeasonsAdapter
import com.jaozinfs.moovs.tvs.ui.viewmodels.TvsViewModel
import com.jaozinfs.moovs.utils.MotionTransitionListener
import kotlinx.android.synthetic.main.fragment_tv_details.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel


class FragmentTvDetails : Fragment() {
    private val tvsViewModel: TvsViewModel by viewModel()
    private val args: FragmentTvDetailsArgs by navArgs()

    private var saveFavoriteJob: Job? = null
    private var removeFavoriteJob: Job? = null
    private lateinit var tvDetailsUI: TvDetailsUI
    lateinit var binding: FragmentTvDetailsBinding
    private lateinit var skeletonScreen: RecyclerViewSkeletonScreen


    private val adapter = SeasonsAdapter { seasonId ->
        findNavController()
            .navigate(
                FragmentTvDetailsDirections.actionNavTvDetailsToNavSeasonDetails(args.tvID, seasonId)
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTvDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private val listener = MotionTransitionListener { animation ->
        TransitionManager.beginDelayedTransition(fab_full_btn)
        binding.chatFabText.isVisible = animation != R.id.end
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        observeLiveData()
        fragment_tv_details_motion_container.addTransitionListener(listener)

        lifecycleScope.launch {
            tvsViewModel.getTvDetails(args.tvID).collect {
                constructDetails(it)
            }

        }
    }

    private fun constructDetails(tvDetailsUI: TvDetailsUI) {
        this@FragmentTvDetails.tvDetailsUI = tvDetailsUI
        val uri = Uri.parse(BASE_BACKDROP_IMAGE_PATTER)
            .buildUpon()
            .appendEncodedPath(tvDetailsUI.poster_path)
            .build()

        binding.backdrop.loadImageCoil(uri)
        binding.titleTv.text = tvDetailsUI.name
        adapter.submitList(tvDetailsUI.seasons)
    }

    private fun setupList() {
        skeletonScreen = Skeleton.bind(seasons_rv)
            .adapter(adapter)
            .load(R.layout.item_tvs_seasons_loading)
            .show()
        seasons_rv.adapter = adapter
    }

    private fun observeLiveData() {
        tvsViewModel.observeTvIsFavorite(args.tvID)
            .observe(viewLifecycleOwner, Observer { favorite ->
                binding.favoriteButtonClickListener = getListenerFavoriteButtonFromState(favorite)
                changeFabText(getLabelFromFavoriteState(favorite))
            })
    }


    private fun getLabelFromFavoriteState(favorited: Boolean): String =
        getString(if (favorited) R.string.label_unfavorite else R.string.label_favorite)


    private fun getListenerFavoriteButtonFromState(favorite: Boolean) = View.OnClickListener {
        disableFabButton()
        when (favorite) {
            true -> {
                removeTvFavorite()
            }
            else -> {
                saveTvFavorite()
            }
        }
    }

    private fun saveTvFavorite() {
        saveFavoriteJob?.cancel()
        saveFavoriteJob = lifecycleScope.launch {
            tvsViewModel.saveTvFavorite(
                tvDetailsUI
            ).collect {
                enableFabButton()
                Toast.makeText(context, getString(R.string.msg_favorite_tv), Toast.LENGTH_LONG)
                    .show()
            }

        }
    }

    private fun removeTvFavorite() {
        removeFavoriteJob?.cancel()
        removeFavoriteJob = lifecycleScope.launch {
            tvsViewModel.removeTvFavorite(args.tvID).collect {
                enableFabButton()
                Toast.makeText(context, getString(R.string.msg_unfavorite_tv), Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun changeFabText(text: String) {
        ObjectAnimator.ofFloat(binding.chatFabText, "alpha", 0f, 1f).apply {
            duration = 500
            doOnStart { binding.chatFabText.text = text }
        }.start()
    }

    private fun disableFabButton() {
        fab_full_btn.isEnabled = false
    }

    private fun enableFabButton() {
        fab_full_btn.isEnabled = true
    }
}