package com.example.myapplication.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.CommonRepository
import com.example.myapplication.model.SuggestItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val commonRepository: CommonRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(
        SearchUiState.Normal
    )
    val uiState: StateFlow<SearchUiState> = _uiState

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _placeholder = MutableStateFlow("手机")
    val placeholder: StateFlow<String> = _placeholder

    private val _focused = MutableStateFlow(true)
    val focused: StateFlow<Boolean> = _focused


    val searchHots = flow{
        emit(HOTS)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList(),
    )

    private val _searchHistoryEditing = MutableStateFlow<Boolean>(false)
    val searchHistoryEditing: StateFlow<Boolean> = _searchHistoryEditing

    val selectedIndex = MutableStateFlow(0)

    fun onQueryChange(data: String) {
        _query.value = data
    }

    fun onSearchClick() {
        if (query.value.isBlank()) {
            _query.value = placeholder.value
        }
        doSearch()
    }

    private fun doSearch() {
        _focused.value = false
        _uiState.value = SearchUiState.Search

    }

    fun onHotClick(data: String) {
        _query.value = data
        doSearch()
    }

    fun selectedIndexChanged(data: Int): Unit {
        selectedIndex.value = data
    }

    fun onSuggestClick(data: SuggestItem) {
        _query.value = data.title
        doSearch()
    }

    fun onFocusedChange(data: Boolean) {
        _focused.value = data
        checkLoadSuggestIfNeed()
    }

    fun finishPage() {
        _uiState.value = SearchUiState.Normal
    }

    private fun checkLoadSuggestIfNeed() {
        if (focused.value) {
            if (query.value.isNotBlank()) {

                //获取搜索建议
                //loadSuggest()
            } else {
                _uiState.value = SearchUiState.Normal
            }
        }
    }


    companion object {
        val HOTS = listOf<String>(
            "华晨宇",
            "最伟大的作品",
            "future",
            "李荣浩",
            "小米",
            "雨天",
            "Justin Bieber",
            "助眠音乐",
            "华为",
        )
    }

}

