package com.example.myapplication

import android.app.Application
import android.util.Log

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        Log.d(TAG, "MyApplication onCreate")
    }

    companion object{
        private const val TAG = "MyApplication"
        lateinit var instance: MyApplication
    }
}