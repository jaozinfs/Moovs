package com.jaozinfs.moovs.movies.ui.fragments

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.jaozinfs.moovs.movies.R
import com.jaozinfs.moovs.movies.ui.MoviesCinemaViewModel
import com.jaozinfs.moovs.movies.ui.adapter.MoviesCinemaAdapter
import kotlinx.android.synthetic.main.fragment_movies_cinema.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel


class MoviesCinemaFragment : Fragment(R.layout.fragment_movies_cinema) {

    private val moviesAdapter = MoviesCinemaAdapter()
    private val moviesViewModel: MoviesCinemaViewModel by viewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureAdapter()

        lifecycleScope.launch {
            moviesViewModel.getMovies().collectLatest {
                moviesAdapter.submitList(it)
            }
        }
    }

    private fun configureAdapter() {
        moviesAdapter.setMovieClickListener { i, movieUi, rootView ->
            val extras = FragmentNavigatorExtras(
                rootView to MovieCinemaDetails.ROOT_VIEW_CONTAINER_TRANSITION_NAME
            )

            val direction =
                MoviesCinemaFragmentDirections.actionNavMoviesCinemaToNavMovieCinemaDetails(
                    movieUi.id
                )

            findNavController().navigate(
                direction,
                extras
            )
        }
        recycler_view.adapter = moviesAdapter
    }

}