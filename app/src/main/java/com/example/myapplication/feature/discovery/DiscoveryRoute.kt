package com.example.myapplication.feature.discovery

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil.compose.AsyncImage
import com.example.myapplication.component.HorizontalPagerIndicator
import com.example.myapplication.component.MyErrorView
import com.example.myapplication.component.MyLoading
import com.example.myapplication.component.song.ItemSong
import com.example.myapplication.feature.sheet.ItemSheetGrid
import com.example.myapplication.model.Ad
import com.example.myapplication.model.ButtonViewData
import com.example.myapplication.model.ViewData
import com.example.myapplication.ui.theme.Space4XLarge
import com.example.myapplication.ui.theme.SpaceExtraMedium
import com.example.myapplication.ui.theme.SpaceLarge
import com.example.myapplication.ui.theme.SpaceMedium
import com.example.myapplication.ui.theme.SpaceOuter
import com.example.myapplication.ui.theme.SpaceSmall
import com.example.myapplication.ui.theme.SpaceTip
import com.example.myapplication.util.DataUtil
import com.example.myapplication.util.ResourceUtil
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun DiscoveryRoute(
    toSearch: () -> Unit,
    toSheetDetail: (String) -> Unit,
    toggleDrawer: () -> Unit,
    toUrl: (String) -> Unit,
    viewModel: DiscoverViewModel = hiltViewModel()
){
    val datum by viewModel.datum.collectAsState()
    DiscoveryScreen(
        toSearch = toSearch,
        toSheetDetail = toSheetDetail,
        toggleDrawer = toggleDrawer,
        toUrl = toUrl,
        onRefresh = viewModel::onRefresh,
        onRetry = viewModel::onRetry,
        datum = datum
    )
}

@Composable
fun DiscoveryScreen(
    toSearch: () -> Unit,
    toSheetDetail: (String) -> Unit,
    toggleDrawer: () -> Unit,
    toUrl: (String) -> Unit,
    onRefresh: () -> Unit,
    onRetry: () -> Unit,
    datum: DiscoverUiState,
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
        when (datum) {
            is DiscoverUiState.Loading -> {
                MyLoading()
            }

            is DiscoverUiState.Success -> {
                DiscoveryList(
                    toSheetDetail = toSheetDetail,
                    toUrl = toUrl,
                    topDatum = datum.data,
                    onRefresh = onRefresh,
                    modifier = Modifier.padding(paddingValues)
                )
            }

            is DiscoverUiState.Error -> {
                MyErrorView(
                    exception = datum.exception,
                    onRetryClick = onRetry
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoveryList(
    toSheetDetail: (String) -> Unit,
    toUrl: (String) -> Unit ,
    topDatum:List<ViewData>,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val pullToRefreshState = rememberPullToRefreshState()
    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(pullToRefreshState.isRefreshing) {
            onRefresh()
        }
    }
    if (topDatum.isNotEmpty()) {
        LaunchedEffect(Unit) {
            pullToRefreshState.endRefresh()
        }
    }

   Box (
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(pullToRefreshState.nestedScrollConnection)

   ){
        val gridState = rememberLazyGridState()
        LazyVerticalGrid(
            columns = androidx.compose.foundation.lazy.grid.GridCells.Fixed(3),
            state = gridState,
            contentPadding = PaddingValues(horizontal = SpaceOuter, vertical = SpaceSmall),
            verticalArrangement = Arrangement.spacedBy(SpaceExtraMedium),
            horizontalArrangement = Arrangement.spacedBy(SpaceExtraMedium),
            modifier = Modifier.fillMaxSize()
        ) {
            topDatum.forEach { data ->
                if (data.ads!=null) {
                   item(
                       span = {GridItemSpan(maxLineSpan)}
                   ) {
                       DiscoverBanner(
                           data = data.ads,
                           onAdClick = { ad ->
                               ad.uri?.let {
                                   toUrl(it)
                               }
                           }
                       )
                   }
                }
                else if (data.buttons!=null) {
                    item (span = { GridItemSpan(maxLineSpan)}){
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = SpaceOuter)
                        ) {
                            itemsIndexed(data.buttons) { index, item ->
                                ItemDiscoveryButton(
                                    data = item,
                                    modifier = Modifier
                                )
                                if(index != data.buttons.lastIndex){
                                    Spacer(modifier = Modifier.size(SpaceLarge))
                                }
                            }
                        }
                    }
                }
                else if (data.sheets!=null) {
                    item (span = { GridItemSpan(maxLineSpan)}){
                        ItemDiscoveryHeader(
                            title = "推荐歌单",
                            toDetail = {}
                        )
                    }

                    items(data.sheets) {
                        ItemSheetGrid(
                            data = it,
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.medium)
                                .clickable {
                                    toSheetDetail(it.id)
                                }
                        )
                    }
                }
                else if (data.songs!=null) {
                    item (span = { GridItemSpan(maxLineSpan)}){
                        ItemDiscoveryHeader(
                            title = "推荐单曲",
                            toDetail = {}
                        )
                    }

                    items(data.songs, span = { GridItemSpan(maxLineSpan) }){
                        ItemSong(data = it)
                    }
                }
            }
        }

        PullToRefreshContainer(
            modifier = Modifier.align(Alignment.TopCenter),
            state = pullToRefreshState,
        )
   }
}

@Composable
fun ItemDiscoveryButton(
    data: ButtonViewData,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Box{
            AsyncImage(
                model = ResourceUtil.rel2abs(data.icon),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
            )
            if(data.icon == "music/music_recommend_button.png"){
                Text(
                    text = DataUtil.currentDayOfMonth().toString(),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 7.dp)
                )
            }
        }
        Spacer(modifier = Modifier.size(SpaceSmall))
        Text(
            text = data.title,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.outline
        )
    }
}

@Composable
fun DiscoverBanner(
    onAdClick: (Ad) -> Unit,
    data: List<Ad>
) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2.571f)//图片的宽高比 750:292=2.571
            .clip(MaterialTheme.shapes.extraSmall)
    ){
        val pagerState = rememberPagerState(pageCount = { data.size })
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
        ) { page ->
            val item = data[page]
            AsyncImage(
                model = ResourceUtil.rel2abs(item.icon),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        onAdClick(item)
                    }
            )
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            pageCount = data.size,
            modifier = Modifier
                .padding(bottom = SpaceMedium)
        )

        val scope = rememberCoroutineScope()
        val lifecycle = LocalLifecycleOwner.current.lifecycle
        LaunchedEffect(pagerState) { 
            var job: Job? = null
            val observe = object : DefaultLifecycleObserver {
                override fun onResume(owner: LifecycleOwner) {
                    super.onResume(owner)
                    job?.cancel()
                    job = scope.launch {
                        while (true) {
                            kotlinx.coroutines.delay(3000)
                            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
                            pagerState.animateScrollToPage(nextPage)
                        }
                    }
                }

                override fun onPause(owner: LifecycleOwner) {
                    super.onPause(owner)
                    job?.cancel()
                }
            }
            lifecycle.addObserver(observe)
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

@Composable
fun ItemDiscoveryHeader(
    title: String,
    modifier: Modifier = Modifier,
    toDetail: () -> Unit = {},
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(vertical = SpaceOuter)
                .weight(1f)
        )
        Text(
            text = "更多>",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier
                .padding(vertical = SpaceOuter)
                .clickable {
                    toDetail()
                }
        )
    }
}