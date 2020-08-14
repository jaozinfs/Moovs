package com.jaozinfs.moovs.movies.domain.movies

import kotlinx.coroutines.flow.Flow

interface MoviesLocalRepository {
    fun getMoviesFavorited(): List<MovieUi>
    fun saveMovieFavorite(movieUi: MovieUi): Long
    fun removeMovieFavorite(movieId: Int): Int
    fun getMovieFavorited(movieUi: MovieUi): Flow<List<MovieUi>>
}