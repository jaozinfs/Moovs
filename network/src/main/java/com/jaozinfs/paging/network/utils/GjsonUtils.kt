package com.jaozinfs.paging.network.utils

import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.jaozinfs.paging.network.utils.GjsonUtils.gsonDefault
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


object GjsonUtils {
    const val apiDataFormat = "yyyy-MM-dd'T'HH:mm:ss"
    const val apiDataFormatBrazil = "dd-MM-yyyy'T'HH:mm:ss"
    val gsonDefault = GsonBuilder()
        .setDateFormat(apiDataFormatBrazil)
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
@Throws(JsonSyntaxException::class)
inline fun <reified T> fromJson(json: String): T {
    return gsonDefault.fromJson(json, object : TypeToken<T>() {}.type)
}