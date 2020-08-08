package com.jaozinfs.paging.movies.data.network

import com.jaozinfs.paging.database.local.entities.MovieEntity
import com.jaozinfs.paging.movies.data.network.model.MovieImagesNetwork
import com.jaozinfs.paging.movies.data.network.response.GenreResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import com.jaozinfs.paging.database.local.entities.Response as MoviesResponse

interface MoviesApi {
    @GET("movie/popular")
    suspend fun getMovies(@Query("page") page: Int, @Query("api_key") key: String = MOVIESTBAPI): Response<MoviesResponse<MovieEntity>>

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") key: String = MOVIESTBAPI,
        @Query("language") language: String = DEFAULT_LANGUAGE
    ): Response<GenreResponse>

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Int,
        @Query("api_key") key: String = MOVIESTBAPI,
        @Query("language") language: String = DEFAULT_LANGUAGE
    ): Response<MovieEntity>

    @GET("movie/{movieId}/images")
    suspend fun getMovieImages(
        @Path("movieId") movieId: Int,
        @Query("api_key") key: String = MOVIESTBAPI
    ): Response<MovieImagesNetwork>

    @GET("movie/now_playing")
    suspend fun getMoviesNowPlaying(
        @Query("api_key") key: String = MOVIESTBAPI,
        @Query("language") language: String = DEFAULT_LANGUAGE
    ): Response<MoviesResponse<MovieEntity>>
}