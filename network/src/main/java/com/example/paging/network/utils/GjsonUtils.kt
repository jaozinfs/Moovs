package com.example.paging.network.utils

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


object GjsonUtils {
    const val apiDataFormat = "yyyy-MM-dd'T'HH:mm:ss"
    val gsonDefault = GsonBuilder()
        .setDateFormat(apiDataFormat)
        .create()
}

object GJsonExtension {
    fun OkHttpClient.Builder.addLogInterceptor(): OkHttpClient.Builder {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        addNetworkInterceptor(logging)
        return this
    }
}