package com.example.myapplication.feature.mediaplayer

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.MediaItem
import com.example.myapplication.component.BackgroundContent
import com.example.myapplication.extension.clickableNoRipple
import com.example.myapplication.R
import com.example.myapplication.database.model.SongEntity
import com.example.myapplication.extension.asFormatTimeString
import com.example.myapplication.feature.lyric.LyricList
import com.example.myapplication.feature.mediaplayer.component.MusicListDialog
import com.example.myapplication.feature.mediaplayer.component.MyRecordImage
import com.example.myapplication.media.PlaybackState
import com.example.myapplication.model.lyric.Lyric
import com.example.myapplication.model.PlayRepeatMode
import com.example.myapplication.ui.theme.SpaceLarge
import com.example.myapplication.ui.theme.SpaceOuter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun MusicPlayerRoute(
    viewModel: MusicPlayerViewModel = hiltViewModel(),
    finishPage: () -> Unit
) {
    val nowPlaying by viewModel.nowPlaying.collectAsStateWithLifecycle()
    val playbackState by viewModel.playbackState.collectAsStateWithLifecycle()
    val currentPosition by viewModel.currentPosition.collectAsStateWithLifecycle()
    val playRepeatMode by viewModel.playRepeatMode.collectAsStateWithLifecycle()
    val musicDatum by viewModel.playListDatum.collectAsStateWithLifecycle()
    val recordRotation by viewModel.recordRotation.collectAsStateWithLifecycle()
    val showMusicListDialog by viewModel.showMusicListDialog.collectAsStateWithLifecycle()
    val showRecord by viewModel.showRecord.collectAsStateWithLifecycle()
    val lyric by viewModel.lyric.collectAsStateWithLifecycle()

    MusicPlayerScreen(
        finishPage = finishPage,
        nowPlaying = nowPlaying,
        playbackState = playbackState,
        currentPosition = currentPosition,
        onSeek = viewModel::onSeek,
        playRepeatMode = playRepeatMode,
        onChangeRepeatModeClick = viewModel::onChangeRepeatModeClick,
        onPreviousClick = viewModel::onPreviousClick,
        onPlayOrPauseClick = viewModel::onPlayOrPauseClick,
        onNextClick = viewModel::onNextClick,
        toggleShowMusicListDialog = viewModel::toggleShowMusicListDialog,
        datum = musicDatum,
        onScrollPage = viewModel::playIndex,
        recordRotation = recordRotation,
        onPauseRecordRotation = viewModel::onPauseRecordRotation,
        toggleRecordAndLyric = viewModel::toggleRecordAndLyric,
        clearPosition = viewModel::clearPosition,
        showRecord = showRecord,
        onLyricPlayClick = viewModel::onSeek,
        lyric = lyric
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
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicPlayerScreen(
    finishPage: () -> Unit,
    nowPlaying: MediaItem,
    playbackState: PlaybackState,
    currentPosition: Long,
    onSeek: (Float) -> Unit,
    playRepeatMode: PlayRepeatMode,
    onChangeRepeatModeClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onPlayOrPauseClick: () -> Unit,
    onNextClick: () -> Unit,
    toggleShowMusicListDialog: () -> Unit,
    datum: List<SongEntity>,
    onScrollPage: (Int) -> Unit = {},
    recordRotation: Float = 0f,
    onPauseRecordRotation: (Boolean) -> Unit = {},
    toggleRecordAndLyric: () -> Unit = {},
    clearPosition: () -> Unit = {},
    showRecord: Boolean,
    pauseRecordRotation: Boolean = false,
    onLyricPlayClick: (Long) -> Unit = {},
    lyric: Lyric
) {
    Scaffold (
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick =  finishPage){
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = null
                        )
                    }
                },
                title = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = nowPlaying.mediaMetadata.title.toString(),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            text = nowPlaying.mediaMetadata.artist.toString(),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ){ paddingValues ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val recordThumbMarginTop = maxWidth * 0.31875f
            val recordMarginTop = maxWidth * 0.49f
            ConstraintLayout(modifier = Modifier.fillMaxSize()){
                val (musicPlayerBackground, recordThumb) = createRefs()

                BackgroundContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .constrainAs(musicPlayerBackground) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )

                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = SpaceLarge)
                ){
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize(),
                        color = Color.Transparent
                    ) {
                        AnimatedVisibility(
                            visible = showRecord,
                            enter = fadeIn(),
                            exit = fadeOut(),
                            modifier = Modifier.fillMaxSize()
                        )
                        {
                            RecordPagerView(
                                datum = datum,
                                nowPlaying = nowPlaying,
                                onScrollPage = onScrollPage,
                                contentPaddingTop = recordMarginTop.value.dp,
                                recordRotation = recordRotation,
                                onPauseRecordRotation = onPauseRecordRotation,
                                clearPosition = clearPosition,
                                modifier = Modifier
                                    .clickableNoRipple {
                                        toggleRecordAndLyric()
                                    }
                            )
                        }

                        AnimatedVisibility(
                            visible = !showRecord,
                            enter = fadeIn(),
                            exit = fadeOut(),
                            modifier = Modifier.fillMaxSize()
                        )
                        {
                            LyricList(
                                data = lyric,
                                currentPosition = currentPosition,
                                onLyricPlayClick = onLyricPlayClick,
                                modifier = Modifier
                                    .padding(paddingValues = paddingValues)
                                    .clickableNoRipple {
                                        toggleRecordAndLyric()
                                    }
                            )
                        }

                    }

                    PlayMediaOtherButtons(
                    )

                    Spacer(Modifier.size(SpaceOuter))

                    //进度信息
                    ProgressInfo(
                        currentPosition = currentPosition,
                        duration = playbackState.durationFormat,
                        onSeek = onSeek,
                    )

                    Spacer(Modifier.size(SpaceOuter))

                    PlayerMediaButtons(
                        isPlaying = playbackState.isPlaying,
                        playRepeatMode = playRepeatMode,
                        onChangeRepeatModeClick = onChangeRepeatModeClick,
                        onPreviousClick = onPreviousClick,
                        onPlayOrPauseClick = onPlayOrPauseClick,
                        onNextClick = onNextClick,
                        toggleShowMusicListDialog = toggleShowMusicListDialog
                    )
                }

                if (showRecord) {
                    RecordThumb(
                        isPlaying = playbackState.isPlaying  && !pauseRecordRotation,
                        modifier = Modifier
                            .constrainAs(recordThumb) {
                                top.linkTo(parent.top, margin = recordThumbMarginTop.value.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.percent(0.48125f)
                            }
                    )
                }
            }
        }
    }
}

