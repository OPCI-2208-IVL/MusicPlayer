package com.example.myapplication.feature.createsheet

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.myapplication.feature.sheetdetail.SHEET_ID

const val CREATE_SHEET_ROUTE = "create_sheet"

fun NavController.navigateToCreateSheet(sheetId: String = "") =
    navigate("${CREATE_SHEET_ROUTE}/$sheetId")

fun NavGraphBuilder.createSheetScreen(
    finishPage: () -> Unit,
) {
    composable("${CREATE_SHEET_ROUTE}/{${SHEET_ID}}") {
        CreateSheetRoute(
            finishPage = finishPage,
        )
    }
}