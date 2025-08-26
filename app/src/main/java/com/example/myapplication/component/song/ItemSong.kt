package com.example.myapplication.component.song

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.myapplication.component.YaASyncImage
import com.example.myapplication.model.Song
import com.example.myapplication.ui.theme.SpaceMedium
import com.example.myapplication.ui.theme.SpaceSmall
import com.example.myapplication.ui.theme.SpaceTip

@Composable
fun ItemSong(
    data: Song,
    modifier: Modifier = Modifier
){

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        YaASyncImage(
            model = data.icon,
            modifier = modifier.size(SpaceTip)
        )

        Column (
            modifier = modifier
                .weight(1f)
                .padding(horizontal = SpaceMedium)
        ){
            Text(
                text = data.title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(
                modifier = modifier.height(SpaceSmall)
            )
            Text(
                text = "${data.artist} - ${data.album}"
            )
        }
    }
}