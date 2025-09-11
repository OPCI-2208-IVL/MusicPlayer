package com.example.myapplication.data.repository

import com.example.myapplication.datasource.UserPreferencesDatasource
import com.example.myapplication.datastore.PlaybackModePreferences
import com.example.myapplication.datastore.SessionPreferences
import com.example.myapplication.datastore.UserPreferences
import com.example.myapplication.model.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalUserDataRepository @Inject constructor(
    private val userPreferencesDatasource: UserPreferencesDatasource,
): UserDataRepository {
    override val userData: Flow<UserData> =
        userPreferencesDatasource.userData

    override suspend fun setNotShowGuide(notShowGuide: Boolean) =
        userPreferencesDatasource.setNotShowGuide(notShowGuide)

    override suspend fun setNotShowTermsServiceAgreement(notShow: Boolean) =
        userPreferencesDatasource.setNotShowTermsServiceAgreement(notShow)

    override suspend fun setSession(data: SessionPreferences?) {
        userPreferencesDatasource.setSession(data!!)
    }

    override suspend fun setUser(data: UserPreferences?) {
        userPreferencesDatasource.setUser(data!!)
    }

    override suspend fun logout() {
        userPreferencesDatasource.logout()
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        userPreferencesDatasource.setDynamicColorPreference(useDynamicColor)
    }

    override suspend fun saveRecentSong(
        id: String,
        currentPosition: Long,
        duration: Long,
    ) {
        userPreferencesDatasource.saveRecentSong(id, currentPosition, duration)
    }

    override suspend fun setRepeatModel(
        data: PlaybackModePreferences
    ) {
        userPreferencesDatasource.setRepeatModel(data)
    }

    override suspend fun setGlobalLyricTextColorIndex(data: Int) {
        userPreferencesDatasource.setGlobalLyricTextColorIndex(data)
    }

    override suspend fun setGlobalLyricTextSize(data: Int) {
        userPreferencesDatasource.setGlobalLyricTextSize(data)
    }

    override suspend fun setGlobalLyricViewPositionY(y: Int) {
        userPreferencesDatasource.setGlobalLyricViewPositionY(y)
    }

    override suspend fun setShowGlobalLyric(data: Boolean) {
        userPreferencesDatasource.setShowGlobalLyric(data)
    }

    override suspend fun setGlobalLyricLock(data: Boolean) {
        userPreferencesDatasource.setGlobalLyricLock(data)
    }
}