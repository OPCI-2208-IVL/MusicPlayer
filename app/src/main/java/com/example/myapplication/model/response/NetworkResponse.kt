package com.example.myapplication.model.response

import kotlinx.serialization.Serializable

@Serializable
data class NetworkResponse<T>(
    val data: T? = null,
    val status: Int = 0, //O:success;else:error
    val message: String? = null, //error message
) {
    val isSucceeded: Boolean get() = status == 0
}