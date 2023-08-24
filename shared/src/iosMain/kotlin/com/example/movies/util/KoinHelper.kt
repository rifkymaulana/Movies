package com.example.movies.util

import com.example.movies.di.getSharedModules
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin() {
        modules(getSharedModules())
    }
}
