package com.jaozinfs.paging.tvs.ui.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionManager
import com.jaozinfs.paging.extensions.loadImageCoil
import com.jaozinfs.paging.tvs.R
import com.jaozinfs.paging.tvs.data.BASE_BACKDROP_IMAGE_PATTER
import com.jaozinfs.paging.tvs.databinding.FragmentTvDetailsBinding
import com.jaozinfs.paging.tvs.domain.model.TvDetailsUI
import com.jaozinfs.paging.tvs.ui.adapter.SeasonsAdapter
import com.jaozinfs.paging.tvs.ui.viewmodels.TvsViewModel
import com.jaozinfs.paging.utils.MotionTransitionListener
import kotlinx.android.synthetic.main.fragment_tv_details.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel


class FragmentTvDetails : Fragment() {
    private val tvsViewModel: TvsViewModel by viewModel()
    private val args: FragmentTvDetailsArgs by navArgs()
    private val adapter = SeasonsAdapter {}
    private var saveFavoriteJob: Job? = null
    private var removeFavoriteJob: Job? = null
    private lateinit var tvDetailsUI: TvDetailsUI
    private var favorited: Boolean = false
    lateinit var binding: FragmentTvDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTvDetailsBinding.inflate(layoutInflater, container, false).apply {

        }
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
        //load image
        val uri = Uri.parse(BASE_BACKDROP_IMAGE_PATTER)
            .buildUpon()
            .appendEncodedPath(tvDetailsUI.poster_path)
            .build()

        binding.backdrop.loadImageCoil(uri)
        binding.titleTv.text = tvDetailsUI.name
        //update adapter
        adapter.submitList(tvDetailsUI.seasons)
    }

    private fun setupList() {
        seasons_rv.adapter = adapter
    }

    private fun observeLiveData() {
        tvsViewModel.observeTvIsFavorite(args.tvID)
            .observe(viewLifecycleOwner, Observer { favorite ->
                favorited = favorite
                binding.fabFullBtn.setOnClickListener(getListenerFavoriteButtonFromState(favorite))
                binding.chatFabText.text = getLabelFromFavoriteState(favorite)
            })
    }


    private fun getLabelFromFavoriteState(favorited: Boolean): String =
        getString(if (favorited) R.string.label_unfavorite else R.string.label_favorite)


    private fun getListenerFavoriteButtonFromState(favorite: Boolean) = View.OnClickListener {
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
                Toast.makeText(context, getString(R.string.msg_favorite_tv), Toast.LENGTH_LONG)
                    .show()
            }

        }
    }

    private fun removeTvFavorite() {
        removeFavoriteJob?.cancel()
        removeFavoriteJob = lifecycleScope.launch {
            tvsViewModel.removeTvFavorite(args.tvID).collect {
                Toast.makeText(context, getString(R.string.msg_unfavorite_tv), Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

}