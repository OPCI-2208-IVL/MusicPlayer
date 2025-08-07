package com.example.myapplication.feature.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.myapplication.component.NavigationBar
import com.example.myapplication.feature.discovery.DiscoveryRoute
import com.example.myapplication.feature.my.MyRoute
import com.example.myapplication.feature.settings.SettingsRoute
import kotlinx.coroutines.launch


@Composable
fun MainRoute() {
    MainScreen()
}

@Composable
fun MainScreen() {
    val pageState = rememberPagerState {
        3
    }

    var currentDestination by rememberSaveable {
        mutableStateOf(TopLeveDestination.DISCOVER.route)
    }

    val scope = rememberCoroutineScope ()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalPager(
            state = pageState,
            userScrollEnabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { page ->
            when (page) {
                0 -> DiscoveryRoute({})
                1 -> MyRoute()
                2 -> SettingsRoute()
            }
        }


        NavigationBar(
            destinations = TopLeveDestination.entries,
            currentDestination = currentDestination,
            onNavigateToDestination = {index ->
                currentDestination = TopLeveDestination.entries[index].route
                scope.launch {
                    pageState.scrollToPage(index)
                }.start()
            },
            modifier = Modifier
        )
    }
}
