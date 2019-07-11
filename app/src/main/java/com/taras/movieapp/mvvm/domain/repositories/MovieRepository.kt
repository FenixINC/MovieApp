package com.taras.movieapp.mvvm.domain.repositories

import androidx.lifecycle.MutableLiveData
import com.taras.movieapp.mvvm.data.entities.Movie

interface MovieRepository {

    fun getMovieList(movieGenre: String): MutableLiveData<List<Movie>>
}