package com.jaozinfs.paging.tvs.ui.fragments

import android.os.Build
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.jaozinfs.paging.extensions.loadImageCoil
import com.jaozinfs.paging.tvs.R
import com.jaozinfs.paging.tvs.data.BASE_BACKDROP_IMAGE_PATTER
import com.jaozinfs.paging.tvs.domain.model.EpisodeUI
import com.jaozinfs.paging.tvs.ui.viewmodels.TvsViewModel
import com.jaozinfs.paging.utils.UriUtils
import com.jaozinfs.paging.utils.formatterDateBrazil
import kotlinx.android.synthetic.main.fragment_episode_details.*
import kotlinx.coroutines.flow.collect
import org.koin.android.viewmodel.ext.android.viewModel


class FragmentTvEpisodeDetails : Fragment(R.layout.fragment_episode_details) {

    companion object {
        const val TITLE_TRANSITION_NAME = "titleTransition"
        const val OVERVIEW_TRANSITION_NAME = "overviewTransition"
        const val BACKDROP_TRANSITION_NAME = "backdropTransition"
        const val ROOT_TRANSITION_NAME = "rootTransition"
    }

    private val tvsViewModel: TvsViewModel by viewModel()
    private val args: FragmentTvEpisodeDetailsArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            enterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
            sharedElementEnterTransition = MaterialContainerTransform()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()


        lifecycleScope.launchWhenCreated {
            tvsViewModel.getEpisodeDetails(args.tvID, args.seasonID, args.episodeID).collect {
                loadEpisodeDetails(it)
            }
        }
    }

    private fun loadEpisodeDetails(episodeUI: EpisodeUI) {
        episodeUI.still_path?.let {
            backdrop.loadImageCoil(
                UriUtils.getUriFromBaseAndBackdrop(
                    BASE_BACKDROP_IMAGE_PATTER,
                    it
                )
            ) {
                title.text = episodeUI.name
                overview.text = episodeUI.overview
                air_date.text = episodeUI.air_date.formatterDateBrazil
                startPostponedEnterTransition()
            }
        }

    }


}