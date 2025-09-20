package com.example.myapplication.model.lyric

import android.content.Context
import com.example.myapplication.data.repository.SongRepository
import com.example.myapplication.data.repository.UserDataRepository
import com.example.myapplication.media.MediaServiceConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LyricManager(
    val context: Context,
    val mediaServiceConnection: MediaServiceConnection,
    val songRepository: SongRepository,
    val userDataRepository: UserDataRepository
) {
    val lyric = MutableStateFlow(Lyric.EMPTY)
    private val nowPlaying = mediaServiceConnection.nowPlaying
    private val applicationScope = CoroutineScope(SupervisorJob())

    init {
        applicationScope.launch {
            nowPlaying.collectLatest {
                prepareLyric(it.mediaId)
            }
        }
    }

    private suspend fun prepareLyric(mediaId: String) {
        if (lyric.value.songId != mediaId) {
            try {
                val song = songRepository.songDetail(mediaId)
                if (song.isSucceeded){
                 if(! song.data?.lyric.isNullOrEmpty()){
                     lyric.value = LyricParser.parse(
                         song.data!!.lyricStyle,
                         song.data.lyric
                     ).copy(
                            songId = mediaId
                     )
                 } else {
                     lyric.value = Lyric.EMPTY
                 }
                }
            } catch (e: Exception) {
                lyric.value = Lyric.EMPTY
            }
        }
    }
}