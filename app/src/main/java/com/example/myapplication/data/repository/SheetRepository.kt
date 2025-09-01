package com.example.myapplication.data.repository

import com.example.myapplication.model.Sheet
import com.example.myapplication.model.network.datasource.ClientRetrofitDatasource
import com.example.myapplication.model.response.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SheetRepository() {
    fun sheetDetail(
        id: String
    ): Flow<NetworkResponse<Sheet>> = flow {
        emit(
            ClientRetrofitDatasource.sheetDetail(id)
        )
    }.flowOn(Dispatchers.IO)
}