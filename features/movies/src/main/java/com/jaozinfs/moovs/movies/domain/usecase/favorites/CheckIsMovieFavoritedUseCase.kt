package com.jaozinfs.moovs.movies.domain.usecase.favorites

import com.jaozinfs.moovs.movies.domain.movies.MovieUi
import com.jaozinfs.moovs.movies.domain.movies.MoviesLocalRepository
import com.jaozinfs.moovs.network.BaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CheckIsMovieFavoritedUseCase(
    private val moviesLocalRepository: MoviesLocalRepository
) : BaseUseCase<MovieUi, Boolean> {

    override fun execute(params: MovieUi): Flow<Boolean> =
        moviesLocalRepository.getMovieFavorited(params).map {
            it.isNotEmpty()
        }
}