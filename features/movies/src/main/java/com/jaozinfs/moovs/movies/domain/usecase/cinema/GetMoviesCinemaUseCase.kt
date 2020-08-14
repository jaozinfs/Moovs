package com.jaozinfs.moovs.movies.domain.usecase.cinema

import com.jaozinfs.moovs.network.BaseUseCase
import com.jaozinfs.moovs.movies.domain.movies.MovieUi
import com.jaozinfs.moovs.movies.domain.movies.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMoviesCinemaUseCase(private val moviesRepository: MoviesRepository) :
    BaseUseCase<Unit, List<MovieUi>> {
    override fun execute(params: Unit): Flow<List<MovieUi>> = flow {
        val movies = moviesRepository.getMoviesCinema()
        emit(movies)
    }
}