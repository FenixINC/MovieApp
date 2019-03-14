package com.taras.movieapp.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.taras.movieapp.data.model.Movie
import com.taras.movieapp.data.repository.MovieRepository

class MovieViewModel : BaseViewModel() {

    private var mMovieGenre: String = ""
    private var mMovieRepository: MovieRepository = MovieRepository(scope)

    private val mLoadMovieByGenre: MutableLiveData<List<Movie>>
        get() = loadMoviesByGenre()

    private val mRefreshMovieByGenre: MutableLiveData<List<Movie>>
        get() = refreshMoviesByGenre()

    private fun loadMoviesByGenre(): MutableLiveData<List<Movie>> {
        return mMovieRepository.doLoadMoviesByGenre(mMovieGenre)
    }

    private fun refreshMoviesByGenre(): MutableLiveData<List<Movie>> {
        return mMovieRepository.doRefreshMoviesByGenre(mMovieGenre)
    }

    fun insert(movie: Movie) {
        mMovieRepository.insert(movie)
    }

    fun insert(movieList: List<Movie>) {
        mMovieRepository.insert(movieList)
    }

    fun deleteMovieList() {
        mMovieRepository.deleteAll()
    }

    fun deleteByGenre(movieGenre: String) {
        mMovieRepository.deleteByGenre(movieGenre)
    }

    fun doLoadMoviesByGenre(movieGenre: String): LiveData<List<Movie>> {
        mMovieGenre = movieGenre
        return mLoadMovieByGenre
    }

    fun doRefreshMoviesByGenre(movieGenre: String): LiveData<List<Movie>> {
        mMovieGenre = movieGenre
        return mRefreshMovieByGenre
    }
}