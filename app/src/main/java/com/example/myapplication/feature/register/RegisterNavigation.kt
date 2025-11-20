package com.example.myapplication.feature.register

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val REGISTER_ROUTE = "register"

fun NavController.navigateToRegister() =
    navigate(REGISTER_ROUTE) {
        launchSingleTop = true
    }

fun NavGraphBuilder.registerScreen(
    finishPage: () -> Unit ,
    finishAllPage: () -> Unit,
) {
    composable(REGISTER_ROUTE) {
        RegisterRoute(
            finishPage = finishPage,
            finishAllPage = finishAllPage
        )
    }
}