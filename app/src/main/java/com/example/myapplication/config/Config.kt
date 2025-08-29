package com.example.myapplication.config

import com.example.myapplication.BuildConfig

object Config {
    const val ENDPOINT = "http://quick-server-sp.ixuea.com/"
    var RESOURCE_ENDPOINT =""
    var RESOURCE_ENDPOINT2 = "https://rs.ixuea.com/music/%s"

    val DEBUG: Boolean = BuildConfig.DEBUG
}