package com.example.myapplication.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.extension.clickableNoRipple
import com.example.myapplication.ui.theme.Space3XLarge
import com.example.myapplication.ui.theme.SpaceOuter

@Composable
fun MyErrorView (
    modifier: Modifier = Modifier,
    message: String = "加载失败",
    retryButtonText: String = "重新加载",
    icon: Int = R.drawable.bg_error,
    onRetryClick: () -> Unit = {}
) {
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .clickableNoRipple {
                onRetryClick()
            }
            .padding(SpaceOuter)
    ){
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(id = icon),
            contentDescription = null
        )

        Spacer(modifier = Modifier.size(Space3XLarge))

        Text(
            text = message,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline,
        )

        Spacer(modifier = Modifier.size(Space3XLarge))

        OutlinedButton(
            onClick = onRetryClick,
            border = BorderStroke(width = 0.5.dp, color = MaterialTheme.colorScheme.outline),
            modifier = Modifier
                .padding(horizontal = 50.dp)
                .widthIn(200.dp)
        ) {
            Text(
                text = retryButtonText
            )
        }
    }
}