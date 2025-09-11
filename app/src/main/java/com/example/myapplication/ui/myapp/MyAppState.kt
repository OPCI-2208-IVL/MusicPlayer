package com.example.myapplication.ui.myapp

import com.example.myapplication.database.LocalDatabase

object MyAppState {
    var session: String = ""
    var userId: String = ""
    var localDatabase: LocalDatabase? = null
}