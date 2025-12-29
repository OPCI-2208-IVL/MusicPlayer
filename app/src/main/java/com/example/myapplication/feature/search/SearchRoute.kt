package com.example.myapplication.feature.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.R
import com.example.myapplication.database.model.SearchHistoryEntity
import com.example.myapplication.feature.sheet.SheetRoute
import com.example.myapplication.feature.user.UserRoute
import com.example.myapplication.model.SuggestItem
import com.example.myapplication.ui.theme.LocalDividerColor
import com.example.myapplication.ui.theme.SpaceExtraMedium
import com.example.myapplication.ui.theme.SpaceExtraOuter
import com.example.myapplication.ui.theme.SpaceExtraSmall
import com.example.myapplication.ui.theme.SpaceExtraSmall2
import com.example.myapplication.ui.theme.SpaceMedium
import com.example.myapplication.ui.theme.SpaceOuter
import kotlinx.coroutines.launch

@Composable
fun SearchRoute(
    finishPage: () -> Unit,
    toSheetDetail: (String) -> Unit,
    toUserDetail: (String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val query by viewModel.query.collectAsState()
    val placeholder by viewModel.placeholder.collectAsState()
    val focused by viewModel.focused.collectAsState()
    val searchHots by viewModel.searchHots.collectAsState()
//    val searchHistories by viewModel.searchHistories.collectAsState()
    val searchHistoryEditing by viewModel.searchHistoryEditing.collectAsState()
    val selectedIndex by viewModel.selectedIndex.collectAsStateWithLifecycle()

    SearchScreen(
        uiState = uiState,
        finishPage = finishPage,
        toSheetDetail = toSheetDetail,
        query = query,
        placeholder = placeholder,
        onQueryChange = viewModel::onQueryChange,
        onSearchClick = viewModel::onSearchClick,
        focused = focused,
        onFocusedChange = viewModel::onFocusedChange,
        onSuggestClick = viewModel::onSuggestClick,
        searchHots = searchHots,
        onHotClick = viewModel::onHotClick,
//        searchHistories = searchHistories,
//        onSearchHistoryClick = viewModel::onSearchHistoryClick,
        searchHistoryEditing = searchHistoryEditing,
//        toggleSearchHistoryEditing = viewModel::toggleSearchHistoryEditing,
//        onDeleteSearchHistoryClick = viewModel::onDeleteSearchHistoryClick,
        selectedIndexChanged = viewModel::selectedIndexChanged,
        selectedIndex = selectedIndex,
        toUserDetail = toUserDetail,
    )

    BackHandler(enabled = uiState != SearchUiState.Normal) {
        viewModel.finishPage()
    }
}

@Composable
fun SearchScreen(
    uiState: SearchUiState = SearchUiState.Normal,
    finishPage: () -> Unit = {},
    toSheetDetail: (data: String) -> Unit = {},
    toUserDetail: (String) -> Unit = {},
    query: String = "",
    placeholder: String = "",
    onQueryChange: (String) -> Unit = {},
    onSearchClick: () -> Unit = {},
    focused: Boolean = false,
    onFocusedChange: (Boolean) -> Unit = {},
    onSuggestClick: (data: SuggestItem) -> Unit = {},
    searchHots: List<String> = listOf(),
    onHotClick: (data: String) -> Unit = {},
    searchHistories: List<SearchHistoryEntity> = listOf(),
    onSearchHistoryClick: (SearchHistoryEntity) -> Unit = {},
    searchHistoryEditing: Boolean = false,
    toggleSearchHistoryEditing: () -> Unit = {},
    onDeleteSearchHistoryClick: (SearchHistoryEntity) -> Unit = {},
    selectedIndex: Int = 1,
    selectedIndexChanged: (Int) -> Unit = {},
) {
    Scaffold(
        topBar = {
            SearchTopAppBar(
                finishPage = finishPage,
                query = query,
                placeholder = placeholder,
                onQueryChange = onQueryChange,
                onSearchClick = onSearchClick,
                focused = focused,
                onFocusedChange = onFocusedChange,
            )
        },

        modifier = Modifier.fillMaxSize()
    ){paddingValues ->
        when (uiState) {
            is SearchUiState.Normal -> {
                NormalView(
                    searchHots = searchHots,
                    onHotClick = onHotClick,
                    searchHistories = searchHistories,
                    onSearchHistoryClick = onSearchHistoryClick,
                    searchHistoryEditing = searchHistoryEditing,
                    toggleSearchHistoryEditing = toggleSearchHistoryEditing,
                    onDeleteSearchHistoryClick = onDeleteSearchHistoryClick,
                    modifier = Modifier.padding(paddingValues)
                )
            }

            is SearchUiState.Suggest -> {
                SuggestView(
                    uiState.suggests,
                    onSuggestClick = onSuggestClick,
                    modifier = Modifier.padding(paddingValues)
                )
            }

            is SearchUiState.Search -> {
                ContentView(
                    query = query,
                    toSheetDetail = toSheetDetail,
                    toUserDetail = toUserDetail,
                    selectedIndex = selectedIndex,
                    selectedIndexChanged = selectedIndexChanged,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

@Composable
fun ContentView(
    toSheetDetail: (data: String) -> Unit,
    toUserDetail: (String) -> Unit,
    query: String,
    selectedIndex: Int,
    selectedIndexChanged: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState {
        SearchResultTitles.size
    }

    Column(
        modifier = modifier
    ) {
        SearchResultTab(
            selectedIndex = selectedIndex,
            tabChanged = {
                selectedIndexChanged(it)
                scope.launch {
                    pagerState.scrollToPage(it)
                }
            },
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { page ->
            when (page) {
                0 -> SheetRoute(
                    query = query,
                    selected = selectedIndex == 0,
                    toSheetDetail = toSheetDetail,
                )

                1 -> UserRoute(
                    query = query,
                    selected = selectedIndex == 1,
                    toUserDetail = toUserDetail,
                )

                else -> {}
            }
        }
    }

    LaunchedEffect(pagerState) {
        // Collect from the a snapshotFlow reading the currentPage
//        https://developer.android.com/develop/ui/compose/layouts/pager?hl=zh-cn
        snapshotFlow { pagerState.settledPage }.collect { page ->
            selectedIndexChanged(page)
        }
    }
}

@Composable
fun SearchResultTab(
    selectedIndex: Int,
    tabChanged: (Int) -> Unit,
) {
    ScrollableTabRow(
        selectedTabIndex = selectedIndex,
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
                Modifier
                    .tabIndicatorOffset(tabPositions[selectedIndex])
                    .height(2.dp),
            )
        },
        edgePadding = 0.dp, //去除左右边距
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .fillMaxWidth()
    ) {

        SearchResultTitles.forEachIndexed { index, title ->
            Tab(
                text = { Text(title) },
                selected = selectedIndex == index,
                onClick = { tabChanged(index) },
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

private val SearchResultTitles = listOf(
    "歌单",
    "用户",
    "单曲",
    "歌手",
    "歌词",
    "专辑",
    "主题",
)

@Composable
fun SuggestView(
    datum: List<SuggestItem>,
    onSuggestClick: (data: SuggestItem) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        datum.forEach {
            item{ SuggestItemView(it, onSuggestClick = onSuggestClick) }
        }
    }
}


@Composable
fun SuggestItemView(
    data: SuggestItem,
    modifier: Modifier = Modifier,
    onSuggestClick: (data: SuggestItem) -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = data.title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onSuggestClick(data)
                }
                .padding(horizontal = SpaceOuter, vertical = SpaceExtraOuter)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(LocalDividerColor.current)
        )
    }
}

@Composable
fun NormalView(
    searchHots: List<String>,
    onHotClick: (data: String) -> Unit,
    searchHistories: List<SearchHistoryEntity>,
    onSearchHistoryClick: (SearchHistoryEntity) -> Unit,
    searchHistoryEditing: Boolean,
    toggleSearchHistoryEditing: () -> Unit,
    onDeleteSearchHistoryClick: (SearchHistoryEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        if (searchHistories.isNotEmpty()) {
            SearchHistoryList(
                searchHistories = searchHistories,
                onSearchHistoryClick = onSearchHistoryClick,
                searchHistoryEditing = searchHistoryEditing,
                toggleSearchHistoryEditing = toggleSearchHistoryEditing,
                onDeleteSearchHistoryClick = onDeleteSearchHistoryClick,
            )
        }
        SearchHotList(
            datum = searchHots,
            onHotClick = onHotClick,
        )
    }
}

@Composable
fun SearchHistoryList(
    searchHistories: List<SearchHistoryEntity>,
    onSearchHistoryClick: (SearchHistoryEntity) -> Unit,
    searchHistoryEditing: Boolean,
    toggleSearchHistoryEditing: () -> Unit,
    onDeleteSearchHistoryClick: (SearchHistoryEntity) -> Unit,
    modifier: Modifier = Modifier
){
    var showDeleteAllDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        //标题容器
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = SpaceOuter, end = SpaceExtraSmall2),
        ) {
            Text(
                text = "搜索历史",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .weight(1f)
            )

            if (searchHistoryEditing) {
                GreyTextButton(
                    onClick = {
                        showDeleteAllDialog = true
                    },
                    title = "全部删除"
                )

                Spacer(
                    modifier = Modifier
                        .width(SpaceExtraSmall)
                        .height(15.dp)
                        .background(MaterialTheme.colorScheme.outline)
                )

                GreyTextButton(
                    onClick = toggleSearchHistoryEditing,
                    title = "完成"
                )
            } else {
                GreyTextButton(
                    onClick = toggleSearchHistoryEditing,
                    title = "编辑"
                )
            }
        }

        //流式布局
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(SpaceMedium),
            verticalArrangement = Arrangement.spacedBy(SpaceMedium),
            modifier = Modifier.padding(start = SpaceOuter, end = SpaceOuter, bottom = SpaceOuter)
        ) {
            searchHistories.forEach {
                SearchHistoryItem(
                    it.title,
                    editing = searchHistoryEditing,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            if (searchHistoryEditing) {
                                onDeleteSearchHistoryClick(it)
                            } else {
                                onSearchHistoryClick(it)
                            }
                        })
            }
        }
    }

}

