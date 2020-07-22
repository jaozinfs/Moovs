package com.example.paging3.data.repository.network

import retrofit2.Response

object NetworkRepositoryRequest {

    const val MOVIESTBAPI = "5280df22b26d2d69e023be5658c0ed0d"

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
