package com.example.myapplication.data.repository

import com.example.myapplication.model.Session
import com.example.myapplication.model.User
import com.example.myapplication.model.network.datasource.ClientNetworkDatasource
import com.example.myapplication.model.response.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.http.Body
import javax.inject.Inject

class SessionRepository @Inject constructor(
    private val networkDatasource: ClientNetworkDatasource
) {
    suspend fun login(
        @Body data: User
    ): Flow<NetworkResponse<Session>> =
        flow {
            emit(
                networkDatasource.login(data)
            )
    }.flowOn(kotlinx.coroutines.Dispatchers.IO)
}