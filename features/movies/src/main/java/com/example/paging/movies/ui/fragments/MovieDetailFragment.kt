package com.example.paging.movies.ui.fragments

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import com.example.paging.movies.R
import com.example.paging.database.local.entities.MovieEntity
import com.example.paging.movies.data.network.BASE_BACKDROP_IMAGE_PATTER
import com.example.paging.ui.loadImageUrl
import kotlinx.android.synthetic.main.fragment_movie_details.*

inline fun <reified T> Fragment.argument(key: String, crossinline default: () -> T?): Lazy<T?> =
    lazy {
        val value = arguments?.get(key)
        if (value is T) value else default()
    }

class MovieDetailFragment : Fragment(R.layout.fragment_movie_details) {
    companion object {

        fun getBundle(movieEntity: MovieEntity): Bundle = Bundle().apply {
            putString(MOVIE_BANNER_ANIMATION_ARG, movieEntity.backdrop_path)
            putString(MOVIE_RATING_ANIMATION_ARG, movieEntity.poster_path)
            putSerializable(MOVIE_ARG, movieEntity)
        }

        const val MOVIE_ARG = "MOVIES_ARG"
        const val MOVIE_BANNER_ANIMATION_ARG = "MOVIES_ANIM_ARG"
        const val MOVIE_RATING_ANIMATION_ARG = "MOVIESRATING_ANIM_ARG"
    }

    private val movieEntity by argument<MovieEntity?>(MOVIE_ARG) { null }
    private val bannerTransitionName by argument<String?>(MOVIE_BANNER_ANIMATION_ARG) { null }
    private val ratingTransitionName by argument<String?>(MOVIE_RATING_ANIMATION_ARG) { null }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Add these two lines below
        setSharedElementTransitionOnEnter()
        postponeEnterTransition()

        val uri = Uri.parse(BASE_BACKDROP_IMAGE_PATTER)
            .buildUpon()
            .appendEncodedPath(movieEntity?.backdrop_path)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            rating_view.transitionName = ratingTransitionName
            banner.apply {
                //1
                transitionName = bannerTransitionName

                //2
                banner.loadImageUrl(uri) {
                    startPostponedEnterTransition()
                }
            }
        }
        movie_name.text = movieEntity?.title

        rating_view.setPercent(movieEntity?.vote_average)


    }

    private fun setSharedElementTransitionOnEnter() {
        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.shared_element_transition)
    }
}