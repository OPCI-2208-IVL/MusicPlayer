package com.example.myapplication.feature.sheetdetail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.component.MyCenterTopAppBar
import com.example.myapplication.component.song.ItemSongSheet
import com.example.myapplication.model.Sheet

@Composable
fun SheetDetailRoute(
    finishPage: () -> Unit,
    viewModel: SheetDetailViewModel = viewModel()
) {
    val data by viewModel.data.collectAsState()
    SheetDetailScreen(
        finishPage = finishPage,
        data = data,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SheetDetailScreen(
    data: Sheet,
    finishPage: () -> Unit
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
        ){
            data.songs?.let {
                itemsIndexed(it) {index,data ->
                    ItemSongSheet(
                        data = data,
                        index = index
                    )
                }
            }
        }
    }
}
