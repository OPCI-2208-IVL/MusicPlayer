package com.example.myapplication.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_history")
data class SearchHistoryEntity (
    @PrimaryKey val title: String,
    val create: Long,
    @ColumnInfo val app: Int = 0
)