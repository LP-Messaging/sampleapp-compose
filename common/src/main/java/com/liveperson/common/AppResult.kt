package com.liveperson.common

import android.util.Log

sealed interface AppResult<out Success, out Error>

data class Success<T>(val data: T) : AppResult<T, Nothing>

data class Failure<T>(val data: T) : AppResult<Nothing, T>

inline fun<T, T1, E> AppResult<T, E>.map(block: (T) -> T1): AppResult<T1, E> {
    return when (this) {
        is Success<T> -> Success(block(data))
        is Failure<E> -> this
    }
}

inline fun <T, E> produceResult(mapper: (Throwable) -> E, block: () -> T): AppResult<T, E> {
    return try {
        Success(block())
    } catch (throwable: Throwable) {
        Log.e("TAG", "RESULT", throwable)
        Failure(mapper(throwable))
    }
}

inline fun <T> runSafely(
    mapper: (Throwable) -> String = { it.toString() },
    block: () -> T
): AppResult<T, String> {
    return produceResult(mapper, block)
}