package com.example.myapplication.exception

import com.example.myapplication.model.response.NetworkResponse

class CommonException(
    val networkResponse: NetworkResponse<*>? = null,
    val throwable: Throwable? = null,
    var tipString: String? = null,
    var tipIcon: Int? = null
) : RuntimeException()
