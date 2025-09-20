package com.example.myapplication.model.lyric

data class Lyric(
    val datum: List<LyricLine>,
    val accurate: Boolean = false,
    val songId: String = "",
){
    companion object {
        val EMPTY = Lyric(emptyList(),false)
    }
}
