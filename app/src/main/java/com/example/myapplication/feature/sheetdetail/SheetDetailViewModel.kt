package com.example.myapplication.feature.sheetdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.SheetRepository
import com.example.myapplication.data.repository.SongRepository
import com.example.myapplication.data.repository.UserDataRepository
import com.example.myapplication.exception.localException
import com.example.myapplication.feature.mediaplayer.BaseMediaPlayerViewModel
import com.example.myapplication.media.MediaServiceConnection
import com.example.myapplication.model.Sheet
import com.example.myapplication.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SheetDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private var sheetRepository: SheetRepository,
    mediaServiceConnection: MediaServiceConnection,
    songRepository: SongRepository,
    userDataRepository: UserDataRepository
): BaseMediaPlayerViewModel(
    mediaServiceConnection,
    songRepository,
    userDataRepository
) {
    private val _data = MutableStateFlow<SheetDetailUiState>(SheetDetailUiState.Loading)
    val data: StateFlow<SheetDetailUiState> = _data
    private val sheetID: String = checkNotNull(savedStateHandle[SHEET_ID])

    private lateinit var sheet: Sheet

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
                             sheet = r.getOrThrow().data !!
                            _data.value = SheetDetailUiState.Success( sheet )
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

    fun onSongClick(index: Int) {
        setMediaAndPlay(
            datum =  sheet.songs!!,
            index =  index,
            navigateToMusicPlayer = true
        )
    }
}