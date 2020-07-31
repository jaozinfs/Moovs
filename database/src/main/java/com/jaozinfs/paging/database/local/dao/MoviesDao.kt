package com.jaozinfs.paging.database.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jaozinfs.paging.database.local.entities.MovieEntity


@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(movies: List<MovieEntity>)

//    @Query("SELECT * from movieentity")
//    fun getMovies(): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM movieentity WHERE original_title LIKE :query ORDER BY vote_average DESC, original_title ASC")
    fun getMoviesByName(query: String): PagingSource<Int, MovieEntity>

    @Query("DELETE from movieentity")
    fun clearRepos()
}