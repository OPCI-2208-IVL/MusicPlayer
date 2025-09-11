package com.example.myapplication.data.di

import com.example.myapplication.data.repository.LocalUserDataRepository
import com.example.myapplication.data.repository.UserDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    @Singleton
    internal abstract fun bindsUserDataRepository(
        userDataRepository: LocalUserDataRepository,
    ): UserDataRepository
}