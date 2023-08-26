package com.example.movies.android

import android.app.Application
import androidx.room.Room
import com.example.movies.android.database.AppDatabase
import com.example.movies.android.di.appModule
import com.example.movies.di.getSharedModules
import org.koin.core.context.startKoin

class Movie : Application() {
    val database by lazy {
        Room.databaseBuilder(this, AppDatabase::class.java, "AppDatabase")
            .build()
    }
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule + getSharedModules())
        }
    }
}