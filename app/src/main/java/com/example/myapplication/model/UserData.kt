package com.example.myapplication.model

data class UserData(
    val notShowGuide: Boolean = false,
//    val session: SessionPreferences = SessionPreferences.newBuilder().build(),

//    val user: UserPreferences = UserPreferences.newBuilder().build(),

    val notShowTermsServiceAgreement: Boolean = false,

    val useDynamicColor: Boolean = false,

    val playRepeatMode: PlaybackMode = PlaybackMode.REPEAT_LIST,

    val playMusicId: String = "",

    val playProgress: Long = 0,

    val playDuration: Long = 0,

//    val globalLyricStyle: GlobalLyricStylePreferences = GlobalLyricStylePreferences.newBuilder().build(),
) {
    fun isLogin():Boolean {
        return false
    }
}