package com.jaozinfs.paging.movies.data.local

import com.jaozinfs.paging.database.MoviesDatabase
import com.jaozinfs.paging.database.local.entities.MovieEntity
import com.jaozinfs.paging.movies.data.mappers.toUI
import com.jaozinfs.paging.movies.domain.movies.MovieUi
import com.jaozinfs.paging.movies.domain.movies.MoviesLocalRepository

class MoviesLocalRepositoryImpl(private val moviesDatabase: MoviesDatabase) :
    MoviesLocalRepository {
    override fun getMoviesFavorited(): List<MovieUi> {
        return moviesDatabase.moviesDao().getMoviesFavorited().map { it.toUI() }
    }

    override fun saveMovieFavorite(movieUi: MovieUi): Long {
        return movieUi.run {
            moviesDatabase.moviesDao().addMovie(
                MovieEntity(
                    adult,
                    backdrop_path,
                    genre_ids ?: emptyList(),
                    id,
                    original_language,
                    original_title,
                    overview,
                    popularity,
                    poster_path,
                    release_date,
                    title,
                    video,
                    vote_average,
                    vote_count,
                    runtime,
                    tagline,
                    genres
                )
            )
        }
    }

    override fun removeMovieFavorite(movieId: Int): Int {
            return moviesDatabase.moviesDao().removeMovieFavorite(movieId)
    }

    override fun getMovieFavorited(movieUi: MovieUi): MovieUi? {
        return moviesDatabase.moviesDao().getMovieFavorited(movieUi.id)?.toUI()
    }

}