package com.example.movies.android.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_fav")
data class MovieFavEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val poster_path: String,
    val overview: String,
    val release_date: String,
    val user_id: Int
)
