package com.example.newtest

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Person::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
}