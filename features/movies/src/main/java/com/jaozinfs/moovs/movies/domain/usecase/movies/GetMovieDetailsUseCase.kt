package com.jaozinfs.moovs.movies.domain.usecase.movies

import com.jaozinfs.moovs.network.BaseUseCase
import com.jaozinfs.moovs.movies.domain.movies.MovieUi
import com.jaozinfs.moovs.movies.domain.movies.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMovieDetailsUseCase(
    private val moviesRepository: MoviesRepository
) :
    BaseUseCase<Int, MovieUi> {

    override fun execute(params: Int): Flow<MovieUi> = flow {
        emit(moviesRepository.getMovieDetails(params))
    }
}