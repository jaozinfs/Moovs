package com.jaozinfs.paging.tvs.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jaozinfs.paging.extensions.loadImageCoil
import com.jaozinfs.paging.tvs.R
import com.jaozinfs.paging.tvs.data.BASE_BACKDROP_IMAGE_PATTER
import com.jaozinfs.paging.tvs.domain.model.SeasonDetailsUI
import com.jaozinfs.paging.tvs.ui.adapter.EpisodesAdapter
import com.jaozinfs.paging.tvs.ui.viewmodels.TvsViewModel
import com.jaozinfs.paging.utils.UriUtils
import kotlinx.android.synthetic.main.fragment_season_details.*
import kotlinx.coroutines.flow.collect
import org.koin.android.viewmodel.ext.android.viewModel


class FragmentTvSeasonDetails : Fragment(R.layout.fragment_season_details) {
    private val tvsViewModel: TvsViewModel by viewModel()
    private val args: FragmentTvSeasonDetailsArgs by navArgs()
    private var scrollPosition: Int = 0

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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      
        setAdapter()

        if(scrollPosition > 0 )
            item_tvs_season_details_container.transitionToEnd()

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

}