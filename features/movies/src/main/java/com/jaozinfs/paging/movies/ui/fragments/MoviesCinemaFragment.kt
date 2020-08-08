package com.jaozinfs.paging.movies.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.jaozinfs.paging.movies.R
import com.jaozinfs.paging.movies.ui.MoviesCinemaViewModel
import com.jaozinfs.paging.movies.ui.adapter.MoviesCinemaAdapter
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
        recycler_view.adapter = moviesAdapter
    }

}