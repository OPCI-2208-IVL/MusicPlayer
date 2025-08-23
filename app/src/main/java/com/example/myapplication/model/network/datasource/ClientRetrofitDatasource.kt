package com.example.myapplication.model.network.datasource

import com.example.myapplication.config.Config.ENDPOINT
import com.example.myapplication.model.Song
import com.example.myapplication.model.ViewData
import com.example.myapplication.model.network.di.NetworkModule
import com.example.myapplication.model.network.retrofit.ClientNetworkAPIService
import com.example.myapplication.model.response.NetworkPageData
import com.example.myapplication.model.response.NetworkResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.Query

object ClientRetrofitDatasource {
    private val service = Retrofit.Builder()
        .baseUrl(ENDPOINT)
        .callFactory(NetworkModule.providesOkHttpClient())
        .addConverterFactory(
            NetworkModule.providesNetworkJson().asConverterFactory("application/json".toMediaType()))
        .build()
        .create(ClientNetworkAPIService::class.java)

    suspend fun songs(): NetworkResponse<NetworkPageData<Song>> {
        return service.songs()
    }

    suspend fun songDetail(
        @Query(value = "id") id: String,
    ): NetworkResponse<Song> {
        return service.songDetail(id)
    }

    suspend fun  index(
        @Query(value = "app")app: Int
    ):NetworkResponse<NetworkPageData<ViewData>> {
        return service.index(app)
    }
}