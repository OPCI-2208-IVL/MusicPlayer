package com.example.myapplication.util

import android.annotation.SuppressLint

object StringUtil {
    fun isPassword(password: String): Boolean {
        val passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}\$"
        return Regex(passwordPattern).matches(password)
    }

    @SuppressLint("DefaultLocale")
    fun formatCount(data: Long): String {
        if (data >= 9999) {
            //保留1位小数
            return String.format("%.1f万", data * 1.0 / 10000)
        }
        return data.toString()
    }
}