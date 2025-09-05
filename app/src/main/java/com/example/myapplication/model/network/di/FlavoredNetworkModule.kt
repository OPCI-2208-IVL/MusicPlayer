package com.example.myapplication.model.network.di

import com.example.myapplication.model.network.datasource.ClientNetworkDatasource
import com.example.myapplication.model.network.datasource.ClientRetrofitDatasource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface FlavoredNetworkModule {
    @Binds
    fun binds(impl: ClientRetrofitDatasource): ClientNetworkDatasource
}