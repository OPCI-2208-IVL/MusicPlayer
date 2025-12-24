package com.example.myapplication.component.song

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.model.Song
import com.example.myapplication.ui.theme.SpaceSmall

@Composable
fun ItemSongSheet(
    data: Song,
    index: Int,
    isPlaying: Boolean,
    modifier: Modifier = Modifier,
    currentPlayMediaID: String = ""
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(
                end = SpaceSmall,
                top = SpaceSmall,
                bottom = SpaceSmall
            )
    ) {

        Box(
            modifier = Modifier.size((50.dp))
        ){
            if(currentPlayMediaID == data.id){
                if (isPlaying){
                    ItemMusicPlayingAnimation(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(24.dp),
                    )
                } else{
                    Image(
                        painter = painterResource(R.drawable.music_playing1),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(24.dp)
                    )
                }
            } else {
                Text(
                    text = "${index + 1}",
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        Column(
            modifier = Modifier.weight(1f)
        ){
            Text(
                text = data.title,
                color =
                if (currentPlayMediaID == data.id)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
            )

            Text(
                text = "${data.artist} - ${data.album}",
                color = MaterialTheme.colorScheme.outline,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
            )
        }

        IconButton(
            onClick = {}
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.surfaceTint
            )
        }
    }
}

@Composable
fun ItemMusicPlayingAnimation(
    modifier: Modifier = Modifier
) {
    val imageVector by rememberSaveable {
        mutableStateOf(
            listOf(
                R.drawable.music_playing1,
                R.drawable.music_playing2,
                R.drawable.music_playing3,
                R.drawable.music_playing4
            )
        )
    }

    val infiniteTransition = rememberInfiniteTransition(label = "MusicPlaying")

    val frameIndex by infiniteTransition.animateValue(
        initialValue = 0,
        targetValue = imageVector.size - 1,
        typeConverter = Int.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "MusicPlayingAnimation"
    )

    Image(
        painter = painterResource(imageVector[frameIndex]),
        contentDescription = null,
        modifier = modifier
    )
}
