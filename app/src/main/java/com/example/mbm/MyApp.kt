package com.example.mbm

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.mbm.common.CustomDebugTree
import com.example.newtest.AppDatabase
import timber.log.Timber

class MyApp: Application() {


    override fun onCreate() {
        super.onCreate()
        context = this

        //initDb()
        initLogger()
    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(CustomDebugTree())
        }

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