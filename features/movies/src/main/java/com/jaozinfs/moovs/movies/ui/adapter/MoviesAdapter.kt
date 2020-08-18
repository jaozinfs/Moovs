package com.jaozinfs.moovs.movies.ui.adapter

import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jaozinfs.moovs.database.local.entities.MovieEntity
import com.jaozinfs.moovs.extensions.lazyFindView
import com.jaozinfs.moovs.extensions.loadImageCoil
import com.jaozinfs.moovs.extensions.setClickListener
import com.jaozinfs.moovs.movies.R
import com.jaozinfs.moovs.movies.data.network.BASE_BACKDROP_IMAGE_PATTER
import com.jaozinfs.moovs.movies.ui.fragments.MovieDetailFragment
import com.jaozinfs.moovs.ui.view.RatingView
import java.util.concurrent.atomic.AtomicBoolean


class MoviesAdapter() :
    PagingDataAdapter<MovieEntity, MoviesAdapter.MoviesViewHolder>(
        MoviesDiffUtils
    ) {
    private var clickListener: ((Int, MovieEntity, ImageView, RatingView) -> Unit)? = null


    inner class MoviesViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val imageview by view.lazyFindView<ImageView>(R.id.image_view)
        val rating by view.lazyFindView<RatingView>(R.id.rating)

        val atomicBoolean = AtomicBoolean(true)

        fun bind(moviesEntity: MovieEntity, position: Int) {
            //set views transition name of each different view
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                imageview.transitionName =
                    "${MovieDetailFragment.BANNER_ENTER_TRANSITION_NAME}-popular-${moviesEntity.id}"
                rating.transitionName =
                    "${MovieDetailFragment.RATING_ENTER_TRANSITION_NAME}-popular-${moviesEntity.id}"
            }

            view.setClickListener {
                clickListener?.invoke(
                    position,
                    moviesEntity,
                    imageview,
                    rating
                )
            }
            rating.setPercent(moviesEntity.vote_average.toFloat(), atomicBoolean.getAndSet(false))
            Uri.parse(BASE_BACKDROP_IMAGE_PATTER)
                .buildUpon()
                .appendEncodedPath(moviesEntity.poster_path)
                .build().apply {
                    imageview.loadImageCoil{
                        uri = this@apply
                        corners = true
                        cornersRadius = 14f
                    }
                }
        }
    }

    private object MoviesDiffUtils : DiffUtil.ItemCallback<MovieEntity>() {
        override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean =
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

    fun setMovieClickListener(clickListener: (Int, MovieEntity, ImageView, RatingView) -> Unit) {
        this@MoviesAdapter.clickListener = clickListener
    }
}


class ReposLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<ReposLoadStateAdapter.ReposLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: ReposLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ReposLoadStateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_footer, parent, false)
        return ReposLoadStateViewHolder(view, retry)
    }

    inner class ReposLoadStateViewHolder(
        private val view: View,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(view) {
        private val retryButton by view.lazyFindView<Button>(R.id.retry_button)
        private val progressBar by view.lazyFindView<ProgressBar>(R.id.progress_bar)
        private val errorMsg by view.lazyFindView<TextView>(R.id.error_msg)

        init {
            retryButton.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                errorMsg.text = loadState.error.localizedMessage
            }
            progressBar.isVisible = loadState is LoadState.Loading
            retryButton.isVisible = loadState !is LoadState.Loading
            errorMsg.isVisible = loadState !is LoadState.Loading
        }


    }
}