package com.example.myapplication.model.response

import kotlinx.serialization.Serializable

@Serializable
data class NetworkPageMeta (
    val total: Int? = null,//total messages
    val pages: Int? = null,//total pages
    val size: Int? = null,//number of messages on one page
    val page: Int? = null,//current page
    val next: Int? = null,//next page
)