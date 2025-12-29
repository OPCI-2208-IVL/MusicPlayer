package com.example.myapplication.feature.createsheet

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.CommonRepository
import com.example.myapplication.data.repository.SheetRepository
import com.example.myapplication.exception.localException
import com.example.myapplication.feature.sheetdetail.SHEET_ID
import com.example.myapplication.model.Sheet
import com.example.myapplication.model.sheet_empty
import com.example.myapplication.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateSheetViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val sheetRepository: SheetRepository,
    private val commonRepository: CommonRepository,
) :ViewModel() {
    val finish = mutableStateOf(false)
    val tipError = MutableStateFlow<String?>(null)

    private val sheetId = checkNotNull(savedStateHandle[SHEET_ID])
    val data = MutableStateFlow(sheet_empty())

    fun onSaveClick() {
        val param = data.value
        if (param.title.isBlank()) {
            tipError.value = "歌单标题不能为空"
            return
        }

        viewModelScope.launch {
            if (sheetId == "") {
                sheetRepository.createSheet(param)
            } else {
                sheetRepository.updateSheet(param)
            }
                .asResult()
                .collectLatest {
                    if (it.isSuccess) {
                        finish.value = true
                    } else {
                        tipError.value = it.exceptionOrNull()!!.localException().tipString!!
                    }
                }
        }
    }

    fun onValueChange(sheet: Sheet) {

    }
}