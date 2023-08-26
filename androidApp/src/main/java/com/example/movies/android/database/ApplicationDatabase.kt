package com.example.movies.android.database

import android.app.Application
import androidx.room.Room

class ApplicationDatabase : Application() {
    val database by lazy {
        Room.databaseBuilder(this, AppDatabase::class.java, "AppDatabase")
            .build()
    }
}