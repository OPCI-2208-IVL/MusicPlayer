package com.example.myapplication.data.repository

import com.example.myapplication.model.BaseID
import com.example.myapplication.model.User
import com.example.myapplication.model.network.datasource.ClientNetworkDatasource
import com.example.myapplication.model.response.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.http.Body
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val networkDatasource: ClientNetworkDatasource
) {
    suspend fun register(
        @Body data: User
    ):Flow<NetworkResponse<BaseID>> =
        flow {
            emit(
                networkDatasource.register(data)
            )
        }.flowOn(Dispatchers.IO)
}