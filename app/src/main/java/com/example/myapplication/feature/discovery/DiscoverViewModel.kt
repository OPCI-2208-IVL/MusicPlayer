package com.example.myapplication.feature.discovery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.ViewData
import com.example.myapplication.model.network.datasource.ClientRetrofitDatasource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DiscoverViewModel:ViewModel() {
    private val _data = MutableStateFlow<List<ViewData>>(listOf())
    val datum: StateFlow<List<ViewData>> = _data

    init {
        loadData()
    }

    private fun loadData() {

        viewModelScope.launch {
//            val songs = ClientRetrofitDatasource.songs()
//            _data.value = songs.data?.list?: emptyList()

            val indexes = ClientRetrofitDatasource.index(30)
            _data.value = indexes.data?.list ?: emptyList()
        }
    }

}