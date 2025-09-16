package com.example.myapplication.feature.mediaplayer.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.component.MusicCircularProgressBar
import com.example.myapplication.extension.clickableNoRipple
import com.example.myapplication.ui.theme.SpaceExtraSmall
import com.example.myapplication.ui.theme.SpaceExtraSmall2
import com.example.myapplication.ui.theme.SpaceLarge
import com.example.myapplication.ui.theme.SpaceMedium
import com.example.myapplication.ui.theme.SpaceOuter
import com.example.myapplication.ui.theme.SpaceSmall

@Composable
fun MusicPlayerBottomBar(
    modifier: Modifier = Modifier,
    title: String,
    artists: String,
    icon: String = "",
    isPlaying: Boolean,
    currentPosition: Float,
    duration: Float,
    recordRotation: Float,
    onPlayOrPauseClick: () -> Unit,
    toggleShowMusicListDialog: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Spacer(Modifier.size(SpaceExtraSmall))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(horizontal = SpaceOuter, vertical = SpaceExtraSmall2)
        ) {
            Box(
                modifier = modifier
                    .size(42.dp)
                    .graphicsLayer(rotationZ = recordRotation)
            ){
                MyRecordImage(
                    icon = icon, modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize(0.64f)
                )

                Image(
                    painter =
                    painterResource(
                        id = R.drawable.music_record_ring,
                    ),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            Spacer(Modifier.size(SpaceMedium))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
            )
            Spacer(Modifier.size(SpaceSmall))
            Text(
                text = "-",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier
            )
            Spacer(Modifier.size(SpaceSmall))
            Text(
                text = artists,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.weight(1f)
            )

            Box(modifier = Modifier.height(26.dp)) {
                MusicCircularProgressBar(
                    progress = currentPosition,
                    progressMax = duration,
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f)
                )

                Image(
                    painter =
                    painterResource(
                        id =
                        if (isPlaying)
                            R.drawable.music_pause
                        else
                            R.drawable.music_play,
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(18.dp)
                        .clickableNoRipple {
                            onPlayOrPauseClick()
                        }
                )
            }

            Spacer(Modifier.size(SpaceLarge))

            Image(
                painter =
                painterResource(
                    id =
                    R.drawable.music_list_small,
                ),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                modifier = Modifier
                    .size(25.dp)
                    .clickableNoRipple {
                        toggleShowMusicListDialog()
                    }
            )
        }

    }
}

