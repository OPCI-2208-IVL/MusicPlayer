package com.example.myapplication.feature.main

import com.example.myapplication.data.repository.SongRepository
import com.example.myapplication.data.repository.UserDataRepository
import com.example.myapplication.feature.mediaplayer.MusicPlayerViewModel
import com.example.myapplication.media.MediaServiceConnection
import com.example.myapplication.model.lyric.LyricManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    songRepository: SongRepository,
    mediaServiceConnection: MediaServiceConnection,
    userDataRepository: UserDataRepository,
    lyricManager: LyricManager
) : MusicPlayerViewModel(
    mediaServiceConnection = mediaServiceConnection,
    songRepository = songRepository,
    userDataRepository = userDataRepository,
    lyricManager = lyricManager
) {
}