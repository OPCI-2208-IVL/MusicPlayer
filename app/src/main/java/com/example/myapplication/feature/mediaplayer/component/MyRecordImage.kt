package com.example.myapplication.feature.mediaplayer.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.util.ResourceUtil
import com.google.common.base.Strings

@Composable
fun MyRecordImage(
    icon: String,
    modifier: Modifier = Modifier
){
    if(Strings.isNullOrEmpty(icon)){
        Image(
            painter = painterResource(id = R.drawable.placeholder),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
        )
    } else {
        AsyncImage(
            model = ResourceUtil.abs2rel(icon),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
        )
    }
}