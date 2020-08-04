package com.jaozinfs.paging.movies.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.jaozinfs.paging.movies.R
import com.jaozinfs.paging.movies.ui.MoviesFavoritesViewModel
import com.jaozinfs.paging.movies.ui.adapter.MoviesFavoriteAdapter
import kotlinx.android.synthetic.main.fragment_movies_favorites.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.viewmodel.ext.android.viewModel

class MoviesFavoritesFragment : Fragment(R.layout.fragment_movies_favorites) {


    private val movieViewModel: MoviesFavoritesViewModel by viewModel()
    private val adapter = MoviesFavoriteAdapter()
    private var jobMovies: Job? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeEvents()
        configureList()
        getMovies()
    }

    /**
     * configure recyclerview with
     * [adapter]
     * [GridLayoutManager]
     */
    private fun configureList() {
        with(movies_rv) {
            layoutManager = GridLayoutManager(context, 2)
            adapter = this@MoviesFavoritesFragment.adapter
        }
        setAdapterClickListener()
    }

    /**
     * persiste movies favorited
     */
    private fun getMovies() {
        jobMovies?.cancel()
        lifecycleScope.launchWhenCreated {
            movieViewModel.getMovies().collectLatest {
                adapter.submitList(it)
            }
        }
    }

    //Click lister of adapter movies
    private fun setAdapterClickListener() {
        adapter.setMovieClickListener { _, movieEntity, bannerImageView, ratingView ->
            val extras = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                FragmentNavigatorExtras(
                    bannerImageView to bannerImageView.transitionName,
                    ratingView to ratingView.transitionName
                )
            } else {
                null
            }
            findNavController().navigate(
                R.id.nav_movies_detail,
                MovieDetailFragment.getBundle(
                    movieEntity.id,
                    movieEntity.backdrop_path,
                    movieEntity.poster_path
                ),
                null,
                extras
            )
        }
    }

    private fun observeEvents() {
        movieViewModel.emptyMoviesObservable.observe(viewLifecycleOwner, Observer {
            lav_android_wave_json.isVisible = it
            title_favorite_empty.isVisible = it
        })
    }
}