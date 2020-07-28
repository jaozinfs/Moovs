package com.example.paging.movies.data.network.movies

import com.example.paging.database.local.entities.MovieEntity
import com.example.paging.movies.data.network.MOVIESTBAPI
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {
    @GET("movie/popular")
    suspend fun getMovies(@Query("page") page: Int, @Query("api_key") key: String = MOVIESTBAPI): Response<com.example.paging.database.local.entities.Response<MovieEntity>>
}