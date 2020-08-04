package com.jaozinfs.paging.movies.ui

import androidx.lifecycle.ViewModel
import com.jaozinfs.paging.movies.domain.usecase.GetMoviesFavoritesUseCase
import com.jaozinfs.paging.movies.domain.usecase.RemoveMovieFavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class MoviesFavoritesViewModel(
    private val getMoviesFavoritesUseCase: GetMoviesFavoritesUseCase
) : ViewModel() {
    fun getMovies() = getMoviesFavoritesUseCase.execute(null).flowOn(Dispatchers.IO)
}