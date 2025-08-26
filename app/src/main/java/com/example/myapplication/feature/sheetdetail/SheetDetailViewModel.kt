package com.example.myapplication.feature.sheetdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.sheet_empty
import com.example.myapplication.model.Sheet
import com.example.myapplication.model.network.datasource.ClientRetrofitDatasource.sheetDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SheetDetailViewModel(
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _data = MutableStateFlow<Sheet>(sheet_empty())
    val data: StateFlow<Sheet> = _data
    private val sheetID: String = checkNotNull(savedStateHandle[SHEET_ID])

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val r = sheetDetail(sheetID)
            _data.value = r.data ?: sheet_empty()
        }
    }
}