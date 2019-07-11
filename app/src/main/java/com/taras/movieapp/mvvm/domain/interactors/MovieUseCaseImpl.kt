package com.taras.movieapp.mvvm.domain.interactors

import androidx.lifecycle.MutableLiveData
import com.taras.movieapp.mvvm.data.entities.Movie
import com.taras.movieapp.mvvm.domain.repositories.MovieRepository
import com.taras.movieapp.mvvm.domain.usecases.MovieUseCase

class MovieUseCaseImpl(private val movieRepository: MovieRepository, private val movieGenre: String) : MovieUseCase {

    sealed class Result {
        object Loading : Result()
        data class Success(val movies: List<Movie>) : Result()
        data class Failure(val throwable: Throwable) : Result()
    }

    override fun getMovieList(movieGenre: String): MutableLiveData<List<Movie>> {
        return movieRepository.getMovieList(movieGenre)
    }
}