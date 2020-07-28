package com.example.paging.movies.ui.fragments

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.paging.movies.R
import com.example.paging.movies.ui.MoviesViewModel
import com.example.paging.movies.ui.adapter.MoviesAdapter
import com.example.paging.movies.ui.adapter.ReposLoadStateAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_movies.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.sharedViewModel


class MoviesFragment : Fragment(R.layout.fragment_movies) {

    companion object {
        const val MovieRVState: String = "FLAG_BUNDLE_MOVIE_RV"
    }

    private lateinit var searchView: SearchView

    private val viewModel: MoviesViewModel by sharedViewModel()

    private var searchJob: Job? = null
    private val adapter = MoviesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapterClickListener(savedInstanceState)

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

        activity?.findViewById<FloatingActionButton>(R.id.fab)?.setOnClickListener {
            getMovies()
        }
        observeFilter()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveMovieStanceState(outState)
    }

    private fun setAdapterClickListener(savedInstanceState: Bundle?) {
        adapter.setMovieClickListener { position, movieEntity, bannerImageView, ratingView ->

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
    }

    private fun saveMovieStanceState(outState: Bundle) {
        outState.putParcelable(MovieRVState, movies_rv.layoutManager?.onSaveInstanceState())
    }

    private fun getMoviesRVRestoreState(savedInstanceState: Bundle): Parcelable? =
        savedInstanceState.getParcelable(MovieRVState)

    private fun getMovies(voteAvarage: Int? = 0, nameFilter: String? = null) {
        searchJob?.cancel()

        searchJob = lifecycleScope.launch {
            viewModel.getMovies(voteAvarage, nameFilter).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.movies_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)

        (searchItem.actionView as? SearchView)?.let {
            searchView = it
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    Log.d("Teste", "aloha")
                    getMovies(nameFilter = query)
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let {
                        if (it.isEmpty())
                            getMovies()
                    }
                    return false
                }
            })
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.filter ->
                findNavController().navigate(
                    R.id.nav_movies_filter
                )
        }
        return true
    }

    private fun observeFilter() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<MoviesFilterFragment.FilterObject>(
            MoviesFilterFragment.FILTER_OBJECT
        )?.observe(viewLifecycleOwner, Observer {
            getMovies(it.voteAvarege)
        })
    }


}
