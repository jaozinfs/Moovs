package com.example.paging3.data.repository.network.movies

import android.util.Log
import com.example.paging3.data.repository.local.entities.MovieEntity
import com.example.paging3.data.repository.network.NetworkRepositoryRequest

class MoviesRepositoryImpl(private val moviesApi: MoviesApi) : MoviesRepository {
    override suspend fun getMovies(page: Int): List<MovieEntity> {
        return NetworkRepositoryRequest.getData {
            moviesApi.getMovies(page, NetworkRepositoryRequest.MOVIESTBAPI)
        }.results.apply {
            Log.d("Teste", "Size: $size")
        }
    }
}