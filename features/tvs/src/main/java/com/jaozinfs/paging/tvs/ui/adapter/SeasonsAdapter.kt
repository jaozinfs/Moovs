package com.jaozinfs.paging.tvs.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.jaozinfs.paging.tvs.R
import com.jaozinfs.paging.tvs.domain.model.SeasonUI
import com.jaozinfs.paging.tvs.ui.viewholders.SeasonViewHolder

class SeasonsAdapter(private val clickListener: (Int) -> Unit) :
    ListAdapter<SeasonUI, SeasonViewHolder>(
        SeasonDiffUtils
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonViewHolder {
        return SeasonViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_tvs_seasons,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SeasonViewHolder, position: Int) {
        getItem(position)?.let { seasonUI ->
            holder.bind(seasonUI)
            holder.view.setOnClickListener {
                clickListener.invoke(seasonUI.season_number)
            }
        }
    }


    private object SeasonDiffUtils : DiffUtil.ItemCallback<SeasonUI>() {
        override fun areItemsTheSame(oldItem: SeasonUI, newItem: SeasonUI): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: SeasonUI, newItem: SeasonUI): Boolean =
            oldItem == newItem
    }
}

