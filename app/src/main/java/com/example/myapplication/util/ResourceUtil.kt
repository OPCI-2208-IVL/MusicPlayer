package com.example.myapplication.util

import com.example.myapplication.config.Config

object ResourceUtil {
    fun rel2abs(uri: String): String {
        return if (uri.startsWith("content://")) {
            uri
        } else if (uri.startsWith("http")) {
            uri
        } else if (uri.startsWith("files"))
            uri
        else if (uri.startsWith("r/")) {
            //上传到服务端的资源
            "${Config.ENDPOINT}${uri}"
        } else
            String.format(Config.RESOURCE_ENDPOINT, uri)
    }


    fun abs2rel(uri: String): String {
        return if (uri.startsWith("http")) {
            return uri
        } else if (uri.startsWith("files"))
            uri
        else if (uri.startsWith("r/")) {
            //上传到服务端的资源
            "${Config.ENDPOINT}${uri}"
        } else
            String.format(Config.RESOURCE_ENDPOINT2, uri)
    }
}
