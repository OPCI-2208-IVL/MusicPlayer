package com.example.myapplication.ui.myapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.example.myapplication.data.repository.UserDataRepository
import com.example.myapplication.model.UserData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Stable
class MyAppUiState(
    coroutineScope: CoroutineScope,
    userDataRepository: UserDataRepository,
) {
    val userData = userDataRepository.userData.stateIn(
        scope = coroutineScope,
        initialValue = UserData(),
        started = SharingStarted.WhileSubscribed(5_000),
    )

    val isLogin: StateFlow<Boolean> = userDataRepository.userData.map {
        it.isLogin()
    }.stateIn(
        scope = coroutineScope,
        initialValue = false,
        started = SharingStarted.WhileSubscribed(5_000),
    )
}

@Composable
fun rememberMyAppUiState(
    userDataRepository: UserDataRepository,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): MyAppUiState {
    return remember(userDataRepository,coroutineScope) {
        MyAppUiState(
            coroutineScope,
            userDataRepository
        )
    }
}