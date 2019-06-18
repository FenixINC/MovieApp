package com.taras.movieapp.coroutines

import com.taras.movieapp.data.model.Movie

interface Service {

    suspend fun getMovies(): Result<Exception, Movie>
}