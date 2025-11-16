package com.example.myapplication.feature.login

import com.example.myapplication.exception.CommonException

sealed interface LoginUIState {
    data object Success : LoginUIState
    data class Error(val exception: CommonException) : LoginUIState
    data class ErrorRes(val message: String) : LoginUIState
    data object Loading : LoginUIState
    data object None : LoginUIState
}