@Composable
fun MySearchTopAppBar(
    query: String,
    placeholder: String,
    onQueryChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    focused: Boolean,
    onFocusedChange: (Boolean) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(38.dp)
                .clip(MaterialTheme.shapes.large)
                .background(color = LocalDividerColor.current)
        ) {
            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearchClick()
                    }
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = SpaceOuter)
                    .onFocusChanged {
                        onFocusedChange(it.isFocused)
                    }
                    .focusRequester(focusRequester)
            )

            if (focused && query.isNotEmpty()) {
                IconButton(onClick = {
                    onQueryChange("")
                }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outline,
                        modifier = Modifier
                            .size(24.dp)
                            .padding(SpaceExtraSmall2)
                    )
                }
            }
        }

        if (query.isEmpty()) {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(horizontal = SpaceOuter)
            )
        }
    }

    LaunchedEffect(focused) {
        if (focused) {
            focusRequester.requestFocus()
        } else {
            focusManager.clearFocus()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopAppBar(
    finishPage: () -> Unit,
    query: String,
    placeholder: String,
    onQueryChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    focused: Boolean,
    onFocusedChange: (Boolean) -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            MySearchTopAppBar(
                query,
                placeholder,
                onQueryChange,
                onSearchClick,
                focused,
                onFocusedChange,
            )
        },
        navigationIcon = {
            IconButton(onClick = finishPage) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        },
        actions = {
            TextButton(
                onClick = onSearchClick,
                modifier = Modifier
            ) {
                Text(
                    text = "搜索"
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors().copy(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
    )
}

@Composable
fun GreyTextButton(
    onClick: () -> Unit,
    title: String,
    modifier: Modifier = Modifier
) {
    TextButton(
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.outline
        ),
        modifier = modifier
    ) {
        Text(
            text =  title,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}


@Composable
fun SearchHotList(
    datum: List<String>,
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit = {},
    onHotClick: (data: String) -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = SpaceOuter, end = SpaceExtraSmall2),
        ) {
            Text(
                text =  "热门搜索",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .weight(1f)
            )

            IconButton(onClick = onRefresh) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_refresh),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline,
                )
            }
        }

        //流式布局
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(SpaceMedium),
            verticalArrangement = Arrangement.spacedBy(SpaceMedium),
            modifier = Modifier.padding(start = SpaceOuter, end = SpaceOuter, bottom = SpaceOuter)
        ) {
            datum.forEach {
                SearchHistoryItem(it,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            onHotClick(it)
                        })
            }
        }
    }
}

@Composable
fun SearchHistoryItem(
    title: String,
    modifier: Modifier = Modifier,
    editing: Boolean = false,
) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceVariant),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(horizontal = SpaceOuter, vertical = SpaceExtraMedium)
        )
        if (editing) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = SpaceExtraSmall2)
                    .size(13.dp)
            )
        }
    }
}
