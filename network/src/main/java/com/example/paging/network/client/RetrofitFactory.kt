package com.example.paging.network.client

import com.example.paging.network.utils.GJsonExtension.addLogInterceptor
import com.example.paging.network.utils.GjsonUtils.gsonDefault
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitFactory {

    fun <T> factoryApiClient(
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

