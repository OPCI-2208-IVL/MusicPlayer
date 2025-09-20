package com.example.myapplication.feature.mediaplayer

import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.SongRepository
import com.example.myapplication.data.repository.UserDataRepository
import com.example.myapplication.media.MediaServiceConnection
import com.example.myapplication.model.lyric.LyricManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class MusicPlayerViewModel @Inject constructor(
    mediaServiceConnection: MediaServiceConnection,
    songRepository: SongRepository,
    userDataRepository: UserDataRepository,
    protected val lyricManager: LyricManager
): BaseMediaPlayerViewModel(
    mediaServiceConnection = mediaServiceConnection,
    songRepository = songRepository,
    userDataRepository = userDataRepository
) {
    val lyric = lyricManager.lyric

    val showRecord = MutableStateFlow(true)

    val recordRotation = MutableStateFlow(0f)

    private val pauseRecordRotation = MutableStateFlow(false)

    init {
        collectCurrentPosition()
    }

    private fun collectCurrentPosition() {
        viewModelScope.launch {
            mediaServiceConnection.currentPosition.collectLatest {
                if (!pauseRecordRotation.value) {
                    if (recordRotation.value > 360f)
                        recordRotation.value = 0f
                    recordRotation.value += ROTATION_PER
                }
                
            }
        }
    }

    fun clearPosition() {
        recordRotation.value = 0f
    }

    fun onClearPlayListClick() {
        hideMusicListDialog()
        mediaServiceConnection.clearAll()
        viewModelScope.launch {
            songRepository.deleteAll()
        }
    }

    fun onItemPlayListClick(index: Int) {
        playIndex(index)
    }

    fun onItemMusicDeleteClick(index: Int) {
        mediaServiceConnection.delete(index)
        viewModelScope.launch {
            songRepository.delete(playListDatum.value[index])
            if (playListDatum.value.isEmpty())
                hideMusicListDialog()
        }
    }

    fun playIndex(index: Int) {
        mediaServiceConnection.playIndex(index)
    }

    fun onPauseRecordRotation(b: Boolean) {
        pauseRecordRotation.value = b
    }

    fun toggleRecordAndLyric() {
        showRecord.value = !showRecord.value
    }

    companion object {
        private const val ROTATION_PER = 0.2304f
    }
}