package com.example.myapplication.feature.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.MediaItem
import com.example.myapplication.R
import com.example.myapplication.component.NavigationBar
import com.example.myapplication.database.model.SongEntity
import com.example.myapplication.extension.clickableNoRipple
import com.example.myapplication.feature.discovery.DiscoveryRoute
import com.example.myapplication.feature.mediaplayer.component.MusicListDialog
import com.example.myapplication.feature.mediaplayer.component.MusicPlayerBottomBar
import com.example.myapplication.feature.my.MyRoute
import com.example.myapplication.feature.settings.SettingsRoute
import com.example.myapplication.media.PlaybackState
import com.example.myapplication.model.UserData
import com.example.myapplication.ui.myapp.MyAppUiState
import com.example.myapplication.ui.theme.SpaceExtraOuter
import com.example.myapplication.ui.theme.SpaceExtraSmall
import com.example.myapplication.ui.theme.SpaceMedium
import com.example.myapplication.ui.theme.SpaceOuter
import kotlinx.coroutines.launch


@Composable
fun MainRoute(
    toSheetDetail: (String) -> Unit,
    appUiState: MyAppUiState,
    toMusicPlayer: () -> Unit,
    viewModel: MainViewModel = hiltViewModel()
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val musicDatum by viewModel.playListDatum.collectAsStateWithLifecycle()
    val nowPlaying by viewModel.nowPlaying.collectAsStateWithLifecycle()
    val playbackState by viewModel.playbackState.collectAsStateWithLifecycle()
    val currentPosition by viewModel.currentPosition.collectAsStateWithLifecycle()
    val recordRotation by viewModel.recordRotation.collectAsStateWithLifecycle()
    val showMusicListDialog by viewModel.showMusicListDialog.collectAsStateWithLifecycle()

    val isLogin by appUiState.isLogin.collectAsState()
    val userData by appUiState.userData.collectAsState()

    val toggleDrawer: () -> Unit = {
        scope.launch {
            drawerState.apply { 
                 if (isClosed) open() else close()
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.background,
                modifier = Modifier.fillMaxHeight()
            ) {
                MainDrawerView(
                    userData = userData,
                    isLogin= isLogin,
                    toProfile = {},
                    toScan = {},
                    toLogin = {},
                    onLogoutClick = {}
                )
            }
        }
    ) {
        MainScreen(
            toSheetDetail = toSheetDetail,
            toggleDrawer = toggleDrawer,
            datum = musicDatum,
            toMusicPlayer = toMusicPlayer,
            nowPlaying = nowPlaying,
            playbackState = playbackState,
            currentPosition = currentPosition.toFloat(),
            recordRotation = recordRotation,
            onPlayOrPauseClick = viewModel::onPlayOrPauseClick,
            toggleShowMusicListDialog = viewModel::toggleShowMusicListDialog ,
        )
    }

    if(showMusicListDialog){
        MusicListDialog(
            datum = musicDatum,
            nowPlaying = nowPlaying,
            onClearPlayListClick = viewModel::onClearPlayListClick,
            onItemPlayListClick = viewModel::onItemPlayListClick,
            onItemMusicDeleteClick = viewModel::onItemMusicDeleteClick,
            onDismissRequest = {
                viewModel.toggleShowMusicListDialog()
            }
        )
    }
}

@Composable
fun MainScreen(
    toSheetDetail: (String) -> Unit,
    toggleDrawer: () -> Unit,
    datum: List<SongEntity> = listOf(),
    toMusicPlayer: () -> Unit,
    nowPlaying: MediaItem = MediaItem.EMPTY,
    playbackState: PlaybackState,
    currentPosition: Float = 0F,
    recordRotation: Float = 0F,
    onPlayOrPauseClick: () -> Unit,
    toggleShowMusicListDialog: () -> Unit,
) {
    val pageState = rememberPagerState {
        3
    }

    var currentDestination by rememberSaveable {
        mutableStateOf(TopLeveDestination.DISCOVER.route)
    }

    val scope = rememberCoroutineScope ()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalPager(
            state = pageState,
            userScrollEnabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { page ->
            when (page) {
                0 -> DiscoveryRoute(
                    toSearch = {},
                    toSheetDetail = toSheetDetail,
                    toggleDrawer = toggleDrawer
                )
                1 -> MyRoute()
                2 -> SettingsRoute()
            }
        }

        if (nowPlaying.mediaId.isNotBlank()){
            MusicPlayerBottomBar(
                modifier = Modifier.clickable { toMusicPlayer() },
                title = nowPlaying.mediaMetadata.title.toString(),
                artists = nowPlaying.mediaMetadata.artist.toString(),
                icon = nowPlaying.mediaMetadata.artworkUri.toString(),
                isPlaying = playbackState.isPlaying,
                currentPosition = currentPosition,
                duration = playbackState.duration.toFloat(),
                recordRotation = recordRotation,
                onPlayOrPauseClick = onPlayOrPauseClick,
                toggleShowMusicListDialog = toggleShowMusicListDialog
            )
        }

        NavigationBar(
            destinations = TopLeveDestination.entries,
            currentDestination = currentDestination,
            onNavigateToDestination = {index ->
                currentDestination = TopLeveDestination.entries[index].route
                scope.launch {
                    pageState.scrollToPage(index)
                }.start()
            },
            modifier = Modifier
        )
    }
}

