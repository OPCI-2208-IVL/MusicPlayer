package com.example.myapplication.model

import kotlinx.serialization.Serializable

@Serializable
data class Ad(
    val title: String? = null,
    val icon: String,
    val uri: String? = null,
    val style: Int = 0,
)