package com.example.myapplication.feature.sheetdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChecklistRtl
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.MediaItem
import androidx.palette.graphics.Palette
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.myapplication.R
import com.example.myapplication.component.MyCenterTopAppBar
import com.example.myapplication.component.MyErrorView
import com.example.myapplication.component.MyLoading
import com.example.myapplication.component.YaASyncImage
import com.example.myapplication.component.song.ItemSongSheet
import com.example.myapplication.extension.clickableNoRipple
import com.example.myapplication.feature.mediaplayer.component.MusicListDialog
import com.example.myapplication.feature.mediaplayer.component.MusicPlayerBottomBar
import com.example.myapplication.media.PlaybackState
import com.example.myapplication.model.Sheet
import com.example.myapplication.ui.theme.SpaceExtraSmall2
import com.example.myapplication.ui.theme.SpaceLarge
import com.example.myapplication.ui.theme.SpaceMedium
import com.example.myapplication.ui.theme.SpaceOuter
import com.example.myapplication.ui.theme.SpaceSmall
import com.example.myapplication.util.ResourceUtil

@Composable
fun SheetDetailRoute(
    finishPage: () -> Unit,
    toMusicPlayer: () -> Unit,
    viewModel: SheetDetailViewModel = hiltViewModel()
) {
    val data by viewModel.data.collectAsState()
    val musicDatum by viewModel.playListDatum.collectAsStateWithLifecycle()
    val nowPlaying by viewModel.nowPlaying.collectAsStateWithLifecycle()
    val playbackState by viewModel.playbackState.collectAsStateWithLifecycle()
    val currentPosition by viewModel.currentPosition.collectAsStateWithLifecycle()
    val recordRotation by viewModel.recordRotation.collectAsStateWithLifecycle()
    val showMusicListDialog by viewModel.showMusicListDialog.collectAsStateWithLifecycle()

    SheetDetailScreen(
        finishPage = finishPage,
        data = data,
        onRetry = viewModel::onRetryClick,
        onSongClick = viewModel::onSongClick,
        onCollectClick = viewModel::onCollectClick,
        nowPlaying = nowPlaying,
        playbackState = playbackState,
        currentPosition = currentPosition.toFloat(),
        recordRotation = recordRotation,
        onPlayOrPauseClick = viewModel::onPlayOrPauseClick,
        toggleShowMusicListDialog = viewModel::toggleShowMusicListDialog ,
        toMusicPlayer = toMusicPlayer,
    )

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
    onSongClick: (Int) -> Unit,
    onCollectClick: ()->Unit,
    nowPlaying: MediaItem,
    playbackState: PlaybackState,
    currentPosition: Float = 0F,
    recordRotation: Float = 0F,
    onPlayOrPauseClick: () -> Unit,
    toMusicPlayer: () -> Unit,
    toggleShowMusicListDialog: () -> Unit,
) {
    when (data) {
        is SheetDetailUiState.Loading -> {
            MyLoading()
        }

        is SheetDetailUiState.Success -> {
            ContentView(
                finishPage = finishPage,
                data = data.sheet,
                onSongClick = onSongClick,
                onCollectClick = onCollectClick,
                nowPlaying = nowPlaying,
                playbackState = playbackState,
                currentPosition = currentPosition,
                recordRotation = recordRotation,
                onPlayOrPauseClick = onPlayOrPauseClick,
                toggleShowMusicListDialog = toggleShowMusicListDialog ,
                toMusicPlayer = toMusicPlayer,
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
    onCollectClick: ()->Unit,
    nowPlaying: MediaItem,
    playbackState: PlaybackState,
    currentPosition: Float = 0F,
    recordRotation: Float = 0F,
    onPlayOrPauseClick: () -> Unit,
    toMusicPlayer: () -> Unit,
    toggleShowMusicListDialog: () -> Unit,
) {
    var sheetDetailBackgroundColor by remember {
        mutableStateOf(Color.Black)
    }

    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            MyCenterTopAppBar(
                finishPage = finishPage,
                titleText = data.title,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = sheetDetailBackgroundColor,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                )
            )
        },
        bottomBar = {
            if (nowPlaying.mediaId.isNotBlank()){
                MusicPlayerBottomBar(
                    title = nowPlaying.mediaMetadata.title.toString(),
                    artists = nowPlaying.mediaMetadata.artist.toString(),
                    icon = nowPlaying.mediaMetadata.artworkUri.toString(),
                    isPlaying = playbackState.isPlaying,
                    currentPosition = currentPosition,
                    duration = playbackState.duration.toFloat(),
                    recordRotation = recordRotation,
                    onPlayOrPauseClick = onPlayOrPauseClick,
                    toggleShowMusicListDialog = toggleShowMusicListDialog,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .navigationBarsPadding()
                        .clickable { toMusicPlayer() },
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValue ->
        Box(
            modifier = Modifier.fillMaxSize()
        ){
            CompositionLocalProvider(
                LocalOverscrollFactory provides null
            ){
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValue)
                ) {
                    item {
                        SheetDetailInfo(
                            data = data,
                            backgroundColor = sheetDetailBackgroundColor,
                            onCollectClick = onCollectClick
                        )
                    }

                    data.songs?.let {
                        item {
                            SheetDetailPlayTitle(
                                size = it.size,
                                onPlayAllClick = { onSongClick(0) }
                            )
                        }

                        itemsIndexed(it) { index, data ->
                            ItemSongSheet(
                                data = data,
                                index = index,
                                isPlaying = playbackState.isPlaying,
                                currentPlayMediaID = nowPlaying.mediaId,
                                modifier = Modifier.clickable { onSongClick(index) }
                            )
                        }
                    }
                }
            }
        }

        LaunchedEffect(nowPlaying) {
            if (nowPlaying.mediaId.isNotBlank()) {
                val index = data.songs?.indexOfFirst { it.id == nowPlaying.mediaId } ?: -1
                if (index >= 0) {
                    listState.animateScrollToItem(index, -200)
                }
            }
        }

        data.icon?.let {
            val iconUrl = ResourceUtil.abs2rel(data.icon)
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(iconUrl)
                    .allowHardware(false)
                    .size(Size.ORIGINAL)
                    .build()
            )
            val state = painter.state
            if (state is AsyncImagePainter.State.Success) {
                state.result.drawable.toBitmap().let { bitmap ->
                    Palette.from(bitmap).generate().let { palette ->
                        val dominantSwatch = palette.dominantSwatch
                        dominantSwatch?.rgb?.let { colorValue ->
                            sheetDetailBackgroundColor = Color(colorValue)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SheetDetailInfo(
    data: Sheet,
    backgroundColor: Color,
    onCollectClick: ()->Unit,
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(SpaceOuter)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding()
        ) {
            YaASyncImage(
                model = data.icon,
                modifier = Modifier
                    .size(80.dp)
                    .clip(shape = MaterialTheme.shapes.small)
            )

            Spacer(modifier = Modifier.size(SpaceOuter))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = data.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    YaASyncImage(
                        model = data.user?.icon,
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.size(SpaceSmall))
                    Text(
                        text = data.user?.nickname ?: "",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.size(SpaceLarge))

        Text(
            text = data.detail?:"",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.size(SpaceOuter))

        val buttonBackground = MaterialTheme.colorScheme.surfaceBright

        val itemButtonBackground by remember { mutableStateOf(buttonBackground) }

        Row(
            horizontalArrangement = Arrangement.spacedBy(SpaceMedium),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            FilledTonalButton(
                onClick = {},
                colors = ButtonDefaults.buttonColors().copy(
                    containerColor = itemButtonBackground,
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Share, contentDescription = null)
                Text(text = "分享", modifier = Modifier.padding(start = 4.dp))
            }

            FilledTonalButton(
                onClick = onCollectClick,
                colors = ButtonDefaults.buttonColors().copy(
                    containerColor = if (data.isCollected) MaterialTheme.colorScheme.primary else itemButtonBackground,
                    contentColor = if (data.isCollected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Text(text = "收藏", modifier = Modifier.padding(start = 4.dp))
            }

            FilledTonalButton(
                onClick = {},
                colors = ButtonDefaults.buttonColors().copy(
                    containerColor =  itemButtonBackground,
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.AutoMirrored.Filled.Comment, contentDescription = null)
                Text(text = "评论", modifier = Modifier.padding(start = 4.dp))
            }

        }
    }
}

@Composable
fun SheetDetailPlayTitle(
    size: Int,
    onPlayAllClick: ()->Unit,
    onDownloadClick: ()->Unit = {},
    onSelectClick: ()->Unit = {},
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(end = SpaceMedium, top = SpaceExtraSmall2, bottom = SpaceExtraSmall2)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(1f)
                .padding(start = SpaceMedium)
                .clickableNoRipple { onPlayAllClick() }
        ) {
            Image(
                painter = painterResource(R.drawable.music_play),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                    .padding(SpaceSmall)
                    .align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.size(SpaceSmall))
            Column {
                Text(
                    text = "播放全部",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                )

                Spacer(modifier = Modifier.height(4.dp)) // 增加垂直间距

                Text(
                    text = "$size 首",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline,
                )
            }
        }

        IconButton(
            onClick = onDownloadClick,
        ) {
            Icon(
                imageVector = Icons.Default.Download,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
            )
        }

        IconButton(
            onClick = onSelectClick,
        ) {
            Icon(
                imageVector = Icons.Default.ChecklistRtl,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
            )
        }

    }
}