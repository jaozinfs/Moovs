package com.jaozinfs.paging.movies.domain.usecase

import com.jaozinfs.paging.database.local.entities.MovieEntity
import com.jaozinfs.paging.movies.domain.BaseUseCase
import com.jaozinfs.paging.movies.domain.movies.MovieUi
import com.jaozinfs.paging.movies.domain.movies.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMovieDetailsUseCase(
    private val moviesRepository: MoviesRepository
) :
    BaseUseCase<Int, MovieUi> {

    override fun execute(params: Int): Flow<MovieUi> = flow {
        emit(moviesRepository.getMovieDetails(params))
    }
}