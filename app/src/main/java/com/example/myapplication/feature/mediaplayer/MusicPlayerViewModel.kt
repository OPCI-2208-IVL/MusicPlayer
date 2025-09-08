package com.example.myapplication.feature.mediaplayer

import com.example.myapplication.media.MediaServiceConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MusicPlayerViewModel @Inject constructor(
    mediaServiceConnection: MediaServiceConnection
): BaseMediaPlayerViewModel(
    mediaServiceConnection = mediaServiceConnection
)