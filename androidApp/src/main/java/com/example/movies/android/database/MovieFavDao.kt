package com.example.movies.android.database

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface MovieFavDao {
    @Insert
    suspend fun insertMovieFav(movieFav: MovieFavEntity)
}