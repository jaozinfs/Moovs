package com.jaozinfs.moovs.movies.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.lottie.LottieDrawable
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.transition.platform.MaterialElevationScale
import com.jaozinfs.moovs.extensions.loadImageCoil
import com.jaozinfs.moovs.extensions.transformCarroucel
import com.jaozinfs.moovs.movies.R
import com.jaozinfs.moovs.movies.data.network.BASE_BACKDROP_IMAGE_PATTER
import com.jaozinfs.moovs.movies.data.network.BASE_BACKDROP_IMAGE_PATTER_ORIGINAL
import com.jaozinfs.moovs.movies.di.moviesModules
import com.jaozinfs.moovs.movies.domain.movies.MovieUi
import com.jaozinfs.moovs.movies.ui.MoviesViewModel
import com.jaozinfs.moovs.movies.ui.adapter.MoviesAdapter
import com.jaozinfs.moovs.movies.ui.adapter.MoviesFavoriteAdapter
import com.jaozinfs.moovs.movies.ui.adapter.ReposLoadStateAdapter
import com.jaozinfs.moovs.utils.UriUtils
import kotlinx.android.synthetic.main.fragment_movies.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.koinApplication
import java.io.Closeable


class MoviesFragment : Fragment(R.layout.fragment_movies) {

    private lateinit var searchView: SearchView
    private val viewModel: MoviesViewModel by sharedViewModel()
    private var searchJob: Job? = null
    private val adapter = MoviesAdapter()
    private val favoriteAdapter = MoviesFavoriteAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        koinApplication {
            unloadKoinModules(moviesModules)
            loadKoinModules(moviesModules)
        }

        setHasOptionsMenu(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            exitTransition = MaterialElevationScale(/* growing= */ false)
            reenterTransition = MaterialElevationScale(/* growing= */ true)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
        setAdapterClickListener()
        setSwipeRefreshListener()
        initFavoritePager()
        initList()
        getFavoriteMovies()
        observeFilter()
    }

    //Set listener of swipe refresh layout to refresh list of movies
    private fun setSwipeRefreshListener() {
        swipe_refresh_layout.setOnRefreshListener {
            resetFilter()
            getMovies(isRefresh = true)
        }
    }

    private fun initList() {
        movies_rv.layoutManager =
            object : LinearLayoutManager(context, HORIZONTAL, false) {
                override fun checkLayoutParams(lp: RecyclerView.LayoutParams?): Boolean {
                    lp?.width = width / 2
                    return true
                }
            }
        //Add Loading state on middle of view
        adapter.addLoadStateListener { loadState ->
            swipe_refresh_layout?.isRefreshing = loadState.source.refresh is LoadState.Loading
            if (loadState.source.refresh is LoadState.Error)
                setNoConnectionNetworkError()
        }
        //empty state
        adapter.addDataRefreshListener {
            movies_empty_animation.isVisible = it
        }
        //set adapter on recycler view and set adapter in header and footer
        movies_rv.adapter = adapter.withLoadStateHeaderAndFooter(
            header = ReposLoadStateAdapter { adapter.retry() },
            footer = ReposLoadStateAdapter { adapter.retry() }
        )
    }

    private fun setNoConnectionNetworkError() {
        with(movies_empty_animation) {
            setAnimation("not-network.json")
            isVisible = true
            repeatCount = LottieDrawable.INFINITE
        }
    }

    //Click lister of adapter movies
    private fun setAdapterClickListener() {
        adapter.setMovieClickListener { _, movieEntity, bannerImageView, ratingView ->
            val extras = FragmentNavigatorExtras(
                bannerImageView to MovieDetailFragment.BANNER_ENTER_TRANSITION_NAME,
                ratingView to MovieDetailFragment.RATING_ENTER_TRANSITION_NAME
            )
            val direction =
                MoviesFragmentDirections.actionNavMoviesToNavMoviesDetail(movieEntity.id)

            findNavController().navigate(
                direction,
                extras
            )
        }
    }

    private fun observeLiveData() {
        viewModel.emptyMovies.observe(viewLifecycleOwner, Observer { isEmpty ->
            favorites_container.isVisible = !isEmpty
        })
    }

    private fun getMovies(
        voteAvarage: String? = null,
        nameFilter: String? = null,
        isAdult: Boolean? = null,
        genres: List<Int>? = null,
        isRefresh: Boolean = false
    ) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.getMovies(
                MoviesViewModel.MoviesFilterObject(
                    voteAvarage,
                    nameFilter,
                    isAdult,
                    genres,
                    isRefresh
                )
            ).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.movies_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        (searchItem.actionView as? SearchView)?.let {
            searchView = it
            searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                    return true
                }

                override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                    getMovies(isRefresh = true)
                    return true
                }
            })
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    getMovies(nameFilter = query)
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    //listener menu item click
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.filter ->
                findNavController().navigate(
                    R.id.nav_movies_filter
                )
        }
        return true
    }

    //Observe when fragment backstack returns of FilterFragment
    //if have value, persiste list with filter object
    private fun observeFilter() {
        if (findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<MoviesFilterFragment.FilterObject>(
                MoviesFilterFragment.FILTER_OBJECT
            )?.value == null
        ) {
            getMovies()
            return
        }
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<MoviesFilterFragment.FilterObject>(
            MoviesFilterFragment.FILTER_OBJECT
        )?.observe(viewLifecycleOwner, Observer {
            it?.let {
                getMovies(
                    it.voteAvarege,
                    isAdult = it.isAdult,
                    genres = it.genres
                )
            }
        })

    }


    private fun initFavoritePager() = with(movies_favorites_pager) {
        offscreenPageLimit = 3
        transformCarroucel()
        adapter = favoriteAdapter
        favoriteAdapter.setMovieClickListener{_, movieEntity, bannerImageView ->
            val extras = FragmentNavigatorExtras(
                bannerImageView to MovieDetailFragment.BANNER_ENTER_TRANSITION_NAME
            )
            val direction =
                MoviesFragmentDirections.actionNavMoviesToNavMoviesDetail(movieEntity.id)

            findNavController().navigate(
                direction,
                extras
            )
        }
        TabLayoutMediator(tab_layout, this) { _, _->}.attach()
    }

    private fun getFavoriteMovies() {
        lifecycleScope.launch {
            viewModel.getFavoriteMovies().collectLatest {
                favoriteAdapter.submitList(it)
                movies_favorites_pager.registerOnPageChangeCallback(FavoriteMovieItemChange(it))
            }
        }
    }


    private fun resetFilter() {
        findNavController().currentBackStackEntry?.savedStateHandle?.set(
            MoviesFilterFragment.FILTER_OBJECT,
            null
        )
    }

    private fun changeBackGroundImage(movieUi: MovieUi) {
        background.loadImageCoil {
            uri = UriUtils.getUriFromBaseAndBackdrop(
                BASE_BACKDROP_IMAGE_PATTER_ORIGINAL,
                movieUi.backdrop_path
            )
        }
    }

    private inner class FavoriteMovieItemChange(private val list: List<MovieUi>) :
        ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            changeBackGroundImage(list[position])
        }
    }
}
