package com.jaozinfs.moovs.movies.domain.usecase.movies

import com.jaozinfs.moovs.network.BaseUseCase
import com.jaozinfs.moovs.movies.domain.movies.MovieImagesUI
import com.jaozinfs.moovs.movies.domain.movies.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMovieImagesUseCase(
    private val moviesRepository: MoviesRepository
) :
    BaseUseCase<Int, MovieImagesUI> {

    override fun execute(params: Int): Flow<MovieImagesUI> = flow {
        emit(moviesRepository.getMovieImages(params))
    }
}