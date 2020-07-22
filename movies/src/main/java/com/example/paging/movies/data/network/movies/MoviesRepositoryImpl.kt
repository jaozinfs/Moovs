package com.example.paging.movies.data.network.movies

import android.util.Log
import com.example.paging.movies.data.local.entities.MovieEntity
import com.example.paging.network.NetworkRepositoryRequest

class MoviesRepositoryImpl(private val moviesApi: MoviesApi) : MoviesRepository {
    override suspend fun getMovies(page: Int): List<MovieEntity> {
        return com.example.paging.network.NetworkRepositoryRequest.getData {
            moviesApi.getMovies(page, com.example.paging.network.NetworkRepositoryRequest.MOVIESTBAPI)
        }.results.apply {
            Log.d("Teste", "Size: $size")
        }
    }
}