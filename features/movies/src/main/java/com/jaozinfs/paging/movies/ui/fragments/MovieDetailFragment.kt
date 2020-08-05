package com.jaozinfs.paging.movies.ui.fragments

import android.gesture.GestureOverlayView.ORIENTATION_HORIZONTAL
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.transition.TransitionInflater
import androidx.viewpager2.widget.ViewPager2
import com.jaozinfs.paging.extensions.argument
import com.jaozinfs.paging.extensions.flipModuleFlow
import com.jaozinfs.paging.movies.R
import com.jaozinfs.paging.movies.data.network.BASE_BACKDROP_IMAGE_PATTER
import com.jaozinfs.paging.movies.domain.movies.MovieUi
import com.jaozinfs.paging.movies.ui.MoviesViewModel
import com.jaozinfs.paging.movies.ui.adapter.MovieImagesAdapter
import com.jaozinfs.paging.ui.loadImageCoil
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.sharedViewModel


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

    private enum class Animations(val id: Int) {
        BANNER_EXPAND(R.id.bannerExpand),
        BANNER_DOWN(R.id.bannerDown),
        BANNER_RETURN(R.id.bannerReturn)
    }

    private val bannerTransitionName by argument<String?>(MOVIE_BANNER_ANIMATION_ARG) { null }
    private val ratingTransitionName by argument<String?>(MOVIE_RATING_ANIMATION_ARG) { null }
    private val movieId by argument<Int?>(MOVIE_ID_ARG) { null }
    private val movieViewModel: MoviesViewModel by sharedViewModel()
    private val movieImagesAdapter = MovieImagesAdapter()
    private var slideJob: Job? = null
    private var imagesJob: Job? = null
    private var removeMovieFavorite: Job? = null
    private lateinit var movieUi: MovieUi

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //set trailers
        setMovieImagesAdapter()
        observeEvents()
        postponeEnterTransition()

        //collect data for this view
        movieId?.let {
            lifecycleScope.launch {
                movieViewModel.getMovieDetails(it).collectLatest {
                    movieUi = it
                    setSharedElementTransitionOnEnter()
                    updateView(it)
                    checkIsFavorited()
                }
            }
            getImagesJob(it)
            setRetryCollectImagesListener(it)
        }
    }

    /**
     * Get Images Slider of Movie
     */
    private fun getImagesJob(movieId: Int) {
        imagesJob?.cancel()
        imagesJob = lifecycleScope.launch {
            movieViewModel.getMovieImages(movieId).collectLatest {
                updateVisibilityOfMovieImages(false)
                setMovieImagesAdapter(it)
            }
        }
    }

    /**
     * Set visibility of Button Retry and Text
     * of Image Slider of Movie
     */
    private fun updateVisibilityOfMovieImages(isEnabled: Boolean) {
        retry_button.isVisible = isEnabled
        retry_text.isVisible = isEnabled
    }

    /**
     * set movie images slider retry button listener
     */
    private fun setRetryCollectImagesListener(movieId: Int) {
        retry_button.setOnClickListener {
            getImagesJob(movieId)
        }
    }

    /**
     * Animations
     */
    private fun setFavoriteButtonListener(favorited: Boolean) {
        followCustomView.setOnClickListener {
            if (favorited)
                removeMovieFavorited()
            else
                animateFavorite()
        }
    }

    private fun removeMovieFavorited() {
        removeMovieFavorite?.cancel()
        lifecycleScope.launch {
            movieId ?: return@launch
            movieViewModel.removeMovieFavorited(movieId!!).collectLatest {
                checkIsFavorited()
            }
        }
    }

    /**
     * Transition start image view
     */
    private fun setSharedElementTransitionOnEnter() {
        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.shared_element_transition)
    }

    /**
     * Title of fragment
     */
    private fun setToolbarTitle(title: String?) {
        (activity as? AppCompatActivity)
            ?.supportActionBar
            ?.title = title
    }

    /**
     * Populate view with entity Movie
     */
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
//                loadImageUrl(uri, Ajustments.FitXY) {
//                    startPostponedEnterTransition()
//                }
                loadImageCoil(uri) {
                    Log.d("Teste", "aq")
                    startPostponedEnterTransition()
                }
            }
            background.loadImageCoil(uriBackground)
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

    /**
     * Configure movie images viewpager
     */
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
            flipModuleFlow(pager.currentItem, movieImagesAdapter.getAdapterSize())
                .collect {
                    pager.setCurrentItem(it, true)
                }
        }
    }


    /**
     * Start animation when favorite movie
     */
    private fun animateFavorite() = lifecycleScope.launch {
        with(movie_details_motion_container) {
            setTransition(Animations.BANNER_EXPAND.id)
            transitionToEnd()
        }
        delay(500)
        with(movie_details_motion_container) {
            setTransition(Animations.BANNER_DOWN.id)
            transitionToEnd()
        }
        delay(1000)
        movieViewModel.saveMovie(movieUi).collect {
            with(movie_details_motion_container) {
                setTransition(Animations.BANNER_RETURN.id)
                transitionToEnd()
            }
            checkIsFavorited(false)
        }
    }

    /**
     * Verify if movie is favorite and change UI
     */
    suspend fun checkIsFavorited(animate: Boolean = true) {
        movieViewModel.checkIsFavorited(movieUi).collect {
            if (animate)
                setMovieFavoriteBackground(it)
            setFavoriteButtonListener(it)
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

    private fun observeEvents() = with(movieViewModel) {
        handleErrorImages.observe(viewLifecycleOwner, Observer {
            updateVisibilityOfMovieImages(true)
        })
        handlerErrorMovieDetails.observe(viewLifecycleOwner, Observer {
            //            context showToast it
        })
        disableFavoriteButton.observe(viewLifecycleOwner, Observer {
            followCustomView.isEnabled = false
        })
    }

    private fun setMovieFavoriteBackground(status: Boolean) {
        if (status)
            setMovieFavorite()
        else
            setMovieDesfavorited()
    }

    private fun setMovieDesfavorited() = with(followCustomView) {
        setBackgroundColor(
            ContextCompat.getColor(
                context,
                com.jaozinfs.paging.R.color.colorChipBackgroundUnselected
            )
        )
        text = getString(R.string.title_favorite)
        setTextColor(ContextCompat.getColor(context, R.color.colorWhite))
    }

    private fun setMovieFavorite() = with(followCustomView) {
        setBackgroundColor(
            ContextCompat.getColor(
                context,
                com.jaozinfs.paging.R.color.colorPrimary
            )
        )
        text = getString(R.string.title_remove_favorite)
        setTextColor(ContextCompat.getColor(context, R.color.colorWhite))
    }
}