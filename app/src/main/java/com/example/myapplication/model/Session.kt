package com.example.myapplication.model

import com.example.myapplication.datastore.SessionPreferences
import kotlinx.serialization.Serializable

@Serializable
data class Session(
    /**
     * 用户Id
     */
    val userId: String,

    /**
     * 登录后的Session
     */
    val session: String,

    /**
     * 聊天token
     */
    val chatToken: String,

    /**
     * 登录后直接返回用户信息，避免再次请求提高效率
     */
    val user: User,
) {
    fun toPreferences(): SessionPreferences? {
        return SessionPreferences.newBuilder()
            .setUserId(userId)
            .setSession(session)
            .setChatToken(chatToken)
            .build()
    }
}