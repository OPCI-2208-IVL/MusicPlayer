package com.example.myapplication

import com.example.myapplication.database.LocalDatabase

object MyAppState {
    var session: String = ""
    var userId: String = ""
    var localDatabase: LocalDatabase? = null
}