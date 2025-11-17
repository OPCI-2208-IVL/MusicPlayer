package com.example.myapplication.feature.discovery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.CommonRepository
import com.example.myapplication.exception.localException

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val commonRepository: CommonRepository
):ViewModel() {
    private val _data = MutableStateFlow<DiscoverUiState>(DiscoverUiState.Loading)
    val datum: StateFlow<DiscoverUiState> = _data

    init {
        loadData()
    }

    private fun loadData() {

        viewModelScope.launch {
//            val songs = ClientRetrofitDatasource.songs()
//            _data.value = songs.data?.list?: emptyList()

            try{
                val indexes = commonRepository.indexes(30)
                _data.value = DiscoverUiState.Success(indexes.data?.list ?: emptyList())
            } catch (e: Exception){
                _data.value = DiscoverUiState.Error( e.localException())
            }
        }
    }

    fun onRetryClick() {
        loadData()
    }

}