package com.example.myapplication.feature.search

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable


const val SEARCH_ROUTE = "search"

fun NavController.navigateToSearch() =
    navigate(SEARCH_ROUTE)

fun NavGraphBuilder.searchScreen(
    finishPage: () -> Unit,
    toSheetDetail: (String) -> Unit,
    toUserDetail: (String) -> Unit,
){
    composable(
        SEARCH_ROUTE,
    ) {
        SearchRoute(
            finishPage = finishPage,
            toSheetDetail = toSheetDetail,
            toUserDetail = toUserDetail,
        )
    }
}