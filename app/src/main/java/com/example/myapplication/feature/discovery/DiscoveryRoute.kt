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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.component.ItemSong
import com.example.myapplication.model.Song


@Composable
fun DiscoveryRoute(
    toSearch: () -> Unit,
    viewModel: DiscoverViewModel = viewModel()
){
    val datum by viewModel.datum.collectAsState()
    DiscoveryScreen(
        toSearch,
        datum
    )
}


@Composable
fun DiscoveryScreen(
    toSearch: () -> Unit,
    songs:List<Song>
) {
    Scaffold (
        topBar = {
            DiscoveryTopBar(toSearch)
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
                contentPadding = PaddingValues(horizontal = 18.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(songs){
                    ItemSong(data = it)
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DiscoveryTopBar(
    toSearch: () -> Unit
){
    CenterAlignedTopAppBar(
        title = {
            Row (
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(38.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(MaterialTheme.colorScheme.onBackground)
                    .clickable {
                        toSearch()
                    }
            ){
                Text("搜索")
            }
        }
    )
}