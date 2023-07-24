package com.example.newtest

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ImageDao {
    @Insert
    fun insert(image: Image)

    @Query("SELECT * FROM images")
    fun getAll(): List<Image>

    @Query("SELECT * FROM images WHERE isSync = 0")
    fun getAllUnsyncImages(): List<Image>

    @Update
    fun update(image: Image)

    @Query("UPDATE images SET isSync = 1 WHERE id = :imageId")
    fun updateSyncStatus(imageId: Int)

    @Delete
    suspend fun delete(image: Image)
}