@Composable
fun MainDrawerView(
    userData: UserData,
    isLogin: Boolean,
    toProfile: () -> Unit,
    toScan: () -> Unit,
    toLogin: () -> Unit,
    onLogoutClick: () -> Unit
){
    val scrollState = rememberScrollState()
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = SpaceOuter)
    ){
        Spacer(Modifier.size(SpaceOuter))

        UserInfoView(
            userData = userData,
            isLogin = isLogin,
            toProfile = toProfile,
            toScan = toScan,
            toLogin = toLogin,
            onLogoutClick = onLogoutClick
        )

        Spacer(Modifier.size(SpaceMedium))

        Column(
            verticalArrangement = Arrangement.spacedBy(SpaceOuter),
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            MyCard1(
                toMessage = {},
                toFriend = {},
                toCode = {},
            )

            MyCard2()
        }

        Spacer(Modifier.size(SpaceMedium))

        if (!userData.isLogin()){
            OutlinedButton(
                onClick = onLogoutClick,
                modifier = Modifier
                    .padding(horizontal = SpaceOuter, vertical = 40.dp)
                    .fillMaxWidth()
            ) {
                Text("退出登录")
            }
        }
    }
}

@Composable
fun UserInfoView(
    userData: UserData,
    isLogin: Boolean,
    toProfile: () -> Unit,
    toScan: () -> Unit,
    toLogin: () -> Unit,
    onLogoutClick: () -> Unit
) {
    DefaultUserProfile(
        toLogin = toLogin,
        toScan = toScan
        )
}

@Composable
private fun DefaultUserProfile(
    toLogin: () -> Unit,
    toScan: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickableNoRipple { toLogin() },
    ) {
        Image(
            painter = painterResource(id = R.drawable.placeholder),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(30.dp).clip(RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp)),
        )

        Spacer(Modifier.size(SpaceMedium))

        Text(
            text = "登录或注册",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Icon(
        imageVector = Icons.Default.ChevronRight,
        contentDescription = null,
        )

        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = toScan) {
            Icon(
                painter = painterResource(id = R.drawable.scan),
                contentDescription = null,
                modifier = Modifier.size(36.dp),
            )
        }
    }
}

@Composable
fun MyCard1(
    toMessage: () -> Unit,
    toFriend: (Int) -> Unit,
    toCode: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
    ) {
        MySettingItem(
            title = "我的消息",
            onClick = toMessage,
        )
        Spacer(Modifier.size(SpaceExtraSmall))
        MySettingItem(
            title = "我的好友",
            onClick = {
                toFriend(0)
            },
        )
        Spacer(Modifier.size(SpaceExtraSmall))
        MySettingItem(
            title = "我的粉丝",
            onClick = {
                toFriend(10)
            },
        )
        Spacer(Modifier.size(SpaceExtraSmall))
        MySettingItem(
            title = "我的二维码",
            onClick = toCode,
        )
    }
}

@Composable
fun MyCard2(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
    ) {
        (1..4).forEach { it ->
            MySettingItem()
            if (it != 4)
                Spacer(Modifier.size(SpaceExtraSmall))
        }
    }
}

@Composable
fun MySettingItem(
    modifier: Modifier = Modifier,
    title: String = "",
    value: String = "",
    onClick: () -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable {
                onClick()
            }
            .padding(
                start = SpaceOuter,
                end = SpaceMedium,
                top = SpaceExtraOuter,
                bottom = SpaceExtraOuter
            ),
    ) {
        Icon(
            imageVector = Icons.Default.QrCode,
            contentDescription = null,
        )

        Spacer(Modifier.size(SpaceMedium))

        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.outline,
            textAlign = TextAlign.Right,
            modifier = Modifier.weight(1f),
        )

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
        )
    }
}