package com.example.myapplication.feature.sheetdetail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val SHEET_DETAIL_ROUTE = "sheet_detail"
const val SHEET_ID = "sheet_id"

fun NavController.navigateToSheetDetail(sheetID: String) {
    navigate("${SHEET_DETAIL_ROUTE}/$sheetID")
}

fun NavGraphBuilder.sheetDetail(
    finishPage: () -> Unit
) {
    composable("${SHEET_DETAIL_ROUTE}/{${SHEET_ID}}") {
        SheetDetailRoute(
            finishPage = finishPage
        )
    }
}