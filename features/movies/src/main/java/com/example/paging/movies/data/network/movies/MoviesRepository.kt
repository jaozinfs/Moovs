package com.example.paging.movies.data.network.movies

import com.example.paging.database.local.entities.MovieEntity

interface MoviesRepository {
    suspend fun getMovies(page: Int): List<MovieEntity>
}