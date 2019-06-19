package com.taras.movieapp.mvvm.repository

import com.taras.movieapp.mvvm.model.BaseResponse

interface MovieRepository {

    suspend fun getMovieList(movieGenre: String)
}