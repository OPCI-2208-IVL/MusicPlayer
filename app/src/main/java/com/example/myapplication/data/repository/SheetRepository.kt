package com.example.myapplication.data.repository

import com.example.myapplication.model.Sheet
import com.example.myapplication.model.network.datasource.ClientNetworkDatasource
import com.example.myapplication.model.response.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SheetRepository @Inject constructor(
    private val networkDatasource: ClientNetworkDatasource
) {

    fun createSheet(
        data:Sheet
    ): Flow<NetworkResponse<Sheet>> = flow<NetworkResponse<Sheet>> {
        emit(
            networkDatasource.createSheet(data)
        )
    }.flowOn(Dispatchers.IO)

    suspend fun createSheets(
        userId: String
    ) = networkDatasource.createSheets(userId)

    suspend fun collectSheets(
        userId: String
    ) = networkDatasource.collectSheets(userId)

    fun sheetDetail(
        id: String
    ): Flow<NetworkResponse<Sheet>> = flow {
        emit(
             networkDatasource.sheetDetail(id)
        )
    }.flowOn(Dispatchers.IO)

    fun cancelCollectSheet(sheetID: String) = flow {
        emit(
            networkDatasource.cancelCollectSheet(sheetID)
        )
    }.flowOn(Dispatchers.IO)

    fun collectSheet(sheetID: String) = flow {
        emit(
            networkDatasource.collectSheet(sheetID)
        )
    }.flowOn(Dispatchers.IO)

    fun updateSheet(data: Sheet) = flow {
        emit(
            networkDatasource.updateSheet(data)
        )
    }.flowOn(Dispatchers.IO)
}