package com.example.paging.network

import retrofit2.Response

object NetworkRepositoryRequest {

    const val MOVIESTBAPI = "5280df22b26d2d69e023be5658c0ed0d"
    const val BASE_BACKDROP_IMAGE_PATTER = "http://image.tmdb.org/t/p/w500/"
    suspend fun <S> getData(
        api: suspend () -> Response<S>
    ): S {
        try {
            val response = api()
            return response.takeIf { it.isSuccessful }?.body()
                ?: throw Exception("Error in request")
        } catch (error: Exception) {
            error.printStackTrace()
            throw error
        }
    }

}
