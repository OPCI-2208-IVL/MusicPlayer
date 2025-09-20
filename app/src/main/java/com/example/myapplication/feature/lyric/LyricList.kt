package com.example.myapplication.feature.lyric

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.style.TextAlign
import com.example.myapplication.model.lyric.Lyric
import com.example.myapplication.ui.theme.SpaceLarge
import com.example.myapplication.ui.theme.SpaceSmall

@Composable
fun LyricList(
    data: Lyric,
    currentPosition : Long,
    onLyricPlayClick : (Long) -> Unit,
    modifier: Modifier = Modifier
){
    val listState = rememberLazyListState()
    var currentIndex by remember { mutableIntStateOf(0) }
    Box(
        modifier = modifier
            .fillMaxSize()
            .onGloballyPositioned { layoutCoordinates ->  

            }
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
        ) {
            itemsIndexed(data.datum){index, lyricLine ->

                Text(
                    text = lyricLine.data,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (currentIndex == index) MaterialTheme.colorScheme.outlineVariant else MaterialTheme.colorScheme.outline,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = SpaceLarge, vertical = SpaceSmall)
                )
            }
        }
    }

    LaunchedEffect(currentPosition) {
        for(i in data.datum.indices){
            val line = data.datum[i]
            if(currentPosition in line.startTime until line.endTime){
                currentIndex = i
                listState.animateScrollToItem(if(i - 7 >= 0) i - 7  else 0)
                break
            }
        }
    }
}