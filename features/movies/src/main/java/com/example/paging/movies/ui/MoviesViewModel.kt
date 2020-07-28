package com.example.paging.movies.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.paging.database.local.entities.MovieEntity
import com.example.paging.movies.data.MoviesPagingSource
import com.example.paging.movies.data.network.movies.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MoviesViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel() {
    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }

    fun getMovies(
        voteAvarage: Int? = null,
        nameFilter: String? = null
    ): Flow<PagingData<MovieEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviesPagingSource(moviesRepository)
            }
        ).flow.cachedIn(viewModelScope).map { paginData ->
            Log.d("Teste", "Name Filter $nameFilter")
            paginData.filter {

                it.vote_average >= voteAvarage ?: 0 &&
                        if (!nameFilter.isNullOrEmpty()) it.original_title.contains(
                            nameFilter,
                            true
                        ) else true

            }
        }

    }

}
