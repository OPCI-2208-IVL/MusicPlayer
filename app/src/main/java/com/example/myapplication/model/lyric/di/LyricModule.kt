package com.example.myapplication.model.lyric.di

import android.content.Context
import com.example.myapplication.data.repository.SongRepository
import com.example.myapplication.data.repository.UserDataRepository
import com.example.myapplication.media.MediaServiceConnection
import com.example.myapplication.model.lyric.LyricManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LyricModule{
    @Provides
    @Singleton
    fun providesLyricManager(
        @ApplicationContext context: Context,
        mediaServiceConnection: MediaServiceConnection,
        userDataRepository: UserDataRepository,
        songRepository: SongRepository
    ): LyricManager {
        return LyricManager(
            context = context,
            mediaServiceConnection = mediaServiceConnection,
            userDataRepository = userDataRepository,
            songRepository = songRepository
        )
    }
}