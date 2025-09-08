package com.example.myapplication.feature.sheetdetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.component.MyCenterTopAppBar
import com.example.myapplication.component.MyErrorView
import com.example.myapplication.component.MyLoading
import com.example.myapplication.component.song.ItemSongSheet
import com.example.myapplication.model.Sheet

@Composable
fun SheetDetailRoute(
    finishPage: () -> Unit,
    toMusicPlayer: () -> Unit,
    viewModel: SheetDetailViewModel = hiltViewModel()
) {
    val data by viewModel.data.collectAsState()
    SheetDetailScreen(
        finishPage = finishPage,
        data = data,
        onRetry = viewModel::onRetryClick,
        onSongClick = viewModel::onSongClick
    )

    LaunchedEffect(viewModel.toMusicPlayer.value) {
        if (viewModel.toMusicPlayer.value){
            toMusicPlayer()
            viewModel.clearMusicPlayer()
        }
    }

}


@Composable
fun SheetDetailScreen(
    data: SheetDetailUiState = SheetDetailUiState.Loading,
    finishPage: () -> Unit,
    onRetry: () -> Unit,
    onSongClick: (Int) -> Unit
) {
    when (data) {
        is SheetDetailUiState.Loading -> {
            MyLoading()
        }

        is SheetDetailUiState.Success -> {
            ContentView(
                finishPage = finishPage,
                data = data.sheet,
                onSongClick = onSongClick
            )
        }

        is SheetDetailUiState.Error -> {
            MyErrorView(
                exception = data.exception,
                onRetryClick = onRetry
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentView(
    finishPage: () -> Unit,
    data: Sheet,
    onSongClick: (Int) -> Unit,
) {
    Scaffold(
        topBar = {
            MyCenterTopAppBar(
                finishPage = finishPage,
                titleText = data.title
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValue ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
        ) {
            data.songs?.let {
                itemsIndexed(it) { index, data ->
                    ItemSongSheet(
                        data = data,
                        index = index,
                        modifier = Modifier.clickable { onSongClick(index) }
                    )
                }
            }
        }
    }
}
