package com.example.myapplication.feature.main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.myapplication.feature.splash.SPlASH_ROUTE

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
    toSheetDetail: (String) -> Unit
) {
    composable(MAIN_ROUTE) {
        MainRoute(toSheetDetail = toSheetDetail)
    }
}