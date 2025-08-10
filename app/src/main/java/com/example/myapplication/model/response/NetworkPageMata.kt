package com.example.myapplication.model.response

data class NetworkPageMata (
    val total: Int? = null,//total messages
    val pages: Int? = null,//total pages
    val size: Int? = null,//number of messages on one page
    val page: Int? = null,//current page
    val next: Int? = null,//next page
)