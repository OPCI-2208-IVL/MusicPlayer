package com.example.myapplication.feature.discovery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.CommonRepository
import com.example.myapplication.model.ViewData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val commonRepository: CommonRepository
):ViewModel() {
    private val _data = MutableStateFlow<List<ViewData>>(listOf())
    val datum: StateFlow<List<ViewData>> = _data

    init {
        loadData()
    }

    private fun loadData() {

        viewModelScope.launch {
//            val songs = ClientRetrofitDatasource.songs()
//            _data.value = songs.data?.list?: emptyList()

            val indexes = commonRepository.indexes(30)
            _data.value = indexes.data?.list ?: emptyList()
        }
    }

}