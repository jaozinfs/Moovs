package com.example.paging.movies.ui.adapter

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
import com.example.paging.movies.R
import com.example.paging.movies.data.local.entities.MovieEntity
import com.example.paging.movies.data.network.BASE_BACKDROP_IMAGE_PATTER
import com.example.paging.ui.lazyFindView
import com.example.paging.ui.loadImageUrl
import com.example.paging.ui.setClickListener
import com.example.paging.ui.view.RatingView
import java.util.concurrent.atomic.AtomicBoolean


class MoviesAdapter(private val itemClickListener: (Int, MovieEntity, ImageView, RatingView) -> Unit) :
    PagingDataAdapter<MovieEntity, MoviesAdapter.MoviesViewHolder>(
        MoviesDiffUtils
    ) {
    companion object {
        fun getMOVIE_BANNER_TRANSITIONTAG(position: Int) = "movie_image_transition$position"
    }

    inner class MoviesViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val imageview by view.lazyFindView<ImageView>(R.id.imageView)
        val rating by view.lazyFindView<RatingView>(R.id.rating)

        val atomicBoolean = AtomicBoolean(true)

        fun bind(moviesEntity: MovieEntity, position: Int) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                imageview.transitionName = moviesEntity.backdrop_path
                rating.transitionName = moviesEntity.poster_path
            }

            view.setClickListener { itemClickListener.invoke(position, moviesEntity, imageview, rating) }

            rating.setPercent(moviesEntity.vote_average.toFloat(), atomicBoolean.getAndSet(false))

            val uri = Uri.parse(BASE_BACKDROP_IMAGE_PATTER)
                .buildUpon()
                .appendEncodedPath(moviesEntity.backdrop_path)
                .build()

            imageview.loadImageUrl(uri)
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
        val retryButton by view.lazyFindView<Button>(R.id.retry_button)
        val progressBar by view.lazyFindView<ProgressBar>(R.id.progress_bar)
        val errorMsg by view.lazyFindView<TextView>(R.id.error_msg)

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