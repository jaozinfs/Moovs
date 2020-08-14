package com.jaozinfs.moovs.movies.data.network

import com.jaozinfs.moovs.database.local.entities.MovieEntity
import com.jaozinfs.moovs.movies.data.mappers.toUI
import com.jaozinfs.moovs.movies.domain.movies.GenreUI
import com.jaozinfs.moovs.movies.domain.movies.MovieImagesUI
import com.jaozinfs.moovs.movies.domain.movies.MovieUi
import com.jaozinfs.moovs.movies.domain.movies.MoviesRepository
import com.jaozinfs.moovs.network.NetworkRepositoryManager

class MoviesRepositoryImpl(private val moviesApi: MoviesApi) :
    MoviesRepository {

    override suspend fun getMovies(page: Int): List<MovieEntity> {
        return NetworkRepositoryManager.getData {
            moviesApi.getMovies(page)
        }.results
    }

    override suspend fun getMovieDetails(movieId: Int): MovieUi =
        NetworkRepositoryManager.getData {
            moviesApi.getMovieDetails(movieId)
        }.toUI()

    override suspend fun getMovieImages(movieId: Int): MovieImagesUI =
        NetworkRepositoryManager.getData {
            moviesApi.getMovieImages(movieId)
        }.toUI()

    override suspend fun getGenres(): List<GenreUI> = NetworkRepositoryManager.getData {
        moviesApi.getGenres()
    }.genres.map { it.toUI() }

    override suspend fun getMoviesCinema(): List<MovieUi> = NetworkRepositoryManager.getData {
        moviesApi.getMoviesNowPlaying()
    }.results.map { it.toUI() }

}