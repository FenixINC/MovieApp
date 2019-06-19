package com.taras.movieapp.coroutines

import com.taras.movieapp.mvvm.model.Movie

interface Service {

    suspend fun getMovies(): Result<Exception, Movie>
}