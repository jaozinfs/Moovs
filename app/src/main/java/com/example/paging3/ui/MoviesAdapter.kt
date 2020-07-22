package com.example.paging3.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.paging3.R
import com.example.paging3.data.repository.local.entities.MovieEntity

class MoviesAdapter :
    PagingDataAdapter<MovieEntity, MoviesAdapter.MoviesViewHolder>(MoviesDiffUtils) {


    class MoviesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind() {

        }
    }

    private object MoviesDiffUtils : DiffUtil.ItemCallback<MovieEntity>() {
        override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean =
            oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean =
            oldItem == newItem
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        //.
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.movies_item,
                parent,
                false
            )
        )
    }
}