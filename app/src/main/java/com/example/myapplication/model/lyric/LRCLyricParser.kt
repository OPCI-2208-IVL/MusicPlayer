package com.example.myapplication.model.lyric

import com.example.myapplication.util.DataUtil.parseLyricTimeToInt

object LRCLyricParser {
    fun parse(content: String): Lyric {
        val  lyricLines = parseData(content)
        for(i in 0 until lyricLines.size -1){
            lyricLines[i].endTime = lyricLines[i+1].startTime
        }
        lyricLines[lyricLines.size -1].endTime = Long.MAX_VALUE
        return Lyric(lyricLines, false)
    }

    private fun parseData(content: String): List<LyricLine> {
        return content.split("\n".toRegex())
            .dropLastWhile { it.isEmpty() }
            .mapNotNull { parseLine(it) }
    }

    private fun parseLine(line: String):LyricLine?{
        val parts = line.split("]")
        if (parts.size < 2) return null
        val timePart = parts[0].removePrefix("[")
        if (!typeMatch(timePart)) return null
        val timeInMillis =  parseLyricTimeToInt(timePart)
        val text = parts.subList(1, parts.size).joinToString("]").trim()
        return LyricLine(data = text, startTime = timeInMillis)
    }

    private fun typeMatch(input: String): Boolean {
        return input.all { char ->
            char.isDigit() || char == ':' || char == '.'
        }
    }

}
