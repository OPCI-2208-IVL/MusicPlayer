package com.example.myapplication.model.lyric

data class LyricLine(

    val data: String,

    val startTime: Long = 0,

    val words: List<String> = emptyList(),

    val wordDurations: List<Int> = emptyList(),

    var endTime: Long = 0,
)

