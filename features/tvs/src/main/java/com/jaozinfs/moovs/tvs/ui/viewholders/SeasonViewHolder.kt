package com.jaozinfs.moovs.tvs.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jaozinfs.moovs.tvs.domain.model.SeasonUI
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_tvs_seasons.*

class SeasonViewHolder(val view: View) : RecyclerView.ViewHolder(view), LayoutContainer {
    fun bind(seasonUI: SeasonUI) {
        title.text = seasonUI.name
        time.text = seasonUI.episode_count.toString()
    }
    override val containerView: View?
        get() = view
}