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
    fun sheetDetail(
        id: String
    ): Flow<NetworkResponse<Sheet>> = flow {
        emit(
             networkDatasource.sheetDetail(id)
        )
    }.flowOn(Dispatchers.IO)
}