package com.jaozinfs.paging.movies.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.jaozinfs.paging.extensions.handleErrors
import com.jaozinfs.paging.movies.domain.usecase.cinema.GetMoviesCinemaUseCase

class MoviesCinemaViewModel(
    private val getMoviesCinemaUseCase: GetMoviesCinemaUseCase
) : ViewModel() {


    fun getMovies() = getMoviesCinemaUseCase.execute(Unit).handleErrors{
        Log.d("Teste", "error: $it")
    }
}
