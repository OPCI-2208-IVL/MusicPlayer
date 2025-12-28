package com.example.myapplication.ui.myapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.myapplication.data.repository.UserDataRepository
import com.example.myapplication.feature.login.loginScreen
import com.example.myapplication.feature.login.navigateToLogin
import com.example.myapplication.feature.loginhome.finishAllLoginPage
import com.example.myapplication.feature.loginhome.loginHomeScreen
import com.example.myapplication.feature.loginhome.navigateToLoginHome
import com.example.myapplication.feature.main.mainScreen
import com.example.myapplication.feature.main.navigateToMain
import com.example.myapplication.feature.mediaplayer.musicPlayerScreen
import com.example.myapplication.feature.mediaplayer.navigateToMusicPlayer
import com.example.myapplication.feature.my.navigateToMy
import com.example.myapplication.feature.register.navigateToRegister
import com.example.myapplication.feature.register.registerScreen
import com.example.myapplication.feature.sheetdetail.navigateToSheetDetail
import com.example.myapplication.feature.sheetdetail.sheetDetail
import com.example.myapplication.feature.splash.SPlASH_ROUTE
import com.example.myapplication.feature.splash.splashScreen
import com.example.myapplication.util.SuperUrlUtil

@Composable
fun Myapp(
    navController: NavHostController,
    userDataRepository: UserDataRepository,
    appUiState: MyAppUiState = rememberMyAppUiState(userDataRepository = userDataRepository)
) {
    NavHost(navController = navController, startDestination = SPlASH_ROUTE) {

        fun processUrlClick(url: String) {
            navController.navigateToMain()
            if(url.startsWith("quickapp://sheets/detail")){
                val query = SuperUrlUtil.getQueryMap(url)
                query["id"]?.let {
                    navController.navigateToSheetDetail(it.toString())
                }
            } else if (url.startsWith("http://") || url.startsWith("https://")) {
                // Handle normal URL
            } else {
                // Handle other custom schemes
            }
        }

        splashScreen(
            toMain = navController::navigateToMain
        )
        mainScreen(
            appUiState = appUiState,
            toSheetDetail = navController::navigateToSheetDetail,
            toMusicPlayer = navController::navigateToMusicPlayer,
            toLogin = navController::navigateToLoginHome,
            toMy = navController::navigateToMy,
            toUrl = ::processUrlClick,
            toEditSheet = {},
            toLocalMusic = {},
            toScanLocalMusic = {}
        )
        sheetDetail(
            finishPage = {
                if (navController.previousBackStackEntry != null)
                    navController.popBackStack() },
            toMusicPlayer = navController::navigateToMusicPlayer
        )
        musicPlayerScreen(
            finishPage = {
                if (navController.previousBackStackEntry != null)
                    navController.popBackStack() },
        )
        loginHomeScreen(
            finishPage = {
                if (navController.previousBackStackEntry != null)
                    navController.popBackStack() },
            toLogin = navController::navigateToLogin,
            toCodeLogin = {},
            finishAllLoginPage = navController::finishAllLoginPage
        )
        loginScreen(
            finishPage = {
                if (navController.previousBackStackEntry != null)
                    navController.popBackStack() },
            toRegister = navController::navigateToRegister,
            toSetPassword = {},
            finishAllLoginPage = navController::finishAllLoginPage
        )
        registerScreen(
            finishPage = {
                if (navController.previousBackStackEntry != null)
                    navController.popBackStack() },
            finishAllPage = navController::finishAllLoginPage
        )
    }
}