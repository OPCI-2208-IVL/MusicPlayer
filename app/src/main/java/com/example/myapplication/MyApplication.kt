package com.example.myapplication

import android.app.Application
import android.util.Log
import com.example.myapplication.data.repository.UserDataRepository
import com.example.myapplication.datastore.SessionPreferences
import com.example.myapplication.media.MediaServiceConnection
import com.example.myapplication.ui.myapp.MyAppState
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MyApplication: Application() {
    @Inject
    lateinit var userDataRepository: UserDataRepository

    private var isInitAfterLogin: Boolean = false

    private val applicationScope =  CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        instance = this
        Log.d(TAG, "MyApplication onCreate")

        applicationScope.launch {
            userDataRepository.userData
                .map { it.session }
                .distinctUntilChanged()
                .collectLatest {
                    MyAppState.session = it.session
                    MyAppState.userId = it.userId
                }
        }
        isInitAfterLogin = true
    }

    fun logout() {
        isInitAfterLogin = false
        applicationScope.launch {
            userDataRepository.logout()
            }
    }

    fun initAfterLogin(session: SessionPreferences) {
        destroyInstance()
        if (isInitAfterLogin) {
            return
        }

        isInitAfterLogin = true
    }

    private fun destroyInstance() {
        MyAppState.localDatabase= null
        MediaServiceConnection.destroyInstance()
    }

    companion object{
        private const val TAG = "MyApplication"
        lateinit var instance: MyApplication
    }
}