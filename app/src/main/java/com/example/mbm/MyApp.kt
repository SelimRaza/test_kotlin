package com.example.mbm

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.newtest.AppDatabase

class MyApp: Application() {


    override fun onCreate() {
        super.onCreate()
        context = this

        //initDb()
    }

    private fun initDb() {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "my-database"
        ).build()
    }

    companion object {
        lateinit var context: Context
    }
}