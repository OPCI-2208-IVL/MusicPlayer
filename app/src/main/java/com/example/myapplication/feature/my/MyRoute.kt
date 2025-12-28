package com.example.myapplication.feature.my

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.myapplication.component.MyCenterTopAppBar
import com.example.myapplication.extension.clickableNoRipple
import com.example.myapplication.feature.main.MySettingItem
import com.example.myapplication.feature.sheet.ItemSheet
import com.example.myapplication.model.Sheet
import com.example.myapplication.ui.myapp.MyAppUiState
import com.example.myapplication.ui.theme.LocalDividerColor
import com.example.myapplication.ui.theme.SpaceExtraSmall
import com.example.myapplication.ui.theme.SpaceOuter
import com.example.myapplication.ui.theme.SpaceSmall

@Composable
fun MyRoute(
    appUiState: MyAppUiState,
    toLogin: () -> Unit,
    toSheetDetail: (String) -> Unit,
    toLocalMusic: () -> Unit,
    toScanLocalMusic: () -> Unit,
    toEditSheet: () -> Unit,
    viewModel: MyViewModel = hiltViewModel()
) {
    val isLogin by appUiState.isLogin.collectAsState()
    val createDatum by viewModel.createDatum.collectAsState()
    val collectDatum by viewModel.collectDatum.collectAsState()

    MyScreen(
        toLocalMusic = toLocalMusic,
        toScanLocalMusic = toScanLocalMusic,
        localMusicCount = 0,
        toEditSheet = toEditSheet,
        toSheetDetail = toSheetDetail,
        createDatum = createDatum,
        collectDatum = collectDatum
    )

    LaunchedEffect(Unit) {
        viewModel.loadData()
        
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScreen(
    toLocalMusic: () -> Unit,
    toScanLocalMusic: () -> Unit,
    toEditSheet: () -> Unit,
    toSheetDetail: (String) -> Unit,
    createDatum: List<Sheet>,
    collectDatum: List<Sheet>,
    localMusicCount: Int = 0
) {
    Scaffold(
        topBar = {
            MyCenterTopAppBar(
                titleText = "我的"
            )
        },
        contentWindowInsets = ScaffoldDefaults
            .contentWindowInsets
            .exclude(WindowInsets.navigationBars)
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            item {
                Spacer(modifier = Modifier.padding(SpaceExtraSmall))
                MySettingItem(
                    title = "本地音乐",
                    onClick = {
                        if (localMusicCount == 0 ) {
                            toScanLocalMusic()
                        } else {
                            toLocalMusic()
                        }
                    }
                )
                Spacer(modifier = Modifier.padding(SpaceExtraSmall))
            }

            item {
                MySettingItem(
                    title = "下载管理"
                )
            }

            item {
                MySettingItem(
                    title = "最近播放"
                )
            }

            item {
                MySettingItem(
                    title = "我的收藏"
                )
            }

            item {
                MySettingTitleSmall(
                    title = "创建的歌单",
                    onClick = {
                        toEditSheet()
                    }
                )
            }

            if(createDatum.isNotEmpty()) {
                items(createDatum) {
                    ItemSheet(
                        data = it,
                        modifier = Modifier.clickable {
                            toSheetDetail(it.id)
                        }
                    )
                    if (it != createDatum.last()) {
                        Spacer(modifier = Modifier.size(SpaceExtraSmall))
                    }
                }
            }

            item {
                MySettingTitleSmall(
                    title = "收藏的歌单",
                )
            }

            if(collectDatum.isNotEmpty()) {
                items(collectDatum) {
                    ItemSheet(
                        data = it,
                        modifier = Modifier.clickable {
                            toSheetDetail(it.id)
                        }
                    )
                    if (it != collectDatum.last()) {
                        Spacer(modifier = Modifier.size(SpaceExtraSmall))
                    }
                }
            }
        }
    }
}

@Composable
fun MySettingTitleSmall(
    title: String,
    onClick: (() -> Unit)? = null,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(LocalDividerColor.current)
            .padding(horizontal = SpaceOuter, vertical = SpaceSmall),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )

        onClick?.let {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline,
                modifier = Modifier.clickableNoRipple {
                    it()
                }
            )
        }
    }
}