@Composable
fun RecordPagerView(
    datum: List<SongEntity>,
    nowPlaying: MediaItem,
    onScrollPage: (Int) -> Unit,
    contentPaddingTop: Dp,
    recordRotation: Float,
    onPauseRecordRotation: (Boolean) -> Unit,
    clearPosition: () -> Unit ,
    modifier: Modifier = Modifier
    ) {
        if (datum.isNotEmpty()) {
            var currentIndex by rememberSaveable {
                mutableIntStateOf(0)
            }

            currentIndex = datum.indexOfFirst {
                it.id == nowPlaying.mediaId
            }

            val pagerState = rememberPagerState(
                initialPage = currentIndex,
                pageCount = { datum.size },
            )

            HorizontalPager(
                state = pagerState,
                beyondViewportPageCount = 2,
                modifier = modifier
                    .fillMaxWidth()
            ) {
                RecordView(
                    data = datum[it],
                    recordRotation = if (currentIndex == it)  recordRotation else 0f,
                    modifier = Modifier
                        .padding(top = contentPaddingTop)
                        .fillMaxWidth()
                        .aspectRatio(1f)
                )
            }

            LaunchedEffect(pagerState) {
                snapshotFlow {
                    pagerState.settledPage
                }
                    .distinctUntilChanged()
                    .collectLatest {
                        if (currentIndex != it) {
                            Log.d("TAG", "RecordPagerView: currentPage $it $currentIndex")
                            clearPosition()
                            onScrollPage(it)
                        }
                    }
            }

            var isProgrammaticScroll by remember { mutableStateOf(false) }

            LaunchedEffect(nowPlaying) {
                if (currentIndex != pagerState.currentPage) {
                    isProgrammaticScroll = true
                    clearPosition()
                    pagerState.animateScrollToPage(page = currentIndex)
                }
            }

            LaunchedEffect(pagerState) {
                snapshotFlow { pagerState.isScrollInProgress }.collect { isScrolling ->
                    if (isProgrammaticScroll) {
                        if (!isScrolling) {
                            isProgrammaticScroll = false
                        }
                    } else {
                        if (isScrolling) {
                            onPauseRecordRotation(true)
                        } else {
                            onPauseRecordRotation(false)
                        }
                    }
                }
            }
        }
}

