package com.jaozinfs.paging.movies.domain.usecase.movies

import com.jaozinfs.paging.movies.data.cache.GenreCacheRepository
import com.jaozinfs.paging.network.BaseUseCase
import com.jaozinfs.paging.movies.domain.movies.GenreUI
import com.jaozinfs.paging.movies.domain.movies.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetGenresUseCase(
    private val moviesRepository: MoviesRepository,
    private val genreCacheRepository: GenreCacheRepository
) :
    BaseUseCase<Unit?, List<GenreUI>> {

    override fun execute(params: Unit?): Flow<List<GenreUI>> = flow {
        val genres = genreCacheRepository.getGenres()
        if (genres == null) {
            val netWorkGenres = moviesRepository.getGenres()
            genreCacheRepository.saveGenres(netWorkGenres)
        }
        emit(genreCacheRepository.getGenres()?.map { GenreUI(it.id, it.name) }
            ?: emptyList())
    }
}