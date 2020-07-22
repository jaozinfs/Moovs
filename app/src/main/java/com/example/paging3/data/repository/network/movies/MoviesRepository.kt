package com.example.paging3.data.repository.network.movies

import com.example.paging3.data.repository.local.entities.MovieEntity
import com.example.paging3.data.repository.local.entities.Response

interface MoviesRepository {
    suspend fun getMovies(page: Int): List<MovieEntity>
}