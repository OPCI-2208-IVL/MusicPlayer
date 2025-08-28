package com.example.myapplication.feature.sheetdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.sheet_empty
import com.example.myapplication.model.network.datasource.ClientRetrofitDatasource.sheetDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SheetDetailViewModel(
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _data = MutableStateFlow<SheetDetailUiState>(SheetDetailUiState.Loading)
    val data: StateFlow<SheetDetailUiState> = _data
    private val sheetID: String = checkNotNull(savedStateHandle[SHEET_ID])

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                val r = sheetDetail(sheetID)
                if (r.status == 0) {
                    _data.value = SheetDetailUiState.Success(r.data?: sheet_empty())
                }else{
                    _data.value = SheetDetailUiState.Error(r.message ?: "error")
                }
            } catch (e: Exception) {
                _data.value = SheetDetailUiState.Error(e.localizedMessage?: "error")
            }
        }
    }

    fun onRetryClick() {
        loadData()
    }
}