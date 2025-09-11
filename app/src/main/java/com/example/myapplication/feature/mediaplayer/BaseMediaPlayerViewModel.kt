package com.example.myapplication.feature.mediaplayer

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.MimeTypes
import com.example.myapplication.data.repository.SongRepository
import com.example.myapplication.data.repository.UserDataRepository
import com.example.myapplication.media.MediaServiceConnection
import com.example.myapplication.model.Song
import com.example.myapplication.model.from
import com.example.myapplication.util.ResourceUtil
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

open class BaseMediaPlayerViewModel(
    private val mediaServiceConnection: MediaServiceConnection,
    private val songRepository: SongRepository,
    userDataRepository: UserDataRepository
): ViewModel() {

    var toMusicPlayer = mutableStateOf<Boolean>(false)

    val nowPlaying = mediaServiceConnection.nowPlaying

    val playbackState = mediaServiceConnection.playbackState

    val currentPosition = mediaServiceConnection.currentPosition.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0,
    )

    val playListDatum = songRepository.getAllPlayList().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

     fun setMediaAndPlay(
        songs: List<Song>,
        index: Int,
        navigateToMusicPlayer: Boolean = false
     ){
        viewModelScope.launch {

            songRepository.clearAllPlayList()
            songRepository.insertList(songs.map {
                it.toSongEntity()
            })

            val mediaItems = songs.map {
                MediaItem.Builder()
                    .apply {
                        setMediaId(it.id)
                        setUri(ResourceUtil.abs2rel(it.uri))
                        setMimeType(MimeTypes.AUDIO_MPEG)
                        setMediaMetadata(
                            MediaMetadata.Builder()
                                .from(it)
                                .apply {
                                    setArtworkUri(Uri.parse(ResourceUtil.abs2rel(it.icon))) // Used by ExoPlayer and Notification
                                    // Keep the original artwork URI for being included in Cast metadata object.
//                                        val extras = Bundle()
//                                        extras.putString(ORIGINAL_ARTWORK_URI_KEY, it.image)
//                                        setExtras(extras)
                                }
                                .build()
                        )
                    }.build()
            }.toList()

            mediaServiceConnection.setMediaAndPlay(mediaItems,index)

            toMusicPlayer.value = navigateToMusicPlayer
        }
     }

    fun clearMusicPlayer(){
        toMusicPlayer.value = false
    }

    fun onSeek(fl: Float) {
        mediaServiceConnection.seekTo(fl.toLong())
    }

    fun onPreviousClick() {
        mediaServiceConnection.seekToPrevious()
    }

    fun onPlayOrPauseClick() {
        mediaServiceConnection.playOrPause()
    }

    fun onNextClick() {
        mediaServiceConnection.seekToNext()
    }
}