package com.jaozinfs.moovs.movies.ui.fragments

import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.jaozinfs.moovs.extensions.loadImageCoil
import com.jaozinfs.moovs.movies.R
import com.jaozinfs.moovs.movies.data.network.BASE_BACKDROP_IMAGE_PATTER
import com.jaozinfs.moovs.movies.ui.MoviesCinemaViewModel
import kotlinx.android.synthetic.main.fragment_movie_cinema_details.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel


class MovieCinemaDetails : Fragment(R.layout.fragment_movie_cinema_details) {
    companion object{
        const val ROOT_VIEW_CONTAINER_TRANSITION_NAME = "rootViewTransitionName"
    }

    private val moviesViewModel: MoviesCinemaViewModel by viewModel()
    private val args: MovieCinemaDetailsArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition = MaterialContainerTransform().apply {
                scrimColor = Color.TRANSPARENT
                drawingViewId = R.id.movie_cinema_detials_container
                containerColor = Color.TRANSPARENT
            }

            sharedElementReturnTransition = MaterialContainerTransform().apply {
                scrimColor = Color.TRANSPARENT
                containerColor = Color.TRANSPARENT
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //post transition until load image with coil
        postponeEnterTransition()
        //collect data for this view
        args.movieId.let {
            lifecycleScope.launch {
                moviesViewModel.getMovieDetails(it).collectLatest { movie ->
                    val uriBackground = Uri.parse(BASE_BACKDROP_IMAGE_PATTER)
                        .buildUpon()
                        .appendEncodedPath(movie.backdrop_path)
                        .build()

                    background.loadImageCoil(uriBackground) {
                        (view.parent as? ViewGroup)?.doOnPreDraw {
                            startPostponedEnterTransition()
                        }
                    }
                }
            }
        }
    }

}