package com.taras.movieapp.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.taras.movieapp.data.model.Movie
import com.taras.movieapp.data.repository.MovieRepository

class MovieViewModel : BaseViewModel() {

    private var mMovieGenre: String = ""
    //    private var mNetworkState = NetworkState.LOADED
    private var mMovieRepository: MovieRepository = MovieRepository(scope)

//    fun getNetworkState(): NetworkState {
//
//    }
//
//    private val mNetworkState: NetworkState
//    get() =

    fun getMovieLivePagedList(movieGenre: String): LiveData<PagedList<Movie>> {
        mMovieGenre = movieGenre
        return getMovieLivePagedList
    }

    private val getMovieLivePagedList: LiveData<PagedList<Movie>>
        get() = mMovieRepository.getMoviesLiveData(mMovieGenre)
}