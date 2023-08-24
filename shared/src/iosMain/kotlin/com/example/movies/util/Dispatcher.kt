package com.example.movies.util

import kotlinx.coroutines.Dispatchers

internal class IosDispatcher : Dispatcher {
    override val io: kotlinx.coroutines.CoroutineDispatcher
        get() = Dispatchers.Default
}

internal actual fun provideDispatcher(): Dispatcher = IosDispatcher()