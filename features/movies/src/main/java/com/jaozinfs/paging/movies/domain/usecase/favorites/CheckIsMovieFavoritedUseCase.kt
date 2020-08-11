package com.jaozinfs.paging.movies.domain.usecase.favorites

import com.jaozinfs.paging.network.BaseUseCase
import com.jaozinfs.paging.movies.domain.movies.MovieUi
import com.jaozinfs.paging.movies.domain.movies.MoviesLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CheckIsMovieFavoritedUseCase(
    private val moviesLocalRepository: MoviesLocalRepository
) : BaseUseCase<MovieUi, Boolean> {

    override fun execute(params: MovieUi): Flow<Boolean> = flow {
        emit(moviesLocalRepository.getMovieFavorited(params)!=null)
    }
}