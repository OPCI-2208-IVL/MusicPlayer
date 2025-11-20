package com.example.myapplication.feature.my

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable


const val MY_ROUTE = "my"

fun NavController.navigateToMy() =
    navigate(MY_ROUTE) {
        launchSingleTop = true
    }

fun NavGraphBuilder.myScreen(
    finishPage: () -> Unit ,
    finishAllPage: () -> Unit,
) {
    composable(MY_ROUTE) {
        MyRoute()
    }
}