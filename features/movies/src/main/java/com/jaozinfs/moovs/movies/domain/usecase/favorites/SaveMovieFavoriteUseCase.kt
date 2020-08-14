package com.jaozinfs.moovs.movies.domain.usecase.favorites

import com.jaozinfs.moovs.network.BaseUseCase
import com.jaozinfs.moovs.movies.domain.movies.MovieUi
import com.jaozinfs.moovs.movies.domain.movies.MoviesLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveMovieFavoriteUseCase(
    private val moviesLocalRepository: MoviesLocalRepository
) : BaseUseCase<MovieUi, Long> {

    override fun execute(params: MovieUi): Flow<Long> = flow {
        emit(moviesLocalRepository.saveMovieFavorite(params))
    }
}