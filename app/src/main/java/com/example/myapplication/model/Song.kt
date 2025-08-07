package com.example.myapplication.model

data class Song(
    val id: String,
    val title: String,
    val uri: String,
    val icon: String? = null,
    val artist: String,
    val album: String,
    val lyricStyle: Int = 0,
    val lyric: String = "",
    val trackNumber: Int
)

object DiscoverPreviewSongData{
    val songs = listOf(
        Song(
            id = "1",
            title = "Bohemian Rhapsody",
            uri = "spotify:track:1",
            icon = "https://example.com/cover1.jpg",
            artist = "Queen",
            album = "A Night at the Opera",
            lyricStyle = 1,
            lyric = "[00:00] Is this the real life?",
            trackNumber = 1
        ),
        Song(
            id = "2",
            title = "Blinding Lights",
            uri = "spotify:track:2",
            icon = "https://example.com/cover2.jpg",
            artist = "The Weeknd",
            album = "After Hours",
            lyricStyle = 2,
            lyric = "[00:00] Yeah...",
            trackNumber = 5
        ),
        Song(
            id = "3",
            title = "Dynamite",
            uri = "spotify:track:3",
            icon = "",  // 测试无封面图情况
            artist = "BTS",
            album = "Dynamite (Single)",
            lyricStyle = 0,  // 默认歌词样式
            lyric = "",      // 测试无歌词情况
            trackNumber = 1
        ),
        Song(
            id = "4",
            title = "Shape of You",
            uri = "spotify:track:4",
            icon = "https://example.com/cover4.jpg",
            artist = "Ed Sheeran",
            album = "÷ (Divide)",
            lyricStyle = 3,
            lyric = "[00:00] The club isn't the best place...",
            trackNumber = 12  // 测试两位数曲目号
        ),
        Song(
            id = "5",
            title = "Yesterday",
            uri = "spotify:track:5",
            icon = "https://example.com/cover5.jpg",
            artist = "The Beatles",
            album = "Help!",
            lyricStyle = 1,
            lyric = "[00:00] Yesterday...",
            trackNumber = 3
        )
    )
}