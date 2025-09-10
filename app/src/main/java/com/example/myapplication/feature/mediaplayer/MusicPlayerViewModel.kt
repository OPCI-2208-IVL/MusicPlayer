package com.example.myapplication.feature.mediaplayer

import com.example.myapplication.data.repository.SongRepository
import com.example.myapplication.media.MediaServiceConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MusicPlayerViewModel @Inject constructor(
    mediaServiceConnection: MediaServiceConnection,
    songRepository: SongRepository
): BaseMediaPlayerViewModel(
    mediaServiceConnection = mediaServiceConnection,
    songRepository = songRepository
)