@Composable
fun RecordView(
    data: SongEntity,
    recordRotation: Float,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
    ) {
        val (recordBackground) = createRefs()
        Box(
            modifier = Modifier
                .constrainAs(recordBackground) {
                    centerHorizontallyTo(parent)
                    width = Dimension.percent(0.759375f)
                }
                .aspectRatio(1f)
                .graphicsLayer(rotationZ = recordRotation)

        ){
            MyRecordImage(
                icon = data.icon,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize(0.64f)
            )

            Image(
                painter = painterResource(id = R.drawable.music_record_ring),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize()
            )
        }
    }
}
@Composable
fun RecordThumb(
    isPlaying: Boolean,
    modifier: Modifier = Modifier,
){
    val rotationAngle by animateFloatAsState(
        targetValue = if (isPlaying) 0f else -25f,
        animationSpec = tween(durationMillis = 300),
        label = "RecordThumb"
    )

    Image(
        painter = painterResource(id = R.drawable.music_record_thumb),
        contentDescription = null,
        contentScale = ContentScale.Inside,
        modifier = modifier
            .graphicsLayer (
                rotationZ = rotationAngle ,
                transformOrigin = TransformOrigin(0.5f,0.12f)
            )
    )
}

@Composable
fun PlayMediaOtherButtons(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        MusicControlSmallButton(
            R.drawable.music_download,
            modifier = Modifier
                .weight(1f)
                .clickableNoRipple {

                }
        )
        MusicControlSmallButton(
            R.drawable.music_collect,
            modifier = Modifier
                .weight(1f)
                .clickableNoRipple {

                }
        )
        MusicControlSmallButton(
            R.drawable.music_comment,
            modifier = Modifier
                .weight(1f)
                .clickableNoRipple {

                }
        )
//        MusicControlSmallButton(
//            R.drawable.music_sing,
//            modifier = Modifier
//                .weight(1f)
//                .clickableNoRipple {
//
//                }
//        )
        MusicControlSmallButton(
            R.drawable.music_equalizer,
            modifier = Modifier
                .weight(1f)
                .clickableNoRipple {

                }
        )
    }
}

@Composable
fun PlayerMediaButtons(
    isPlaying: Boolean,
    playRepeatMode: PlayRepeatMode,
    onChangeRepeatModeClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onPlayOrPauseClick: () -> Unit,
    onNextClick: () -> Unit,
    toggleShowMusicListDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        MusicControlButton(
            when (playRepeatMode) {
                PlayRepeatMode.REPEAT_LIST -> R.drawable.music_repeat_list
                PlayRepeatMode.REPEAT_ONE -> R.drawable.music_repeat_single
                PlayRepeatMode.REPEAT_SHUFFLE, PlayRepeatMode.REPEAT_UNSPECIFIED -> R.drawable.music_repeat_random
            },
            modifier = Modifier
                .weight(1f)
                .clickableNoRipple {
                    onChangeRepeatModeClick()
                }
        )
        MusicControlMiddleButton(
            R.drawable.music_previous,
            modifier = Modifier
                .weight(1f)
                .clickableNoRipple {
                    onPreviousClick()
                }
        )
        Image(
            painter =
            painterResource(
                id =
                if (isPlaying)
                    R.drawable.music_pause
                else
                    R.drawable.music_play
            ),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .weight(1f)
                .clickableNoRipple {
                    onPlayOrPauseClick()
                }
        )
        MusicControlMiddleButton(
            R.drawable.music_next,
            modifier = Modifier
                .weight(1f)
                .clickableNoRipple {
                    onNextClick()
                }
        )
        MusicControlButton(
            R.drawable.music_list,
            modifier = Modifier
                .weight(1f)
                .clickableNoRipple {
                    toggleShowMusicListDialog()
                }
        )
    }
}

@Composable
fun MusicControlButton(icon: Int, modifier: Modifier) {
    Image(
        painter =
        painterResource(
            id = icon
        ),
        contentDescription = null,
        modifier = modifier
            .aspectRatio(1f)
            .padding(SpaceOuter)
//            .background(Color.Blue)
    )
}

@Composable
fun MusicControlMiddleButton(icon: Int, modifier: Modifier) {
    Image(
        painter =
        painterResource(
            id = icon
        ),
        contentDescription = null,
        modifier = modifier
            .aspectRatio(1f)
            .padding(19.dp)
//            .background(Color.Blue)
    )
}

@Composable
fun MusicControlSmallButton(icon: Int, modifier: Modifier) {
    Image(
        painter =
        painterResource(
            id = icon
        ),
        contentDescription = null,
        modifier = modifier
            .aspectRatio(1f)
            .padding(SpaceOuter)
    )
}

@Composable
fun ProgressInfo(
    currentPosition: Long,
    duration: Long,
    onSeek: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = SpaceOuter),
    ) {
        Slider(
            modifier = Modifier
                .fillMaxWidth(),
            value = currentPosition.toFloat(),
            valueRange = 0f..duration.toFloat(),
            onValueChange = onSeek,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = currentPosition.asFormatTimeString(),
                style = MaterialTheme.typography.bodySmall,
                color = Color.White
            )
            Text(
                text = duration.asFormatTimeString(),
                style = MaterialTheme.typography.bodySmall,
                color = Color.White
            )
        }
    }
}
