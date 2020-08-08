package com.jaozinfs.paging.movies.domain.usecase.favorites

import com.jaozinfs.paging.movies.domain.BaseUseCase
import com.jaozinfs.paging.movies.domain.movies.MovieUi
import com.jaozinfs.paging.movies.domain.movies.MoviesLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMoviesFavoritesUseCase(
    private val moviesLocalRepository: MoviesLocalRepository
) : BaseUseCase<Unit?, List<MovieUi>> {

    override fun execute(params: Unit?): Flow<List<MovieUi>> = flow {
        emit(moviesLocalRepository.getMoviesFavorited())
    }
}