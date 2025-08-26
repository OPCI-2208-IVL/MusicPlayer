package com.example.myapplication.feature.splash

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable


const val SPlASH_ROUTE = "splash"

fun NavGraphBuilder.splashScreen(
    toMain: () -> Unit,
) {
    composable(SPlASH_ROUTE) {
        SplashRoute(
            toMain = toMain
        )
    }
}
