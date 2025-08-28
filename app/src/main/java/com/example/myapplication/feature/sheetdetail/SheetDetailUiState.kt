package com.example.myapplication.feature.sheetdetail

import com.example.myapplication.model.Sheet

sealed interface SheetDetailUiState{
    data class Success(val sheet: Sheet): SheetDetailUiState

    data object Loading: SheetDetailUiState

    data class Error(val message: String): SheetDetailUiState
}