package com.example.myapplication.media

import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService

class MediaService: MediaSessionService() {
    private var mediaSession:MediaSession? = null
    private val replaceableForwardingPlayer: ReplaceableForwardingPlayer by lazy {
        ReplaceableForwardingPlayer(player)
    }


    override fun onCreate() {
        super.onCreate()
        mediaSession = MediaSession.Builder(this, replaceableForwardingPlayer)
            .setCallback(getCallback())
            .build()
    }

    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        super.onDestroy()
    }

    fun getCallback(): MediaSession.Callback {
        return MediaServiceCallback()
    }

    open inner class MediaServiceCallback : MediaSession.Callback {

    }

    private val player: Player by lazy {
        val player = ExoPlayer.Builder(this).build().apply {
        setAudioAttributes(audioAttributes, true)
        setHandleAudioBecomingNoisy(true)
        addListener(playerEventListener)
    }
//        player.addAnalyticsListener(EventLogger(null, "exoplayer-uamp"))
        player
    }

    private val playerEventListener = PlayerEventListener()

    private inner class PlayerEventListener : Player.Listener {
        override fun onEvents(player: Player, events: Player.Events) {
            if (events.contains(Player.EVENT_POSITION_DISCONTINUITY)
                || events.contains(Player.EVENT_MEDIA_ITEM_TRANSITION)
                || events.contains(Player.EVENT_PLAY_WHEN_READY_CHANGED)
            ) {
                currentMediaItemIndex = player.currentMediaItemIndex
//                saveRecentSongToStorage()
            }
        }
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }

    private var currentMediaItemIndex: Int = 0

}