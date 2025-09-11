package com.example.myapplication.media

import android.content.ComponentName
import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.example.myapplication.data.repository.SongRepository
import com.example.myapplication.data.repository.UserDataRepository
import com.example.myapplication.database.model.SongEntity
import com.example.myapplication.database.model.asMediaItem
import com.example.myapplication.model.PlaybackMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.guava.await
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MediaServiceConnection(
    context: Context,
    serviceComponent: ComponentName,
    private val songRepository: SongRepository,
    private val userDataRepository: UserDataRepository
) {
    private var mediaController: MediaController? = null

    private val coroutineContext: CoroutineContext = Dispatchers.Main

    private val scope = CoroutineScope(coroutineContext + SupervisorJob())

    private lateinit var currentPlayer: Player

    private val playerListener: PlayerListener = PlayerListener()

    private var publicProgressJob: Job? = null

    val nowPlaying = MutableStateFlow<MediaItem>(NOTHING_PLAYING)

    val networkFailure = MutableStateFlow<Boolean>(false)

    val playbackState = MutableStateFlow<PlaybackState>(EMPTY_PLAYBACK_STATE)

    val currentPosition = MutableStateFlow<Long>(0)

    private var lastPosition: Long = 0

    private inner class PlayerListener : Player.Listener {

        override fun onEvents(player: Player, events: Player.Events) {
            currentPlayer = player
            if (events.contains(Player.EVENT_PLAY_WHEN_READY_CHANGED)
                || events.contains(Player.EVENT_PLAYBACK_STATE_CHANGED)
                || events.contains(Player.EVENT_MEDIA_ITEM_TRANSITION)
            ) {
                updatePlaybackState(player)
                if (player.playbackState != Player.STATE_IDLE) {

                    networkFailure.value = false
                }
            }
            if (events.contains(Player.EVENT_MEDIA_METADATA_CHANGED)
                || events.contains(Player.EVENT_MEDIA_ITEM_TRANSITION)
                || events.contains(Player.EVENT_PLAY_WHEN_READY_CHANGED)
            ) {

                updateNowPlaying(player)

//                player.currentMediaItem?.mediaId?.let {
//                    if (lyric.value.songId != it && Strings.isNullOrEmpty(lyric.value.lyric)) {
//                        scope.launch {
//                            val songDetail = songRepository.songDetail(it)
//                            if (songDetail.isSucceeded) {
//                                lyric.value = songDetail.data!!.lyric
//                            }
//                        }
//                    }
//                }
//
//                lyric.value.songId !=
//                prepareLyric(player.currentMediaItem.mediaId)
            }
        }

        override fun onPlayerErrorChanged(error: PlaybackException?) {
            error?.printStackTrace()
            when (error?.errorCode) {
                PlaybackException.ERROR_CODE_IO_BAD_HTTP_STATUS,
                PlaybackException.ERROR_CODE_IO_INVALID_HTTP_CONTENT_TYPE,
                PlaybackException.ERROR_CODE_IO_CLEARTEXT_NOT_PERMITTED,
                PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_FAILED,
                PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_TIMEOUT -> {
//                    networkFailure.value = true
                }
            }
        }
    }

    init {
        scope.launch {
            val sessionToken = SessionToken(context, serviceComponent)

            mediaController = MediaController
                .Builder(context,sessionToken)
                .setListener(MediaControllerListener())
                .buildAsync()
                .await()

            mediaController!!.addListener(playerListener)

            initPlayList()
        }
    }

    private suspend fun initPlayList(){
        val datum = songRepository.getAllPlayListAsync()
        if (datum.isEmpty()) return

        val userData = userDataRepository.userData.first()
        val startIndex = datum.indexOfFirst { it.id == userData.playMusicId }

        mediaController?.run {
            setMedias(datum.map(SongEntity::asMediaItem), startIndex, userData.playProgress)
            if (userData.playRepeatMode.ordinal == PlaybackMode.REPEAT_SHUFFLE.ordinal) {
                shuffleModeEnabled = true

                repeatMode = Player.REPEAT_MODE_ALL
            } else {
                shuffleModeEnabled = false
                repeatMode =
                    if (userData.playRepeatMode.ordinal == PlaybackMode.REPEAT_ONE.ordinal) {
                        Player.REPEAT_MODE_ONE
                    } else
                        Player.REPEAT_MODE_ALL
            }

            prepare()
        }

    }

    fun updateNowPlaying(
        player: Player
    ){
        val mediaItem = player.currentMediaItem ?: MediaItem.EMPTY
        if (mediaItem == MediaItem.EMPTY){
            return
        }
        nowPlaying.value = mediaItem
    }

    fun setMediaAndPlay(datum: List<MediaItem>, startIndex: Int){
        setMedias(datum,startIndex)
        mediaController!!.prepare()
        mediaController!!.play()
    }

    private fun setMedias(datum: List<MediaItem>, startIndex: Int, startPositionMs: Long = 0) {
        mediaController!!.setMediaItems(
            datum, startIndex, startPositionMs
        )
    }

    fun updatePlaybackState(player: Player){
        playbackState.value =
            PlaybackState(
                player.playbackState,
                player.playWhenReady,
                player.duration
            )
        currentPosition.value = player.currentPosition
        checkPublishProgressTask()
    }

    private fun checkPublishProgressTask(){
        if (playbackState.value.isPlaying){
            if (publicProgressJob != null){
                return
            }

            publicProgressJob = scope.launch {
                while (playbackState.value.isPlaying){
                    currentPosition.value = mediaController?.currentPosition?: 0
                    delay(16)
                    if (currentPosition.value - lastPosition >= 2000) {
                        userDataRepository.saveRecentSong(
                            mediaController!!.currentMediaItem!!.mediaId,
                            currentPosition.value,
                            playbackState.value.duration
                        )
                        lastPosition = currentPosition.value
                    }

                }
            }
        } else {
            publicProgressJob?.cancel()
            publicProgressJob = null
        }
    }

    fun playOrPause(){
        mediaController?.run {
            if(isPlaying)
                pause()
            else
                play()
        }
    }

    private inner class MediaControllerListener : MediaController.Listener {
        override fun onDisconnected(controller: MediaController) {
            release()
        }
    }

    fun release() {
        nowPlaying.value = NOTHING_PLAYING
        mediaController?.let {
            it.removeListener(playerListener)
            it.release()
        }
        instance = null
    }

    fun seekTo(position: Long) {
        mediaController?.run {
            seekTo(position)
            if (!isPlaying)
                play()
        }
    }

    fun seekToPrevious() {
        mediaController?.run {
            seekToPrevious()
            play()
        }
    }

    fun seekToNext() {
        mediaController?.run {
            seekToNext()
            play()
        }
    }

    companion object{
        private const val TAG = "MediaServiceConnection"

        @Volatile
        private var instance: MediaServiceConnection? = null

        fun getInstance(
            context: Context,
            serviceComponent: ComponentName,
            songRepository: SongRepository,
            userDataRepository: UserDataRepository
        ) =
            instance ?: synchronized(this){
                instance ?: MediaServiceConnection(context,serviceComponent, songRepository, userDataRepository ).also {
                    instance = it
                }
            }
    }
}