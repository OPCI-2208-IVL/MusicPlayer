package com.example.myapplication.util

import java.util.Calendar

object DataUtil {
    fun currentYear(): Int {
        return Calendar.getInstance().get(Calendar.YEAR)
    }

    fun currentDayOfMonth(): Int {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    }

    fun parseLyricTimeToInt(data: String): Long {

        val time = data.replace(":", ".")

        //使用.拆分
        val strings = time.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()

        //分别取出分秒毫秒
        val m = strings[0].toInt()
        val s = strings[1].toInt()
        val ms = strings[2].toInt()

        //转为毫秒
        return ((m * 60 + s) * 1000 + ms).toLong()
    }
}