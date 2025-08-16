package com.example.myapplication.model.network.di

import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object NetworkModule {
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    fun okHttpCallFactory(
        okHttpClient: OkHttpClient,
    ): Call.Factory = okHttpClient


    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS) //连接超时时间
            .writeTimeout(10, TimeUnit.SECONDS) //写超时时间
            .readTimeout(10, TimeUnit.SECONDS)

            .build()
    }
}