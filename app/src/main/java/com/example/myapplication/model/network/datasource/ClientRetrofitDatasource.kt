package com.example.myapplication.model.network.datasource

import com.example.myapplication.config.Config.ENDPOINT
import com.example.myapplication.model.Sheet
import com.example.myapplication.model.Song
import com.example.myapplication.model.ViewData
import com.example.myapplication.model.network.retrofit.ClientNetworkAPIService
import com.example.myapplication.model.response.NetworkPageData
import com.example.myapplication.model.response.NetworkResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.Query
import javax.inject.Inject

class ClientRetrofitDatasource @Inject constructor(
    networkJson: Json,
    okHttpCallFactory: Call.Factory
) : ClientNetworkDatasource {
    private val service = Retrofit.Builder()
        .baseUrl(ENDPOINT)
        .callFactory(okHttpCallFactory)
        .addConverterFactory(
            networkJson.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(ClientNetworkAPIService::class.java)

    override suspend fun songs(): NetworkResponse<NetworkPageData<Song>> {
        return service.songs()
    }

    override suspend fun songDetail(
        @Query(value = "id")id: String,
    ): NetworkResponse<Song> {
        return service.songDetail(id)
    }

    override suspend fun  index(
        @Query(value = "app")app: Int
    ):NetworkResponse<NetworkPageData<ViewData>> {
        return service.index(app)
    }

    override suspend fun sheetDetail(
        @Query(value = "id")id: String
    ):NetworkResponse<Sheet> {
        return service.sheetDetail(id)
    }
}