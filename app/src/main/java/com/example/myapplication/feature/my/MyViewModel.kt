package com.example.myapplication.feature.my

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.SheetRepository
import com.example.myapplication.exception.localException
import com.example.myapplication.model.Sheet
import com.example.myapplication.ui.myapp.MyAppState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val sheetRepository: SheetRepository
):ViewModel() {

    val tipError = MutableStateFlow<String?>(null)

    val createDatum = MutableStateFlow<List<Sheet>>(emptyList())
    val collectDatum = MutableStateFlow<List<Sheet>>(emptyList())

    fun loadData(){
        if(MyAppState.session.isNotBlank()){
            viewModelScope.launch {
                val createSheetsJob = async {
                    runCatching {
                        sheetRepository.createSheets(MyAppState.userId)
                    }
                }
                val collectSheetsJob = async {
                    runCatching {
                        sheetRepository.collectSheets(MyAppState.userId)
                    }
                }

                val indexResult = createSheetsJob.await()
                val productsResult = collectSheetsJob.await()

                if(indexResult.isFailure || productsResult.isFailure){
                    val throws = indexResult.exceptionOrNull()?:productsResult.exceptionOrNull()
                    tipError.value = throws!!.localException().tipString
                    return@launch
                }

                val indexResultData = indexResult.getOrNull()!!
                val productsResultData = productsResult.getOrNull()!!

                if(!indexResultData.isSucceeded){
                    tipError.value = indexResultData.message
                    return@launch
                }

                if(!productsResultData.isSucceeded){
                    tipError.value = productsResultData.message
                    return@launch
                }

                createDatum.value = indexResultData.data?.list?: emptyList()
                collectDatum.value = productsResultData.data?.list?: emptyList()
            }
        }
    }
}