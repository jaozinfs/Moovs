package com.jaozinfs.moovs.movies.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.jaozinfs.moovs.extensions.handleErrors
import com.jaozinfs.moovs.movies.domain.movies.MovieUi
import com.jaozinfs.moovs.movies.domain.usecase.cinema.GetMoviesCinemaUseCase
import com.jaozinfs.moovs.movies.domain.usecase.movies.GetMovieDetailsUseCase
import kotlinx.coroutines.flow.Flow

class MoviesCinemaViewModel(
    private val getMoviesCinemaUseCase: GetMoviesCinemaUseCase,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {


    fun getMovies() = getMoviesCinemaUseCase.execute(Unit).handleErrors{
        Log.d("Teste", "error: $it")
    }

    fun getMovieDetails(id: Int): Flow<MovieUi> = getMovieDetailsUseCase.execute(id)
}
