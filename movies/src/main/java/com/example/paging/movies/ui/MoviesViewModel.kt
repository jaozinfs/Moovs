package com.example.paging.movies.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.paging.movies.data.MoviesPagingSource
import com.example.paging.movies.data.network.movies.MoviesRepository

class MoviesViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel() {
    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }

    fun getMovies() =
        Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviesPagingSource(
                    moviesRepository
                )
            }
        ).flow.cachedIn(viewModelScope)


}