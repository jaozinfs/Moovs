package com.jaozinfs.moovs.movies.ui

import androidx.lifecycle.ViewModel
import com.jaozinfs.moovs.extensions.handleErrors
import com.jaozinfs.moovs.movies.domain.movies.MovieUi
import com.jaozinfs.moovs.movies.domain.usecase.cinema.GetMoviesCinemaUseCase
import com.jaozinfs.moovs.movies.domain.usecase.movies.GetMovieDetailsUseCase
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

class MoviesCinemaViewModel(
    private val getMoviesCinemaUseCase: GetMoviesCinemaUseCase,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {


    fun getMovies() = getMoviesCinemaUseCase.execute(Unit).handleErrors {
        Timber.e("error: $it")
    }

    fun getMovieDetails(id: Int): Flow<MovieUi> = getMovieDetailsUseCase.execute(id)
}
