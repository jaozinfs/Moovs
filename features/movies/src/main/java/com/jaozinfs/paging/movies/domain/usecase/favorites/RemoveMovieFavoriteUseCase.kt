package com.jaozinfs.paging.movies.domain.usecase.favorites

import com.jaozinfs.paging.movies.domain.BaseUseCase
import com.jaozinfs.paging.movies.domain.movies.MoviesLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoveMovieFavoriteUseCase(
    private val moviesLocalRepository: MoviesLocalRepository
) : BaseUseCase<Int, Int> {

    override fun execute(params: Int): Flow<Int> = flow {
        emit(moviesLocalRepository.removeMovieFavorite(params))
    }
}