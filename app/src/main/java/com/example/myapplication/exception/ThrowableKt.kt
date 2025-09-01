package com.example.myapplication.exception

import com.example.myapplication.MyApplication
import com.example.myapplication.util.NetworkUtil

fun Throwable.localException(): CommonException{
    if (this is CommonException) {
        this.tipString = this.networkResponse!!.message ?: "unknown_error"
        return this
    }
    else{
        if(NetworkUtil.isNetworkAvailable(MyApplication.instance))
            return CommonException(
                throwable = this,
                tipString = this.localizedMessage
            )
        else
            return CommonException(
                throwable = this,
                tipString = "network not connect"
            )
    }
}