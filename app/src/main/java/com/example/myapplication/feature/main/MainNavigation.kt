package com.example.myapplication.feature.main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.myapplication.feature.splash.SPlASH_ROUTE
import com.example.myapplication.ui.myapp.MyAppUiState

const val MAIN_ROUTE = "main"

fun NavController.navigateToMain() {
    navigate(MAIN_ROUTE) {
        launchSingleTop = true
        popUpTo(SPlASH_ROUTE) {
            inclusive = true
        }
    }
}

fun NavGraphBuilder.mainScreen(
    toSheetDetail: (String) -> Unit,
    toMusicPlayer: () -> Unit,
    appUiState: MyAppUiState,
) {
    composable(MAIN_ROUTE) {
        MainRoute(
            toSheetDetail = toSheetDetail,
            appUiState =  appUiState,
            toMusicPlayer = toMusicPlayer,
        )
    }
}