package com.example.myapplication.media.di

import android.content.ComponentName
import android.content.Context
import com.example.myapplication.data.repository.SongRepository
import com.example.myapplication.data.repository.UserDataRepository
import com.example.myapplication.media.MediaService
import com.example.myapplication.media.MediaServiceConnection
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MediaModule {
    @Provides
    fun provideMusicServiceConnection(
        @ApplicationContext context: Context,
        songRepository: SongRepository,
        userDataRepository: UserDataRepository
    ): MediaServiceConnection{
        return MediaServiceConnection.getInstance(
            context,
            ComponentName(
                context,
                MediaService::class.java
            ),
            songRepository,
            userDataRepository
        )
    }
}