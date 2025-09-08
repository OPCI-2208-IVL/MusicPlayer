package com.example.myapplication.extension

import androidx.compose.runtime.Composable
import java.util.Locale
import kotlin.time.Duration.Companion.milliseconds

@Composable
internal fun Long.asFormatTimeString() = milliseconds.toComponents { minutes, seconds, _ ->
    "${String.format(locale = Locale.US, format = "%02d", minutes)}:${String.format(locale = Locale.US, format = "%02d", seconds)}"
}