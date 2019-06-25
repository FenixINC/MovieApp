package com.taras.movieapp.mvvm.domain.repositories

interface MovieRepository {

    suspend fun getMovieList(movieGenre: String)
}