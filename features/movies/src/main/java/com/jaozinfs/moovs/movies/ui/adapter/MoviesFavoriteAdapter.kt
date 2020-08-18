package com.jaozinfs.moovs.movies.ui.adapter

import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.jaozinfs.moovs.extensions.lazyFindView
import com.jaozinfs.moovs.extensions.loadImageCoil
import com.jaozinfs.moovs.extensions.setClickListener
import com.jaozinfs.moovs.movies.R
import com.jaozinfs.moovs.movies.data.network.BASE_BACKDROP_IMAGE_PATTER
import com.jaozinfs.moovs.movies.domain.movies.MovieUi
import com.jaozinfs.moovs.movies.ui.fragments.MovieDetailFragment
import com.jaozinfs.moovs.ui.view.RatingView
import org.koin.core.KoinComponent
import org.koin.dsl.module


class MoviesFavoriteAdapter() :
    RecyclerView.Adapter<MoviesFavoriteAdapter.MoviesViewHolder>(), KoinComponent {

    private var clickListener: ((Int, MovieUi, ImageView) -> Unit)? = null
    private var items: List<MovieUi> = emptyList()

    inner class MoviesViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val imageview by view.lazyFindView<ImageView>(R.id.background)

        fun bind(moviesEntity: MovieUi) {
            //set views transition name of each different view
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                imageview.transitionName =
                    "${MovieDetailFragment.BANNER_ENTER_TRANSITION_NAME}-${moviesEntity.id}"
            }

            view.setClickListener {
                clickListener?.invoke(
                    position,
                    moviesEntity,
                    imageview
                )
            }
            val imageUri = Uri.parse(BASE_BACKDROP_IMAGE_PATTER)
                .buildUpon()
                .appendEncodedPath(moviesEntity.poster_path)
                .build()

            imageview.loadImageCoil {
                uri = imageUri
                corners = true
                cornersRadius = 12f
            }
        }
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.movies_favorite_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    fun setMovieClickListener(clickListener: (Int, MovieUi, ImageView) -> Unit) {
        this@MoviesFavoriteAdapter.clickListener = clickListener
    }

    fun submitList(items: List<MovieUi>) {
        this@MoviesFavoriteAdapter.items = items
        notifyDataSetChanged()
    }

}
