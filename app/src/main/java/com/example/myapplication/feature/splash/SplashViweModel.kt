package com.example.myapplication.feature.splash

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SplashViewModel(
    timeDelay: Long = 3000
) : ViewModel() {

    private var timer: CountDownTimer? = null
    private val _timeLeft = MutableStateFlow(0L)
    val timeLeft: StateFlow<Long> = _timeLeft
    var navigateToNext = MutableStateFlow(false)

    init {
        timeDelay(timeDelay)
    }

    private fun timeDelay(time: Long) {
        timer = object : CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _timeLeft.value = millisUntilFinished / 1000 + 1
            }

            override fun onFinish() {
                navigateToNext.value = true
            }
        }.start()
    }

    fun onSkip() {
        timer?.cancel()
        navigateToNext.value = true
    }
}