package com.example.myapplication.feature.loginhome

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val LOGIN_HOME_ROUTE = "login_home"

fun NavController.navigateToLoginHome() = navigate(LOGIN_HOME_ROUTE)

fun NavController.finishAllLoginPage() {
    popBackStack(LOGIN_HOME_ROUTE, true)
}

fun NavGraphBuilder.loginHomeScreen(
    finishPage: () -> Unit,
    toLogin: () -> Unit,
    toCodeLogin: () -> Unit,
    finishAllLoginPage: () -> Unit,
) {
    composable(LOGIN_HOME_ROUTE) {
        LoginHomeRoute(
            finishPage = finishPage,
            toLogin = toLogin,
            toCodeLogin = toCodeLogin,
            finishAllLoginPage = finishAllLoginPage
        )
    }
}