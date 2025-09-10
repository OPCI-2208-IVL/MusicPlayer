package com.example.myapplication.database.di

import com.example.myapplication.database.LocalDatabase
import com.example.myapplication.database.dao.SongDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun providesSongDao(
        database: LocalDatabase,
    ): SongDao = database.songDao()
}