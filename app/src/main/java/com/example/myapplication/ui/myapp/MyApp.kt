package com.example.myapplication.ui.myapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.myapplication.data.repository.UserDataRepository
import com.example.myapplication.feature.main.mainScreen
import com.example.myapplication.feature.main.navigateToMain
import com.example.myapplication.feature.mediaplayer.musicPlayerScreen
import com.example.myapplication.feature.mediaplayer.navigateToMusicPlayer
import com.example.myapplication.feature.sheetdetail.navigateToSheetDetail
import com.example.myapplication.feature.sheetdetail.sheetDetail
import com.example.myapplication.feature.splash.SPlASH_ROUTE
import com.example.myapplication.feature.splash.splashScreen

@Composable
fun Myapp(
    navController: NavHostController,
    userDataRepository: UserDataRepository,
    appUiState: MyAppUiState = rememberMyAppUiState(userDataRepository = userDataRepository)
) {
    NavHost(navController = navController, startDestination = SPlASH_ROUTE) {
        splashScreen(
            toMain = navController::navigateToMain
        )
        mainScreen(
            appUiState = appUiState,
            toSheetDetail = navController::navigateToSheetDetail,
            toMusicPlayer = navController::navigateToMusicPlayer
        )
        sheetDetail(
            finishPage = navController::popBackStack,
            toMusicPlayer = navController::navigateToMusicPlayer
        )
        musicPlayerScreen(
            finishPage = navController::popBackStack
        )
    }
}