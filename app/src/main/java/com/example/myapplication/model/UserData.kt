package com.example.myapplication.model

import com.example.myapplication.datastore.GlobalLyricStylePreferences
import com.example.myapplication.datastore.SessionPreferences
import com.example.myapplication.datastore.UserPreferences

data class UserData(
    val notShowGuide: Boolean = false,
    val session: SessionPreferences = SessionPreferences.newBuilder().build(),

    val user: UserPreferences = UserPreferences.newBuilder().build(),

    val notShowTermsServiceAgreement: Boolean = false,

    val useDynamicColor: Boolean = false,

    val playRepeatMode: PlayRepeatMode = PlayRepeatMode.REPEAT_LIST,

    val playMusicId: String = "",

    val playProgress: Long = 0,

    val playDuration: Long = 0,

    val globalLyricStyle: GlobalLyricStylePreferences = GlobalLyricStylePreferences.newBuilder().build(),
) {
    fun isLogin():Boolean {
        return false
    }
}