package com.taras.movieapp.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.taras.movieapp.data.model.Movie
import com.taras.movieapp.data.paging.MovieBoundaryCallback
import com.taras.movieapp.data.paging.MovieSourceFactory
import com.taras.movieapp.data.repository.MovieRepository
import java.util.concurrent.Executors

class MovieViewModel : BaseViewModel() {

    private var mMovieGenre: String = ""
    private var mMovieRepository: MovieRepository = MovieRepository(scope)

    private var mMovieSourceFactory = MovieSourceFactory()
    private var moviesLiveData: LiveData<PagedList<Movie>>? = null

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

    //////////////////////

    fun getPagedListLiveData(): LiveData<PagedList<Movie>> {
        val movieBoundaryCallback = MovieBoundaryCallback()
        val executor = Executors.newFixedThreadPool(13)

        val pagedListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(10)
                .setPageSize(10)
                .build()

        moviesLiveData = LivePagedListBuilder<Int, Movie>(mMovieSourceFactory, pagedListConfig)
//            .setBoundaryCallback(movieBoundaryCallback)
                .setFetchExecutor(executor)
                .build()

        return moviesLiveData!!
    }

    init {
        val movieBoundaryCallback = MovieBoundaryCallback()
        val executor = Executors.newFixedThreadPool(13)

        val pagedListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(10)
                .setPageSize(10)
                .build()

        moviesLiveData = LivePagedListBuilder<Int, Movie>(mMovieSourceFactory, pagedListConfig)
//            .setBoundaryCallback(movieBoundaryCallback)
                .setFetchExecutor(executor)
                .build()
    }

    fun getMoviesLiveData(): LiveData<PagedList<Movie>> {
        return moviesLiveData!!
    }
}