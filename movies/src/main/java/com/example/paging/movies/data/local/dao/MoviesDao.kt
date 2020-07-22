package com.example.paging.movies.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Insert
import androidx.room.Query
import com.example.paging.movies.data.local.entities.MovieEntity

interface MoviesDao {
    @Insert
    suspend fun addAll(movies: List<MovieEntity>)

    @Query("SELECT * from movieentity")
    suspend fun getMovies(): PagingSource<Int, MovieEntity>

    @Query("DELETE from movieentity")
    fun clearRepos()
}