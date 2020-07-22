package com.example.paging.network.client

import com.example.paging.network.utils.GJsonExtension.addLogInterceptor
import com.example.paging.network.utils.GjsonUtils.gsonDefault
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Este objeto auxilia na criação de api
 *
 */
object RetrofitFactory {

    /**
     * Cria a api a partir da interface com as anotações dos endpoints
     * @param baseUrl a partir da base url é criada a API
     * @return API
     */
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

