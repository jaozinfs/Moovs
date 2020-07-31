package com.jaozinfs.paging.movies.ui.fragments

import android.gesture.GestureOverlayView.ORIENTATION_HORIZONTAL
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.transition.TransitionInflater
import androidx.viewpager2.widget.ViewPager2
import com.jaozinfs.paging.movies.R
import com.jaozinfs.paging.movies.data.network.BASE_BACKDROP_IMAGE_PATTER
import com.jaozinfs.paging.movies.domain.movies.MovieUi
import com.jaozinfs.paging.movies.ui.MoviesViewModel
import com.jaozinfs.paging.movies.ui.adapter.MovieImagesAdapter
import com.jaozinfs.paging.ui.Ajustments
import com.jaozinfs.paging.ui.loadImageUrl
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.sharedViewModel


inline fun <reified T> Fragment.argument(key: String, crossinline default: () -> T?): Lazy<T?> =
    lazy {
        val value = arguments?.get(key)
        if (value is T) value else default()
    }

class MovieDetailFragment : Fragment(R.layout.fragment_movie_details) {
    companion object {
        fun getBundle(movieId: Int, backdrop_path: String, poster_path: String): Bundle =
            Bundle().apply {
                putString(MOVIE_BANNER_ANIMATION_ARG, backdrop_path)
                putString(MOVIE_RATING_ANIMATION_ARG, poster_path)
                putInt(MOVIE_ID_ARG, movieId)
            }

        const val MOVIE_ID_ARG = "MOVIES_ARG"
        const val MOVIE_BANNER_ANIMATION_ARG = "MOVIES_ANIM_ARG"
        const val MOVIE_RATING_ANIMATION_ARG = "MOVIESRATING_ANIM_ARG"
    }

    private val bannerTransitionName by argument<String?>(MOVIE_BANNER_ANIMATION_ARG) { null }
    private val ratingTransitionName by argument<String?>(MOVIE_RATING_ANIMATION_ARG) { null }
    private val movieId by argument<Int?>(MOVIE_ID_ARG) { null }
    private val movieViewModel: MoviesViewModel by sharedViewModel()
    private val movieImagesAdapter = MovieImagesAdapter()
    private var slideJob: Job? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Add these two lines below
        setSharedElementTransitionOnEnter()
        postponeEnterTransition()
        //set trailers
        setMovieImagesAdapter()
        //collect data for this view
        movieId?.let {
            lifecycleScope.launch {
                movieViewModel.getMovieDetails(it).collectLatest {
                    updateView(it)
                }
            }
            lifecycleScope.launch {
                movieViewModel.getMovieImages(it).collectLatest {
                    setMovieImagesAdapter(it)
                }
            }
        }

    }


    private fun setSharedElementTransitionOnEnter() {
        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.shared_element_transition)
    }

    private fun setToolbarTitle(title: String?) {
        (activity as? AppCompatActivity)
            ?.supportActionBar
            ?.title = title
    }

    private fun updateView(movie: MovieUi) {

        val uri = Uri.parse(BASE_BACKDROP_IMAGE_PATTER)
            .buildUpon()
            .appendEncodedPath(movie.poster_path)
            .build()
        val uriBackground = Uri.parse(BASE_BACKDROP_IMAGE_PATTER)
            .buildUpon()
            .appendEncodedPath(movie.backdrop_path)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            rating_view.transitionName = ratingTransitionName
            banner.apply {
                //1
                transitionName = bannerTransitionName

                //2
                loadImageUrl(uri, Ajustments.FitXY) {
                    startPostponedEnterTransition()
                }
            }
            background.loadImageUrl(uriBackground)
        }
        movie_name.text = movie.title
        movie_age.text = movie.release_date.substringBefore("-", "")
        rating_view.setPercent(movie.vote_average)

        movie.adult.let {
            age_view.setIsAdult(it)
        }
        movie_details.text = movie.shortDescription
        movie_subtitle.text = movie.tagline
        setToolbarTitle(movie.original_title)

    }

    private fun setMovieImagesAdapter() {
        with(pager) {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
        }
        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)
        val offsetPx = resources.getDimensionPixelOffset(R.dimen.offset)
        pager.setPageTransformer { page, position ->
            val viewPager = page.parent.parent as ViewPager2
            val offset = position * -(2 * offsetPx + pageMarginPx)
            if (viewPager.orientation == ORIENTATION_HORIZONTAL) {
                if (ViewCompat.getLayoutDirection(viewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                    page.translationX = -offset
                } else {
                    page.translationX = offset
                }
            } else {
                page.translationY = offset
            }
        }
        pager.registerOnPageChangeCallback(SlideTrailersCallback())
        pager.adapter = movieImagesAdapter
    }


    /**
     * set movie images
     */
    private fun setMovieImagesAdapter(it: List<String>) {
        movieImagesAdapter.setList(it)
        startSlide()
    }

    /**
     * Reset last job
     * Init slide collector, pass current position and final position
     */
    private fun startSlide() {
        slideJob?.cancel()
        slideJob = lifecycleScope.launch {
            movieViewModel.autoSlide(pager.currentItem, movieImagesAdapter.getAdapterSize())
                .collect {
                    pager.setCurrentItem(it, true)
                }
        }
    }

    /**
     * Callback to register on viewPager
     * call startSlide every page change
     */
    inner class SlideTrailersCallback : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            startSlide()
        }
    }
}