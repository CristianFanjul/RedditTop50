package com.example.reddittop50.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitFactory {
    const val BASE_URL = "https://www.reddit.com/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    fun articlesRetrofitService(): IApiClient {
        val client = getOkHttpClient(loggingInterceptor)
        return getRetrofitService(client).create(IApiClient::class.java)
    }

    private fun getRetrofitService(okHttpClient: OkHttpClient? = null): Retrofit {
        return Retrofit.Builder().also {
            if (okHttpClient != null) {
                it.client(okHttpClient)
            }
        }.baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getOkHttpClient(vararg interceptors: Interceptor? = arrayOf()): OkHttpClient {
        return OkHttpClient.Builder().run {
            interceptors.forEach { interceptor -> interceptor?.let { addInterceptor(it) } }
            connectTimeout(1, TimeUnit.MINUTES)
            readTimeout(1, TimeUnit.MINUTES)
            writeTimeout(1, TimeUnit.MINUTES)
            build()
        }
    }
}