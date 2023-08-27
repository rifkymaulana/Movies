package com.example.movies.android.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieFavDao {
    @Insert
    suspend fun insertMovieFav(movieFav: MovieFavEntity)

    @Query("SELECT * FROM movies_fav WHERE user_id = :userId")
    suspend fun getAllByUserId(userId: Int): List<MovieFavEntity>?
}