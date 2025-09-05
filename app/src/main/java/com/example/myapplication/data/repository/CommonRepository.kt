package com.example.myapplication.data.repository

import com.example.myapplication.model.network.datasource.ClientNetworkDatasource
import javax.inject.Inject

class CommonRepository @Inject constructor(
    private val networkDatasource: ClientNetworkDatasource
)  {
    suspend fun indexes(
        app: Int = 30,
    )= networkDatasource.index(
        app = app
    )
}