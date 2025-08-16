package com.example.myapplication.model.response

import kotlinx.serialization.Serializable

@Serializable
data class NetworkPageData<T>(
    val list: List<T>? = null,
    val pagination: NetworkPageMeta? = null,
)
