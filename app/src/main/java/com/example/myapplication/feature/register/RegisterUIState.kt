package com.example.myapplication.feature.register

import com.example.myapplication.exception.CommonException

sealed interface RegisterUIState {
    data object Success : RegisterUIState
    data class Error(val exception: CommonException) : RegisterUIState
    data class ErrorRes(val message: String) : RegisterUIState
    data object Loading : RegisterUIState
    data object None : RegisterUIState
}