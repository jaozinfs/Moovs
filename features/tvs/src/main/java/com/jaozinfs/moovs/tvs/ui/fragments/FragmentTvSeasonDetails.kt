package com.jaozinfs.moovs.tvs.ui.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jaozinfs.moovs.extensions.loadImageCoil
import com.jaozinfs.moovs.tvs.R
import com.jaozinfs.moovs.tvs.data.BASE_BACKDROP_IMAGE_PATTER
import com.jaozinfs.moovs.tvs.databinding.FragmentSeasonDetailsBinding
import com.jaozinfs.moovs.tvs.domain.model.SeasonDetailsUI
import com.jaozinfs.moovs.tvs.ui.adapter.EpisodesAdapter
import com.jaozinfs.moovs.tvs.ui.viewmodels.TvsViewModel
import com.jaozinfs.moovs.utils.UriUtils
import kotlinx.android.synthetic.main.fragment_season_details.*
import kotlinx.coroutines.flow.collect
import org.koin.android.viewmodel.ext.android.viewModel


class FragmentTvSeasonDetails : Fragment() {
    private val tvsViewModel: TvsViewModel by viewModel()
    private val args: FragmentTvSeasonDetailsArgs by navArgs()
    private lateinit var fragmentSeasonDetailsBinding: FragmentSeasonDetailsBinding

    private val adapter = EpisodesAdapter { episodeID, root, backDrop, title, overView ->
        val extras = FragmentNavigatorExtras(
            root to FragmentTvEpisodeDetails.ROOT_TRANSITION_NAME,
            backDrop to FragmentTvEpisodeDetails.BACKDROP_TRANSITION_NAME,
            title to FragmentTvEpisodeDetails.TITLE_TRANSITION_NAME,
            overView to FragmentTvEpisodeDetails.OVERVIEW_TRANSITION_NAME
        )
        findNavController()
            .navigate(
                FragmentTvSeasonDetailsDirections.actionNavSeasonDetailsToNavEpisodeDetails(
                    args.tvID,
                    args.seasonID,
                    episodeID
                ),
                extras
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentSeasonDetailsBinding = FragmentSeasonDetailsBinding.inflate(inflater, container, false)
        return fragmentSeasonDetailsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setShadow()
        setAdapter()


        lifecycleScope.launchWhenCreated {
            tvsViewModel.getSeasonEpisodes(args.tvID, args.seasonID).collect {
                loadEpisodeDetails(it)
                adapter.submitList(it.episodeResponses)
            }
        }
    }

    private fun loadEpisodeDetails(seasonDetailsUI: SeasonDetailsUI) {
        season_title.text = seasonDetailsUI.name
        seasonDetailsUI.poster_path?.let {
            backdrop.loadImageCoil {
                uri = UriUtils.getUriFromBaseAndBackdrop(BASE_BACKDROP_IMAGE_PATTER, it)
            }
        }
    }

    private fun setAdapter() {
        episodes_rv.adapter = adapter
    }

    private fun setShadow() {
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO ->
                fragmentSeasonDetailsBinding.themeDark = false
            Configuration.UI_MODE_NIGHT_YES ->
                fragmentSeasonDetailsBinding.themeDark = true
        }
    }
}