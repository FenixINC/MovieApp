package com.taras.movieapp.coroutines

import com.taras.movieapp.mvvm.data.entities.Movie

interface Service {

    suspend fun getMovies(): Result<Exception, Movie>
}