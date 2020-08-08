package com.jaozinfs.paging.movies.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaozinfs.paging.movies.domain.usecase.favorites.GetMoviesFavoritesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach

class MoviesFavoritesViewModel(
    private val getMoviesFavoritesUseCase: GetMoviesFavoritesUseCase
) : ViewModel() {
    private val _emptyMovies = MutableLiveData<Boolean>()
    val emptyMoviesObservable: LiveData<Boolean> = _emptyMovies

    fun getMovies() = getMoviesFavoritesUseCase.execute(null)
        .flowOn(Dispatchers.IO)
        .onEach {
            _emptyMovies.value = it.isEmpty()
        }.flowOn(Dispatchers.Main)
}