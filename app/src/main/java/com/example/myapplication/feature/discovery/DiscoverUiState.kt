package com.example.myapplication.feature.discovery

import com.example.myapplication.exception.CommonException

import com.example.myapplication.model.ViewData

sealed interface DiscoverUiState {
    data class Success(val data: List<ViewData>) : DiscoverUiState
    data object Loading : DiscoverUiState
    data class Error(val exception: CommonException) : DiscoverUiState
}