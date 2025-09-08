package com.example.myapplication.feature.mediaplayer

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.MediaItem
import com.example.myapplication.component.BackgroundContent
import com.example.myapplication.extension.clickableNoRipple
import com.example.myapplication.R
import com.example.myapplication.extension.asFormatTimeString
import com.example.myapplication.media.PlaybackState
import com.example.myapplication.ui.theme.SpaceLarge
import com.example.myapplication.ui.theme.SpaceOuter

@Composable
fun MusicPlayerRoute(
    viewModel: MusicPlayerViewModel = hiltViewModel(),
    finishPage: () -> Unit
) {
    val nowPlaying by viewModel.nowPlaying.collectAsStateWithLifecycle()
    val playbackState by viewModel.playbackState.collectAsStateWithLifecycle()
    val currentPosition by viewModel.currentPosition.collectAsStateWithLifecycle()

    MusicPlayerScreen(
        finishPage = finishPage,
        nowPlaying = nowPlaying,
        playbackState = playbackState,
        currentPosition = currentPosition,
        onSeek = viewModel::onSeek
    )
}

@SuppressLint("UnusedBoxWithConstraintsScope", "UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicPlayerScreen(
    finishPage: () -> Unit,
    nowPlaying: MediaItem,
    playbackState: PlaybackState,
    currentPosition: Long,
    onSeek: (Float) -> Unit
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
    ){
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
        ) {
            ConstraintLayout(modifier = Modifier.fillMaxSize()){
                val (musicPlayerBackground, recordThumb, recordBackground) = createRefs()

                BackgroundContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .constrainAs(musicPlayerBackground){
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
                        onChangeRepeatModeClick = {},
                        onPreviousClick = {},
                        onPlayOrPauseClick = {},
                        onNextClick = {},
                        onMusicListClick = {}
                    )
                }
            }
        }
    }
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
//    playRepeatMode: PlaybackMode,
    onChangeRepeatModeClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onPlayOrPauseClick: () -> Unit,
    onNextClick: () -> Unit,
    onMusicListClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        MusicControlButton(
//            when (playRepeatMode) {
//                PlaybackMode.REPEAT_LIST -> R.drawable.music_repeat_list
//                PlaybackMode.REPEAT_ONE -> R.drawable.music_repeat_single
//                PlaybackMode.REPEAT_SHUFFLE, PlaybackMode.REPEAT_UNSPECIFIED -> R.drawable.music_repeat_random
//            },
            R.drawable.music_repeat_list,
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
                    onMusicListClick()
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
