package com.taras.movieapp.coroutines

import retrofit2.Response
import timber.log.Timber

open class BaseRepository {

    suspend fun <T : Any> apiCall(call: suspend () -> Response<T>, errorMessage: String): T? {
        val result: Result2<T> = apiResult(call, errorMessage)
        var data: T? = null

        when (result) {
            is Result2.Success ->
                data = result.data
            is Result2.Error -> {
                Timber.e("${result.exception}")
            }
        }

        return data
    }

    private suspend fun <T : Any> apiResult(call: suspend () -> Response<T>, errorMessage: String): Result2<T> {
        val response = call.invoke()
        return when {
            response.isSuccessful -> {
                if (response.body() != null) {
                    Result2.Success(response.body()!!)
                } else Result2.Error(Exception("NULL response body!"))
            }
            else -> Result2.Error(Exception("Error Occurred during getting api result, Custom ERROR - $errorMessage"))
        }
    }
}