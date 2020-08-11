package com.jaozinfs.paging.tvs.data.network

import com.jaozinfs.paging.tvs.data.DEFAULT_LANGUAGE
import com.jaozinfs.paging.tvs.data.MOVIESTBAPI
import com.jaozinfs.paging.tvs.data.network.response.TvDetailsResponse
import com.jaozinfs.paging.tvs.data.network.response.TvResponse
import com.jaozinfs.paging.tvs.data.network.response.TvsBaseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvsApi {
    @GET("tv/popular")
    suspend fun getTvsPopular(
        @Query("api_key") key: String = MOVIESTBAPI,
        @Query("language") language: String = DEFAULT_LANGUAGE
    ): Response<TvsBaseResponse<TvResponse>>

    @GET("tv/on_the_air")

    suspend fun getTvsOnAir(
        @Query("api_key") key: String = MOVIESTBAPI,
        @Query("language") language: String = DEFAULT_LANGUAGE
    ): Response<TvsBaseResponse<TvResponse>>

    @GET("tv/{tv_id}")
    suspend fun getTvDetails(
        @Path("tv_id") tvID: Int,
        @Query("api_key") key: String = MOVIESTBAPI,
        @Query("language") language: String = DEFAULT_LANGUAGE
    ): Response<TvDetailsResponse>
}