package com.example.myapplication.feature.discovery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.myapplication.model.DiscoverPreviewSongData
import com.example.myapplication.model.Song
import com.example.myapplication.model.network.datasource.ClientRetrofitDatasource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DiscoverViewModel:ViewModel() {
    private val _data = MutableStateFlow<List<Song>>(listOf())
    val datum: StateFlow<List<Song>> = _data

    init {
        loadData()
    }

    private fun loadData() {
        _data.value = DiscoverPreviewSongData.songs

        viewModelScope.launch {
            val songs = ClientRetrofitDatasource.songs()
            _data.value = songs.data?.list?: emptyList()
        }
    }

}