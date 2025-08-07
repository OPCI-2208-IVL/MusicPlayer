package com.example.myapplication.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.extension.clickableNoRipple
import com.example.myapplication.feature.main.TopLeveDestination


@Composable
fun NavigationBar(
    destinations: List<TopLeveDestination>,
    currentDestination: String,
    onNavigateToDestination: (Int) -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
    ){
        destinations.forEachIndexed { index, destination ->

            val selected:Boolean = destination.route == currentDestination

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
                    .padding(vertical = 2.dp)
                    .clickableNoRipple {
                        onNavigateToDestination(index)
                    }
            ) {
                Image(
                    painter = painterResource(id =
                        if(selected) destination.selectedIcon
                        else destination.unSelectedIcon
                    ),
                    contentDescription = stringResource(id = destination.titleTextID),
                    modifier = Modifier.size(25.dp)
                )

                Spacer(modifier = Modifier.size(5.dp))

                Text(text = stringResource(id = destination.titleTextID),
                    color =
                        if (selected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.scrim
                )
            }
        }
    }
}