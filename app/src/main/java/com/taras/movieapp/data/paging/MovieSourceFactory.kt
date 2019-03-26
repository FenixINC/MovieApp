package com.taras.movieapp.data.paging

import androidx.paging.DataSource
import com.taras.movieapp.data.model.Movie

class MovieSourceFactory : DataSource.Factory<Int, Movie>() {

    private val movieDataSource = MovieDataSource()

    override fun create(): DataSource<Int, Movie> = movieDataSource

//    private var mMutableLiveDataMovieList = MutableLiveData<MovieDataSource>()
//
//    override fun create(): DataSource<Long, Movie> {
//        val movieDataSource = MovieDataSource()
//        mMutableLiveDataMovieList.postValue(movieDataSource)
//        return movieDataSource
//    }
}