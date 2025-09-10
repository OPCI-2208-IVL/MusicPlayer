package com.example.myapplication.database.di

import android.content.Context
import androidx.room.Room
import com.example.myapplication.MyAppState
import com.example.myapplication.database.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun providesLocalDatabase(
        @ApplicationContext context:Context
    ): LocalDatabase {
        if (MyAppState.localDatabase == null) {
            val databaseName = "my_database_${MyAppState.userId}"
            MyAppState.localDatabase = Room.databaseBuilder(
                context,
                LocalDatabase::class.java,
                databaseName,
            ).build()
        }
        return MyAppState.localDatabase!!
    }
}