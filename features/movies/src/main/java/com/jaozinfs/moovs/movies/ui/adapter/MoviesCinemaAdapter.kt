package com.jaozinfs.moovs.movies.ui.adapter

import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jaozinfs.moovs.extensions.lazyFindView
import com.jaozinfs.moovs.extensions.loadImageCoil
import com.jaozinfs.moovs.extensions.setClickListener
import com.jaozinfs.moovs.movies.R
import com.jaozinfs.moovs.movies.data.network.BASE_BACKDROP_IMAGE_PATTER
import com.jaozinfs.moovs.movies.domain.movies.MovieUi
import com.jaozinfs.moovs.movies.ui.fragments.MovieCinemaDetails
import com.jaozinfs.moovs.ui.view.RatingView
import java.util.concurrent.atomic.AtomicBoolean


class MoviesCinemaAdapter() :
    ListAdapter<MovieUi, MoviesCinemaAdapter.MoviesViewHolder>(
        MoviesDiffUtils
    ) {
    private var clickListener: ((Int, MovieUi, View) -> Unit)? = null

    private var motionEvent: MotionLayout.TransitionListener? = null

    inner class MoviesViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val imageview by view.lazyFindView<ImageView>(R.id.image_view)
        val rating by view.lazyFindView<RatingView>(R.id.rating)
        val atomicBoolean = AtomicBoolean(true)

        fun bind(moviesEntity: MovieUi, position: Int) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.transitionName =
                    "${MovieCinemaDetails.ROOT_VIEW_CONTAINER_TRANSITION_NAME}-${moviesEntity.id}"
            }

            view.setClickListener {
                clickListener?.invoke(
                    position,
                    moviesEntity,
                    view
                )
            }
            LocationManager.PASSIVE_PROVIDER
            rating.setPercent(moviesEntity.vote_average.toFloat(), atomicBoolean.getAndSet(false))

            val uri = Uri.parse(BASE_BACKDROP_IMAGE_PATTER)
                .buildUpon()
                .appendEncodedPath(moviesEntity.backdrop_path)
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
                R.layout.movie_cinema_item,
                parent,
                false
            )
        )
    }

    fun setMovieClickListener(clickListener: (Int, MovieUi, root: View) -> Unit) {
        this@MoviesCinemaAdapter.clickListener = clickListener
    }


}

