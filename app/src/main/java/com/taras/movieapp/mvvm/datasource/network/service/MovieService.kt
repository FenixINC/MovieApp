package com.taras.movieapp.mvvm.datasource.network.service

import com.taras.movieapp.mvvm.model.BaseResponse
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

//    @GET("/api/vod/search")
//    fun getMovieListAsync(
//        @Query("genre") genre: String,
//        @Query("start_index") startIndex: Int,
//        @Query("max_results") maxResult: String
//    ): Deferred<Response<BaseResponse>>

    @GET("/api/vod/search")
    fun getMovieList(
            @Query("genre") genre: String,
            @Query("start_index") startIndex: Int,
            @Query("max_results") maxResult: String
    ): Call<BaseResponse>

    @GET("/api/vod/search")
            /*suspend */fun getMovieListRequestAsync(
            @Query("genre") genre: String,
            @Query("start_index") startIndex: Int,
            @Query("max_results") maxResult: String
    ): Call<BaseResponse>

    @GET("/api/vod/search")
    fun getMovieListDeferred(
            @Query("genre") genre: String,
            @Query("start_index") startIndex: Int,
            @Query("max_results") maxResult: String
    ): Deferred<Response<BaseResponse>>
}