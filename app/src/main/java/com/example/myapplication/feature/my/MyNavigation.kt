package com.example.myapplication.feature.my

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.myapplication.ui.myapp.MyAppUiState


const val MY_ROUTE = "my"

fun NavController.navigateToMy() =
    navigate(MY_ROUTE) {
        launchSingleTop = true
    }

fun NavGraphBuilder.myScreen(
    finishPage: () -> Unit,
    finishAllPage: () -> Unit,
    appUiState: MyAppUiState,
    toLogin: () -> Unit,
    toSheetDetail: (String) -> Unit,
    toLocalMusic: () -> Unit,
    toScanLocalMusic: () -> Unit,
    toEditSheet: () -> Unit,
) {
    composable(MY_ROUTE) {
        MyRoute(
            appUiState = appUiState,
            toLogin = toLogin,
            toSheetDetail = toSheetDetail,
            toLocalMusic = toLocalMusic,
            toScanLocalMusic = toScanLocalMusic,
            toEditSheet = toEditSheet
        )
    }
}