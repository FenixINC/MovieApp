package com.taras.movieapp.mvvm.viewmodel

import androidx.lifecycle.LiveData
import com.taras.movieapp.mvvm.model.Movie
import com.taras.movieapp.mvvm.repository.MovieRepositoryImpl

class MovieViewModel : BaseViewModel() {

    private var mMovieGenre: String = ""
    private val mMovieRepository = MovieRepositoryImpl()

    fun getMovieListNetwork(movieGenre: String): LiveData<List<Movie>> {
        mMovieGenre = movieGenre
        return mMovieListNetwork
    }

    fun getMovieListDatabase(movieGenre: String): LiveData<List<Movie>> {
        mMovieGenre = movieGenre
        return mMovieListDatabase
    }

    private val mMovieListNetwork: LiveData<List<Movie>>
        get() = mMovieRepository.getMovieListNetwork(mMovieGenre)

    private val mMovieListDatabase: LiveData<List<Movie>>
        get() = mMovieRepository.getMovieListDatabase(mMovieGenre)

//    fun getMovieLivePagedList(movieGenre: String): LiveData<PagedList<Movie>> {
//        mMovieGenre = movieGenre
//        return getMovieLivePagedList
//    }
//
//    private val getMovieLivePagedList: LiveData<PagedList<Movie>>
//        get() = mMovieRepository.getMoviesLiveData(mMovieGenre)
}