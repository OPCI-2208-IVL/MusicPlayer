package com.example.myapplication.model

import kotlinx.serialization.Serializable

@Serializable
data class SuggestItem (
    val id: String = "",
    val title: String = ""
)