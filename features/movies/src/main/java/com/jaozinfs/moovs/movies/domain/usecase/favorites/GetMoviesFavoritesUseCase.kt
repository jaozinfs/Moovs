package com.jaozinfs.moovs.movies.domain.usecase.favorites

import com.jaozinfs.moovs.network.BaseUseCase
import com.jaozinfs.moovs.movies.domain.movies.MovieUi
import com.jaozinfs.moovs.movies.domain.movies.MoviesLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMoviesFavoritesUseCase(
    private val moviesLocalRepository: MoviesLocalRepository
) : BaseUseCase<Unit?, List<MovieUi>> {

    override fun execute(params: Unit?): Flow<List<MovieUi>> = flow {
        emit(moviesLocalRepository.getMoviesFavorited())
    }
}