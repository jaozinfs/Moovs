package com.jaozinfs.moovs.movies.domain.usecase.favorites

import com.jaozinfs.moovs.network.BaseUseCase
import com.jaozinfs.moovs.movies.domain.movies.MoviesLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoveMovieFavoriteUseCase(
    private val moviesLocalRepository: MoviesLocalRepository
) : BaseUseCase<Int, Int> {

    override fun execute(params: Int): Flow<Int> = flow {
        emit(moviesLocalRepository.removeMovieFavorite(params))
    }
}