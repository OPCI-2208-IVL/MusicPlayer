package com.example.myapplication.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.util.ResourceUtil.abs2rel

@Composable
fun BackgroundContent(
    modifier: Modifier = Modifier,
    data: String? = null,
){
    if(data == null) {
        Image(
            painter = painterResource(R.drawable.music_player_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
        )
    } else {
        AsyncImage(
            model = abs2rel(data),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
        )
    }
}