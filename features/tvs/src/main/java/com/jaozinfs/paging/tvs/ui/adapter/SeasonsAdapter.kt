package com.jaozinfs.paging.tvs.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jaozinfs.paging.extensions.lazyFindView
import com.jaozinfs.paging.tvs.R
import com.jaozinfs.paging.tvs.domain.model.SeasonUI


class SeasonsAdapter(private val clickListener: (Int) -> Unit) :
    ListAdapter<SeasonUI, SeasonsAdapter.SeasonsViewHolder>(
        SeasonDiffUtils
    ) {


    inner class SeasonsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val titleTv by view.lazyFindView<TextView>(R.id.title)
        private val timeTv by view.lazyFindView<TextView>(R.id.time)

        fun bind(seasonUI: SeasonUI) {

            titleTv.text = seasonUI.name
            timeTv.text = seasonUI.episode_count.toString()

        }
    }

    private object SeasonDiffUtils : DiffUtil.ItemCallback<SeasonUI>() {
        override fun areItemsTheSame(oldItem: SeasonUI, newItem: SeasonUI): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: SeasonUI, newItem: SeasonUI): Boolean =
            oldItem == newItem
    }

    override fun onBindViewHolder(holder: SeasonsViewHolder, position: Int) {

        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonsViewHolder {
        return SeasonsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_tvs_seasons,
                parent,
                false
            )
        )
    }

}

