package com.example.myapplication.util

object StringUtil {
    fun isPassword(password: String): Boolean {
        val passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}\$"
        return Regex(passwordPattern).matches(password)
    }
}