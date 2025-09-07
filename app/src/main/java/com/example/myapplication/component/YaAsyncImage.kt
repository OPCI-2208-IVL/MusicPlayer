package com.example.myapplication.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.ui.theme.LocalDividerColor
import com.example.myapplication.util.ResourceUtil.abs2rel

@Composable
fun YaASyncImage(
    model: String?,
    modifier: Modifier = Modifier
) {
    if(model == null) {
        Image(
            painter = painterResource(R.drawable.placeholder),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .clip(MaterialTheme.shapes.small)
                .background(LocalDividerColor.current)
        )
    } else {
        AsyncImage(
            model = abs2rel(model),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .clip(MaterialTheme.shapes.small)
                .background(LocalDividerColor.current)
        )
    }
}