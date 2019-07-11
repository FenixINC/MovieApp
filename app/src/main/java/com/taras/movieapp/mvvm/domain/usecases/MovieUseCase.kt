package com.taras.movieapp.mvvm.domain.usecases

import androidx.lifecycle.MutableLiveData
import com.taras.movieapp.mvvm.data.entities.Movie

interface MovieUseCase {
    fun getMovieList(movieGenre: String): MutableLiveData<List<Movie>>
}