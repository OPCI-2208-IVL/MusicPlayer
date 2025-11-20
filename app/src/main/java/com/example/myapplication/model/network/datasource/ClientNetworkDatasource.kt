package com.example.myapplication.model.network.datasource

import com.example.myapplication.model.BaseID
import com.example.myapplication.model.Session
import com.example.myapplication.model.Sheet
import com.example.myapplication.model.Song
import com.example.myapplication.model.User
import com.example.myapplication.model.ViewData
import com.example.myapplication.model.response.NetworkPageData
import com.example.myapplication.model.response.NetworkResponse

interface ClientNetworkDatasource {
    suspend fun songs(): NetworkResponse<NetworkPageData<Song>>

    suspend fun songDetail(
        id: String
    ): NetworkResponse<Song>

    suspend fun  index(
        app: Int
    ): NetworkResponse<NetworkPageData<ViewData>>

    suspend fun sheetDetail(
        id: String
    ): NetworkResponse<Sheet>

    suspend fun login(
        data: User
    ): NetworkResponse<Session>

    suspend fun register(
        data: User
    ): NetworkResponse<BaseID>
}