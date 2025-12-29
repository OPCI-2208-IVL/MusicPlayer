package com.example.myapplication.feature.search

import com.example.myapplication.model.SuggestItem

sealed interface SearchUiState {
    data object Normal : SearchUiState
    data class Suggest(
        val suggests: List<SuggestItem>,
    ) : SearchUiState
    data object Search : SearchUiState
}