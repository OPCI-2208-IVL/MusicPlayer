package com.example.myapplication.model

import androidx.media3.common.MediaMetadata
import com.example.myapplication.database.model.SongEntity
import com.example.myapplication.util.ResourceUtil
import kotlinx.serialization.Serializable

@Serializable
data class Song(
    val id: String,
    val title: String,
    val uri: String,
    val icon: String,
    val album: String,
    val artist: String,
    val genre: String,
    val lyricStyle: Int = 0,
    val lyric: String = "",
    val trackNumber: Int = 1,
    val totalTrackCount: Int = 1,
    val duration: Int=0,
){
    fun toSongEntity(): SongEntity {
        return SongEntity(
            id = id,
            title = title,
            uri = ResourceUtil.abs2rel(uri),
            icon = icon,
            album = album,
            artist = artist,
            genre = genre,
            lyricStyle = lyricStyle,
            lyric = lyric,
            trackNumber = trackNumber,
            totalTrackCount = totalTrackCount,
            playList = true,
        )
    }
}

fun MediaMetadata.Builder.from(data: Song): MediaMetadata.Builder {
    setTitle(data.title)
    setDisplayTitle(data.title)
    setArtist(data.artist)
    setAlbumTitle(data.album)
    setGenre(data.genre)
    setTrackNumber(data.trackNumber)
    setTotalTrackCount(data.totalTrackCount)
    setIsBrowsable(false)
    setMediaType(MediaMetadata.MEDIA_TYPE_MUSIC)
    setIsPlayable(true)
    // The duration from the JSON is given in seconds, but the rest of the code works in
    // milliseconds. Here's where we convert to the proper units.
    //    val durationMs = TimeUnit.SECONDS.toMillis(data.duration)
    //    val bundle = Bundle()
    //    bundle.putLong("durationMs", durationMs)
    return this
}

object DiscoverPreviewSongData{
    val songs = listOf(
        Song(
            id = "001",
            title = "Starlight Serenade",
            uri = "content://media/audio/001",
            icon = "https://example.com/cover1.jpg",
            album = "Midnight Dreams",
            artist = "Luna Waves",
            genre = "Synthwave",
            lyricStyle = 1,
            lyric = "[Verse 1]\nUnder the neon sky...",
            trackNumber = 3,
            totalTrackCount = 12,
            duration = 237  // 3分57秒
        ),
        Song(
            id = "002",
            title = "Desert Highway",
            uri = "content://media/audio/002",
            icon = "",  // 测试无封面图情况
            album = "Dust and Echoes",
            artist = "The Nomads",
            genre = "Rock",
            lyricStyle = 0,  // 无歌词
            trackNumber = 1,
            totalTrackCount = 8,
            duration = 184  // 3分04秒
        ),
        Song(
            id = "003",
            title = "Ocean's Whisper (feat. Marine)",
            uri = "content://media/audio/003",
            icon = "https://example.com/cover3.png",
            album = "Deep Blue",
            artist = "Coral Sound ft. Marine",
            genre = "Ambient",
            lyricStyle = 2,
            lyric = "Waves crashing...",  // 简单歌词样式
            trackNumber = 5,
            totalTrackCount = 10,
            duration = 318  // 5分18秒
        ),
        Song(
            id = "004",
            title = "City Lights",
            uri = "content://media/audio/004",
            icon = "https://example.com/cover4.png",
            album = "Urban Tales",  // 测试无artist情况
            artist = "",
            genre = "Electronic",
            trackNumber = 7,
            totalTrackCount = 15,
            duration = 276  // 4分36秒
        ),
        Song(
            id = "005",
            title = "Mountain Top",  // 测试超长标题
            uri = "content://media/audio/005",
            icon = "https://example.com/long_cover_url_here.jpg",
            album = "Nature's Symphony: The Complete Wilderness Collection",
            artist = "Forest Echo Ensemble",
            genre = "Classical",
            lyricStyle = 1,
            lyric = "Climbing higher...",
            trackNumber = 12,
            totalTrackCount = 12,  // 最后一首
            duration = 422  // 7分02秒
        )
    )
}