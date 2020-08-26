package com.jaozinfs.moovs.movies.ui.fragments

import android.gesture.GestureOverlayView.ORIENTATION_HORIZONTAL
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.jaozinfs.moovs.extensions.flipModuleFlow
import com.jaozinfs.moovs.extensions.loadImageCoil
import com.jaozinfs.moovs.extensions.themeColor
import com.jaozinfs.moovs.movies.R
import com.jaozinfs.moovs.movies.data.network.BASE_BACKDROP_IMAGE_PATTER
import com.jaozinfs.moovs.movies.domain.movies.MovieUi
import com.jaozinfs.moovs.movies.ui.MoviesViewModel
import com.jaozinfs.moovs.movies.ui.adapter.MovieImagesAdapter
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.sharedViewModel


class MovieDetailFragment : Fragment(R.layout.fragment_movie_details) {

    companion object {
        const val BANNER_ENTER_TRANSITION_NAME = "bannerEnterTransition"
        const val RATING_ENTER_TRANSITION_NAME = "ratingEnterTransition"
    }

    private enum class Animations(val id: Int) {
        BANNER_EXPAND(R.id.bannerExpand),
        BANNER_DOWN(R.id.bannerDown),
        BANNER_RETURN(R.id.bannerReturn)
    }

    private val args: MovieDetailFragmentArgs by navArgs()
    private val movieViewModel: MoviesViewModel by sharedViewModel()
    private val movieImagesAdapter = MovieImagesAdapter()
    private var slideJob: Job? = null
    private var imagesJob: Job? = null
    private var removeMovieFavorite: Job? = null
    private lateinit var movieUi: MovieUi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSharedElementTransitionOnEnter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //post transition until load image with coil
        postponeEnterTransition()
        //set trailers
        setViewTransitionsNames()
        setMovieImagesAdapter()
        observeEvents()
        readMore()
        //collect data for this view
        args.movieId.let {
            lifecycleScope.launch {
                movieViewModel.getMovieDetails(it).collectLatest {
                    movieUi = it
                    updateView(it)
                    observeMovieIsFavorite()
                }
            }
            getImagesJob(it)
            setRetryCollectImagesListener(it)
        }
    }

    private fun setViewTransitionsNames() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            banner.transitionName = BANNER_ENTER_TRANSITION_NAME
            rating_view.transitionName = RATING_ENTER_TRANSITION_NAME
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
     * Transition start image view
     */
    private fun setSharedElementTransitionOnEnter() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition = MaterialContainerTransform().apply {
                drawingViewId = com.jaozinfs.moovs.R.id.main_fragmnet_container
                isElevationShadowEnabled = true
                scrimColor = Color.TRANSPARENT
                setAllContainerColors(requireContext().themeColor(com.jaozinfs.moovs.R.attr.colorSurface))
            }
        }
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

        banner.loadImageCoil(uri) {
            //after load image start animation of banner transition
            startPostponedEnterTransition()
        }
        background.loadImageCoil(uriBackground)
        movie_name.text = movie.title
        movie_age.text = movie.release_date.substringBefore("-", "")
        rating_view.setPercent(movie.vote_average)

        movie.adult.let {
            age_view.setIsAdult(it)
        }
        movie_details.text = movie.shortDescription
        setToolbarTitle(movie.original_title)
        overview_tv.text = movie.overview
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

    private fun observeMovieIsFavorite() {
        movieViewModel.checkIsFavorited(movieUi).observe(viewLifecycleOwner, Observer {
            setMovieFavoriteBackground(it)
            followCustomView.setOnClickListener(getListenerFromFavoriteState(it))
        })
    }

    private fun setMovieFavoriteBackground(isFavorite: Boolean) {
        if (isFavorite)
            setMovieFavorite()
        else
            setMovieNormal()
    }

    private fun setMovieNormal() = with(followCustomView) {
        setBackgroundColor(
            ContextCompat.getColor(
                context,
                com.jaozinfs.moovs.R.color.colorChipBackgroundUnselected
            )
        )
        text = getString(R.string.title_favorite)
    }

    private fun setMovieFavorite() = with(followCustomView) {
        setBackgroundColor(
            ContextCompat.getColor(
                context,
                com.jaozinfs.moovs.R.color.colorPrimary
            )
        )
        text = getString(R.string.title_remove_favorite)
    }

    private fun getListenerFromFavoriteState(isFavorite: Boolean) = View.OnClickListener {
        when (isFavorite) {
            true -> {
                removeMovieFavorited()
            }
            else -> {
                animateFavorite()
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
        }
    }

    private fun removeMovieFavorited() {
        removeMovieFavorite?.cancel()
        lifecycleScope.launch {
            movieViewModel.removeMovieFavorited(args.movieId).collect()
        }
    }

    private fun readMore() = with(see_more_tv){
        setOnClickListener {
            movie_details_motion_container.transitionToState(R.id.overViewTransition)
        }
    }
}