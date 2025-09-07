package com.example.myapplication.feature.mediaplayer

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.MimeTypes
import com.example.myapplication.media.MediaServiceConnection
import com.example.myapplication.model.Song
import com.example.myapplication.model.from
import com.example.myapplication.util.ResourceUtil
import kotlinx.coroutines.launch

open class BaseMediaPlayerViewModel(
    protected val mediaServiceConnection: MediaServiceConnection
): ViewModel() {
     fun setMediaAndPlay(
        songs: List<Song>,
        index: Int,
        navigateToMusicPlayer: Boolean = false
    ){
        viewModelScope.launch {
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
                                    setArtworkUri(Uri.parse(ResourceUtil.abs2rel(it.icon!!))) // Used by ExoPlayer and Notification
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
        }
    }
}