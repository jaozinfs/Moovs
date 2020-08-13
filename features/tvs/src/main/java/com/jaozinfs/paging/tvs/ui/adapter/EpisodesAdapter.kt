package com.jaozinfs.paging.tvs.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.jaozinfs.paging.tvs.R
import com.jaozinfs.paging.tvs.domain.model.EpisodeUI
import com.jaozinfs.paging.tvs.ui.viewholders.EpisodeViewHolder

class EpisodesAdapter(private val clickListener: (Int, ConstraintLayout, ImageView, TextView, TextView) -> Unit) :
    ListAdapter<EpisodeUI, EpisodeViewHolder>(
        SeasonDiffUtils
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        return EpisodeViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_tvs_seasons_episode,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        getItem(position)?.let { episodeUI ->
            holder.bind(episodeUI) { root, imv, tv, tv2 ->
                clickListener.invoke(episodeUI.episode_number, root, imv, tv, tv2)
            }
        }
    }


    private object SeasonDiffUtils : DiffUtil.ItemCallback<EpisodeUI>() {
        override fun areItemsTheSame(oldItem: EpisodeUI, newItem: EpisodeUI): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: EpisodeUI, newItem: EpisodeUI): Boolean =
            oldItem == newItem
    }
}

