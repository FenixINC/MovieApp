package com.taras.movieapp.data.service

import com.taras.movieapp.data.model.BaseResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("/api/vod/search")
    fun getMovieList(
        @Query("genre") genre: String,
        @Query("start_index") startIndex: Int,
        @Query("max_results") maxResult: String
    ): Deferred<Response<BaseResponse>>
}