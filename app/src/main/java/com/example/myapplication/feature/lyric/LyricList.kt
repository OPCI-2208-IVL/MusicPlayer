package com.example.myapplication.feature.lyric

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myapplication.model.Lyric

@Composable
fun LyricList(
    data: Lyric,
    currentPosition : Long,
    onLyricPlayClick : (Long) -> Unit,
    modifier: Modifier = Modifier
){
    Text("Lyric List", modifier = modifier)
}