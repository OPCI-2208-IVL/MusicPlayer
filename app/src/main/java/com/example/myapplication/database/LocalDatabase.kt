package com.example.myapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.database.dao.SongDao
import com.example.myapplication.database.model.SongEntity

@Database(
    entities = [SongEntity::class],
    version = 1,
    exportSchema = true
)
abstract class LocalDatabase:RoomDatabase() {
    abstract fun songDao(): SongDao
}