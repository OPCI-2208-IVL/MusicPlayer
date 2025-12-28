package com.example.myapplication.model.network.retrofit

import com.example.myapplication.model.BaseID
import com.example.myapplication.model.BaseModel
import com.example.myapplication.model.Session
import com.example.myapplication.model.Sheet
import com.example.myapplication.model.Song
import com.example.myapplication.model.User
import com.example.myapplication.model.ViewData
import com.example.myapplication.model.response.NetworkPageData
import com.example.myapplication.model.response.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ClientNetworkAPIService {
    @GET("v1/songs/page")
    suspend  fun songs(): NetworkResponse<NetworkPageData<Song>>

    @GET("v1/songs/info")
    suspend fun songDetail(
        @Query(value = "id")id: String,
    ): NetworkResponse<Song>

    @GET("v1/users/{userId}/create")
    suspend fun createSheets(
        @Path("userId") userId: String
    ): NetworkResponse<NetworkPageData<Sheet>>

    @GET("v1/users/{userId}/collect")
    suspend fun collectSheets(
        @Path("userId") userId: String
    ): NetworkResponse<NetworkPageData<Sheet>>

    @POST("v1/sheets/add")
    suspend fun createSheet(
        @Body data: Sheet
    ): NetworkResponse<Sheet>

    @POST("v1/sheets/update")
    suspend fun updateSheet(
        @Body data: Sheet
    ): NetworkResponse<Sheet>

    @GET("v1/indexes")
    suspend fun  index(
        @Query(value = "app")app: Int
    ):NetworkResponse<NetworkPageData<ViewData>>

    @GET("v1/sheets/info")
    suspend fun sheetDetail(
        @Query(value = "id")id: String
    ):NetworkResponse<Sheet>

    @POST("v1/login")
    suspend fun login(
        @Body data: User
    ): NetworkResponse<Session>

    @POST("v1/users/add")
    suspend fun register(
        @Body data: User
    ): NetworkResponse<BaseID>

    @POST("v1/collects/add")
    suspend fun collectSheet(
        @Body data: BaseID
    ): NetworkResponse<BaseModel>

    @POST("v1/collects/delete")
    suspend fun cancelCollectSheet(
        @Body data: BaseID
    ): NetworkResponse<BaseModel>
}