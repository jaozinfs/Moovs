package com.jaozinfs.paging.movies.domain.usecase.favorites

import com.jaozinfs.paging.movies.domain.BaseUseCase
import com.jaozinfs.paging.movies.domain.movies.MovieUi
import com.jaozinfs.paging.movies.domain.movies.MoviesLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveMovieFavoriteUseCase(
    private val moviesLocalRepository: MoviesLocalRepository
) : BaseUseCase<MovieUi, Long> {

    override fun execute(params: MovieUi): Flow<Long> = flow {
        emit(moviesLocalRepository.saveMovieFavorite(params))
    }
}