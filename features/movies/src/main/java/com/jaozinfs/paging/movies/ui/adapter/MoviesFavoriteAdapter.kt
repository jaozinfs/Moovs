package com.jaozinfs.paging.movies.ui.adapter

import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jaozinfs.paging.movies.R
import com.jaozinfs.paging.movies.data.network.BASE_BACKDROP_IMAGE_PATTER
import com.jaozinfs.paging.movies.domain.movies.MovieUi
import com.jaozinfs.paging.ui.lazyFindView
import com.jaozinfs.paging.ui.loadImageCoil
import com.jaozinfs.paging.ui.setClickListener
import com.jaozinfs.paging.ui.view.RatingView
import java.util.concurrent.atomic.AtomicBoolean


class MoviesFavoriteAdapter() :
    ListAdapter<MovieUi, MoviesFavoriteAdapter.MoviesViewHolder>(
        MoviesDiffUtils
    ) {
    private var clickListener: ((Int, MovieUi, ImageView, RatingView) -> Unit)? = null


    inner class MoviesViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val imageview by view.lazyFindView<ImageView>(R.id.image_view)
        val rating by view.lazyFindView<RatingView>(R.id.rating)

        val atomicBoolean = AtomicBoolean(true)

        fun bind(moviesEntity: MovieUi, position: Int) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                imageview.transitionName = moviesEntity.backdrop_path
                rating.transitionName = moviesEntity.poster_path
            }

            view.setClickListener {
                clickListener?.invoke(
                    position,
                    moviesEntity,
                    imageview,
                    rating
                )
            }
            LocationManager.PASSIVE_PROVIDER
            rating.setPercent(moviesEntity.vote_average.toFloat(), atomicBoolean.getAndSet(false))

            val uri = Uri.parse(BASE_BACKDROP_IMAGE_PATTER)
                .buildUpon()
                .appendEncodedPath(moviesEntity.poster_path)
                .build()

            imageview.loadImageCoil(uri)
        }
    }

    private object MoviesDiffUtils : DiffUtil.ItemCallback<MovieUi>() {
        override fun areItemsTheSame(oldItem: MovieUi, newItem: MovieUi): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MovieUi, newItem: MovieUi): Boolean =
            oldItem == newItem
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {

        getItem(position)?.let { holder.bind(it, position) }
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

    fun setMovieClickListener(clickListener: (Int, MovieUi, ImageView, RatingView) -> Unit) {
        this@MoviesFavoriteAdapter.clickListener = clickListener
    }
}

