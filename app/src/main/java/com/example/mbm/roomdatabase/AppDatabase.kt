package com.example.mbm.roomdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newtest.Image
import com.example.newtest.ImageDao

@Database(entities = [Image::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDao
}