package com.example.paging.data.repository.network.movies

import com.example.paging.data.repository.local.entities.MovieEntity

interface MoviesRepository {
    suspend fun getMovies(page: Int): List<MovieEntity>
}