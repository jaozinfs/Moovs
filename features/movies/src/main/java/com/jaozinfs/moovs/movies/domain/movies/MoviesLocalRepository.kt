package com.jaozinfs.moovs.movies.domain.movies

interface MoviesLocalRepository {
    fun getMoviesFavorited(): List<MovieUi>
    fun saveMovieFavorite(movieUi: MovieUi): Long
    fun removeMovieFavorite(movieId: Int): Int
    fun getMovieFavorited(movieUi: MovieUi): MovieUi?
}