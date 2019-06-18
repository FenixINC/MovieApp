package com.taras.movieapp.coroutines

sealed class Result2<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result2<T>()
    data class Error(val exception: Exception) : Result2<Nothing>()
}