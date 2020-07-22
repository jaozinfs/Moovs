package com.example.paging.movies.data.network.movies

import com.example.paging.movies.data.local.entities.MovieEntity
import com.example.paging.network.NetworkRepositoryManager

class MoviesRepositoryImpl(private val moviesApi: MoviesApi) : MoviesRepository {
    override suspend fun getMovies(page: Int): List<MovieEntity> {
        return NetworkRepositoryManager.getData {
            moviesApi.getMovies(page)
        }.results
    }
}