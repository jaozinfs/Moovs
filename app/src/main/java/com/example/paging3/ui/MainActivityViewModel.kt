package com.example.paging3.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.paging3.data.repository.network.movies.MoviesRepository
import com.example.paging3.datasource.MoviesRemoteMediator

class MainActivityViewModel(
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
            pagingSourceFactory = { MoviesRemoteMediator(moviesRepository) }
        ).flow.cachedIn(viewModelScope)


}