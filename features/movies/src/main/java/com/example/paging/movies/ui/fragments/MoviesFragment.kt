package com.example.paging.movies.ui.fragments

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.paging.movies.R
import com.example.paging.movies.ui.MoviesViewModel
import com.example.paging.movies.ui.adapter.MoviesAdapter
import com.example.paging.movies.ui.adapter.ReposLoadStateAdapter
import kotlinx.android.synthetic.main.fragment_movies.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.sharedViewModel


class MoviesFragment : Fragment(R.layout.fragment_movies) {

    companion object {
        const val MovieRVState: String = "FLAG_BUNDLE_MOVIE_RV"
    }

    val viewModel: MoviesViewModel by sharedViewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MoviesAdapter { position, movieEntity, bannerImageView, ratingView ->

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
                    movieEntity
                ),
                null,
                extras
            )
            savedInstanceState?.let { Bundle() }
        }

        movies_rv.layoutManager = GridLayoutManager(context, 2)
        savedInstanceState?.let {
            movies_rv.layoutManager?.onRestoreInstanceState(getMoviesRVRestoreState(it))
        }
        movies_rv.adapter = adapter.withLoadStateHeaderAndFooter(
            header = ReposLoadStateAdapter { adapter.retry() },
            footer = ReposLoadStateAdapter {
                Log.d("Teste", "retry")
                adapter.retry()
            }
        )
        lifecycleScope.launch {
            viewModel.getMovies().collectLatest {
                Log.d("Teste", "Collected")
                adapter.submitData(it)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveMovieStanceState(outState)
    }

    fun saveMovieStanceState(outState: Bundle) {
        outState.putParcelable(MovieRVState, movies_rv.layoutManager?.onSaveInstanceState())
    }

    fun getMoviesRVRestoreState(savedInstanceState: Bundle): Parcelable? =
        savedInstanceState.getParcelable(MovieRVState)
}