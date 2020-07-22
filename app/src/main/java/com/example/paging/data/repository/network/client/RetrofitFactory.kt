package com.example.paging.data.repository.network.client

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitFactory {

    inline fun <T> factoryApiClient(
        api: Class<T>,
        baseUrl: String
    ): T {
        val client = OkHttpClient.Builder().run {
            connectTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            addLogInterceptor()
            build()
        }

        return Retrofit.Builder().run {
            client(client)
            addConverterFactory(GsonConverterFactory.create(gsonDefault))
            baseUrl(baseUrl)
            build()
        }.create(api)
    }

}

internal const val apiDataFormat = "yyyy-MM-dd'T'HH:mm:ss"
val gsonDefault = GsonBuilder()
    .setDateFormat(apiDataFormat)
    .create()

fun OkHttpClient.Builder.addLogInterceptor(): OkHttpClient.Builder {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        addNetworkInterceptor(logging)
    return this
}