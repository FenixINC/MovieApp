package com.taras.movieapp.data.paging

import androidx.paging.DataSource
import com.taras.movieapp.mvvm.model.Movie
import kotlinx.coroutines.CoroutineScope

class MovieSourceFactory(movieGenre: String, scope: CoroutineScope) : DataSource.Factory<Int, Movie>() {

    private val mMovieDataSource = MovieDataSource(movieGenre, scope)

    override fun create(): DataSource<Int, Movie> = mMovieDataSource

//    private var mMutableLiveDataMovie = MutableLiveData<MovieDataSource>()
//
//    override fun create(): DataSource<Int, Movie> {
//        val movieDataSource = mMovieDataSource
//        mMutableLiveDataMovie.postValue(movieDataSource)
//        return movieDataSource
//    }
//
//    fun getLiveDataMovieDataSource(): LiveData<MovieDataSource> {
//        return mMutableLiveDataMovie
//    }
}