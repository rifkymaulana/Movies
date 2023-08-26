package com.example.movies.android.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

const val AUTHORIZATION_HEADER: String =
    "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzMmNjOTA3YTViMTI5MTM3OTkyNDBhZjIzOGY1ODBlYSIsInN1YiI6IjY0ZTVmMzg5ZTg5NGE2MDEzYmFmMzIyMyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ._koYRpzgBpXcG1RkYnwKGWjMC3f7LEyY10wq_FRYAGA"

data class Movies(
    val results: List<Movie>?
)

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String,
    val release_date: String,
)

interface MovieApiService {
    @GET("movie/now_playing?language=en&page=1")
    suspend fun getNowPlaying(
        @Header("Authorization") authorization: String
    ): Movies

    @GET("movie/popular?language=en&page=1")
    suspend fun getPopular(
        @Header("Authorization") authorization: String
    ): Movies

    @GET("movie/top_rated?language=en&page=1")
    suspend fun getTopRated(
        @Header("Authorization") authorization: String
    ): Movies

    @GET("movie/upcoming?language=en&page=1")
    suspend fun getUpcoming(
        @Header("Authorization") authorization: String
    ): Movies
}

object RetrofitInstance {
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    val retrofit: Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build()).build()
}

val movieApiService: MovieApiService = RetrofitInstance.retrofit.create(MovieApiService::class.java)


