package com.example.myapplication.model

import kotlinx.serialization.Serializable

@Serializable
data class ButtonViewData(
    val icon: String,
    val title: String,
    val url: String? = null,
    val style: Int = 0,
) {
}