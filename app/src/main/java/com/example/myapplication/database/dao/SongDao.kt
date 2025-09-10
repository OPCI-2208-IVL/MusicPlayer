package com.example.myapplication.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.database.model.SongEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SongDao {
    @Query("SELECT * FROM song ORDER BY track_number ASC")
    suspend fun getAllAsync(): List<SongEntity>

    @Query("SELECT * FROM song WHERE play_list = true ORDER BY track_number ASC")
    suspend fun getAllPlayListAsync(): List<SongEntity>

    @Query("SELECT * FROM song ORDER BY track_number ASC")
    fun getAll(): Flow<List<SongEntity>>

    @Query("SELECT * FROM song WHERE play_list = true ORDER BY track_number ASC")
    fun getAllPlayList(): Flow<List<SongEntity>>

    @Query("SELECT * FROM song WHERE source = '10' ORDER BY track_number ASC")
    suspend fun getAllSourceLocal(): List<SongEntity>

    @Query("SELECT * FROM song WHERE title = :title")
    fun findByTitle(title: String): SongEntity

    @Query("SELECT * FROM song WHERE title = :title")
    fun findByTitleFlow(title: String): Flow<SongEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg data: SongEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(entities: List<SongEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<SongEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: SongEntity)

    @Update
    fun update(data: SongEntity)

    @Update
    fun updateAll(vararg data: SongEntity)

    @Query("UPDATE song SET play_list = false")
    suspend fun clearAllPlayList()

    @Query("UPDATE song SET play_list = true WHERE source = '10'")
    suspend fun updateAllLocalMusicPlayList()

    @Delete
    suspend fun delete(data: SongEntity)

    @Query("DELETE FROM song")
    suspend fun deleteAll()

    @Query("DELETE FROM song WHERE source = '10'")
    suspend fun deleteAllBySourceLocal()
}