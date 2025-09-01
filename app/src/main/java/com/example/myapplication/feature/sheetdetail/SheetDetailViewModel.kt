package com.example.myapplication.feature.sheetdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.SheetRepository
import com.example.myapplication.exception.localException
import com.example.myapplication.result.asResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SheetDetailViewModel(
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _data = MutableStateFlow<SheetDetailUiState>(SheetDetailUiState.Loading)
    val data: StateFlow<SheetDetailUiState> = _data
    private val sheetID: String = checkNotNull(savedStateHandle[SHEET_ID])

    private val sheetRepository = SheetRepository()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                sheetRepository.sheetDetail(sheetID)
                    .asResult()
                    .collectLatest { r ->
                        if (r.isSuccess) {
                            _data.value = SheetDetailUiState.Success(r.getOrThrow().data !!)
                        }else{
                            _data.value = SheetDetailUiState.Error( r.exceptionOrNull()!!.localException())
                        }
                    }
            } catch (e: Exception) {
                _data.value = SheetDetailUiState.Error( e.localException() )
            }
        }
    }

    fun onRetryClick() {
        loadData()
    }
}