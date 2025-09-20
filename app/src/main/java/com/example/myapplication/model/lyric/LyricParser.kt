package com.example.myapplication.model.lyric

import com.example.myapplication.util.Constant.KSC

object LyricParser{
    fun parse(type: Int, content: String): Lyric {
        if (type == KSC) {
            return KSCLyricParser.parse(content)
        }
        return LRCLyricParser.parse(content)
    }
}