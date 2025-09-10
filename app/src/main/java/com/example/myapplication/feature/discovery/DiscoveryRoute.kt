package com.example.myapplication.feature.discovery

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.component.song.ItemSong
import com.example.myapplication.feature.sheet.ItemSheet
import com.example.myapplication.model.ViewData
import com.example.myapplication.ui.theme.Space4XLarge
import com.example.myapplication.ui.theme.SpaceOuter
import com.example.myapplication.ui.theme.SpaceSmall
import com.example.myapplication.ui.theme.SpaceTip


@Composable
fun DiscoveryRoute(
    toSearch: () -> Unit,
    toSheetDetail: (String) -> Unit,
    toggleDrawer: () -> Unit,
    viewModel: DiscoverViewModel = hiltViewModel()
){
    val datum by viewModel.datum.collectAsState()
    DiscoveryScreen(
        toSearch = toSearch,
        toSheetDetail = toSheetDetail,
        toggleDrawer = toggleDrawer,
        topDatum = datum
    )
}


@Composable
fun DiscoveryScreen(
    toSearch: () -> Unit,
    toSheetDetail: (String) -> Unit,
    toggleDrawer: () -> Unit,
    topDatum:List<ViewData>
) {
    Scaffold (
        topBar = {
            DiscoveryTopBar(
                toSearch = toSearch,
                toggleDrawer = toggleDrawer
            )
        },
        contentWindowInsets = ScaffoldDefaults
            .contentWindowInsets
            .exclude(WindowInsets.navigationBars)
    ){paddingValues->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ){
            LazyColumn(
                contentPadding = PaddingValues(horizontal = SpaceOuter),
                verticalArrangement = Arrangement.spacedBy(SpaceSmall),
                modifier = Modifier.fillMaxSize()
            ) {
                topDatum.forEach { data ->
                    if (data.sheets!=null) {
                        items(data.sheets) {
                            ItemSheet(
                                data = it,
                                modifier = Modifier.clickable {
                                    toSheetDetail(it.id)
                                }
                            )
                        }
                    }
                    else if (data.songs!=null) {
                        items(data.songs) {
                            ItemSong(data = it)
                        }
                    }
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DiscoveryTopBar(
    toSearch: () -> Unit,
    toggleDrawer: () -> Unit
){
    CenterAlignedTopAppBar(
        navigationIcon = {
            IconButton(onClick = toggleDrawer) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = null,
                    modifier = Modifier.size(36.dp)
                )
            }
        },
        title = {
            Row (
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Space4XLarge)
                    .clip(RoundedCornerShape(SpaceTip))
                    .background(MaterialTheme.colorScheme.surfaceDim)
                    .clickable {
                        toSearch()
                    }
            ){
                Text("搜索")
            }
        }
    )
}