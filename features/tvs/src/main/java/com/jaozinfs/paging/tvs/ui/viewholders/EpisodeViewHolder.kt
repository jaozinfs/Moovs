package com.jaozinfs.paging.tvs.ui.viewholders

import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.jaozinfs.paging.extensions.loadImageCoil
import com.jaozinfs.paging.tvs.data.BASE_BACKDROP_IMAGE_PATTER
import com.jaozinfs.paging.tvs.domain.model.EpisodeUI
import com.jaozinfs.paging.tvs.ui.fragments.FragmentTvEpisodeDetails
import com.jaozinfs.paging.utils.UriUtils
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_tvs_seasons_episode.*

class EpisodeViewHolder(val view: View) : RecyclerView.ViewHolder(view), LayoutContainer {

    override val containerView: View?
        get() = view

    fun bind(episodeUI: EpisodeUI, clickListener: (ConstraintLayout, ImageView, TextView, TextView) -> Unit) {
        view.setOnClickListener { clickListener.invoke(root, backdrop, title, overview) }
        //set transitions names
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            title.transitionName =
                "${FragmentTvEpisodeDetails.TITLE_TRANSITION_NAME}-${episodeUI.id}"
            overview.transitionName =
                "${FragmentTvEpisodeDetails.OVERVIEW_TRANSITION_NAME}-${episodeUI.id}"
            backdrop.transitionName =
                "${FragmentTvEpisodeDetails.BACKDROP_TRANSITION_NAME}-${episodeUI.id}"
            root.transitionName =
                "${FragmentTvEpisodeDetails.ROOT_TRANSITION_NAME}-${episodeUI.id}"
        }

        title.text = episodeUI.name
        overview.text = episodeUI.overview

        episodeUI.still_path?.let {
            backdrop.loadImageCoil {
                uri =
                    UriUtils.getUriFromBaseAndBackdrop(BASE_BACKDROP_IMAGE_PATTER, it)
            }
        }

    }

}