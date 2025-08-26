package com.example.myapplication.model.network.retrofit

import com.example.myapplication.model.Sheet
import com.example.myapplication.model.Song
import com.example.myapplication.model.ViewData
import com.example.myapplication.model.response.NetworkPageData
import com.example.myapplication.model.response.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ClientNetworkAPIService {
    @GET("v1/songs/page")
    suspend  fun songs(): NetworkResponse<NetworkPageData<Song>>

    @GET("v1/songs/info")
    suspend fun songDetail(
        @Query(value = "id")id: String,
    ) :NetworkResponse<Song>

    @GET("v1/indexes")
    suspend fun  index(
        @Query(value = "app")app: Int
    ):NetworkResponse<NetworkPageData<ViewData>>

    @GET("v1/sheets/info")
    suspend fun sheetDetail(
        @Query(value = "id")id: String
    ):NetworkResponse<Sheet>
}