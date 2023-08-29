package com.example.movies.android.data

import android.content.Context

class SharedPreference(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("Movie", Context.MODE_PRIVATE)

    fun save(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }
    fun get(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    fun remove(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }
}