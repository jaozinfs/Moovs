package com.jaozinfs.moovs.database.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jaozinfs.moovs.database.local.entities.MovieEntity


@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovie(movies: MovieEntity): Long

    @Query("SELECT * FROM movieentity ORDER by id DESC")
    fun getMoviesFavorited(): List<MovieEntity>

    @Query("DELETE from movieentity WHERE id is :movieId")
    fun removeMovieFavorite(movieId: Int): Int

    @Query("SELECT * FROM movieentity WHERE id is :movieId")
    fun getMovieFavorited(movieId: Int): MovieEntity?

}