package com.example.myapplication.model.lyric

import com.example.myapplication.util.DataUtil.parseLyricTimeToInt
import com.quick.app.util.LyricStringUtil


//example: karaoke.add('00:38.914', '00:42.164', '那通失去希望', '290,373,348,403,689,1147');

object KSCLyricParser {
    fun parse(content: String): Lyric {
        val  lyricLines = parseData(content)
        return Lyric(lyricLines, true)
    }

    private fun parseData(content: String): List<LyricLine> {
        return content.split(";".toRegex())
            .dropLastWhile { it.isEmpty() }
            .mapNotNull { parseLine(it.trim()) }
    }

    private fun parseLine(line: String):LyricLine?{
        return if (line.startsWith("karaoke.add")){
            val params = line.substring(13, line.length - 3).trim().split("', '")
            if (params.size < 3) return null
            val startTime = parseLyricTimeToInt(params[0].trim())
            val endTime = parseLyricTimeToInt(params[1].trim())
            val data = params.subList(2, params.size).joinToString(",").trim()
            val words: List<String>
            val word: String
            if ( data[0] == '[' ){
                words = LyricStringUtil.englishWords(data)
                word = words.joinToString(" ")
            } else {
                words = LyricStringUtil.chineseWords(data)
                word = data
            }
            val wordDurations = mutableListOf<Int>()
            val wordsTimeString = params[3].split(",")
            for (time in wordsTimeString){
                wordDurations.add(time.trim().toInt())
            }
            return LyricLine(data = word, startTime = startTime, endTime = endTime, words = words, wordDurations = wordDurations)
        } else {
            null
        }
    }
}

