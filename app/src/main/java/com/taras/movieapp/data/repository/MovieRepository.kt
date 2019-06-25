package com.taras.movieapp.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.taras.movieapp.utils.Constants
import com.taras.movieapp.mvvm.data.entities.Movie
import com.taras.movieapp.data.paging.MovieBoundaryCallback
import com.taras.movieapp.data.paging.MovieSourceFactory
import kotlinx.coroutines.CoroutineScope

class MovieRepository(scope: CoroutineScope) {

    private val mScope = scope

    private var mMovieGenre = ""
    private lateinit var moviesLiveData: LiveData<PagedList<Movie>>
    private lateinit var mMovieSourceFactory: MovieSourceFactory
    private lateinit var mMovieBoundaryCallback: MovieBoundaryCallback

    fun getMoviesLiveData(movieGenre: String): LiveData<PagedList<Movie>> {
        mMovieGenre = movieGenre
        mMovieSourceFactory = MovieSourceFactory(mMovieGenre, mScope)
        mMovieBoundaryCallback = MovieBoundaryCallback(mMovieGenre, mScope)
//        val executor = Executors.newFixedThreadPool(1)

        val pagedListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(Constants.DEFAULT_PAGE_SIZE) // get 20 items in first load
                .setPageSize(Constants.DEFAULT_PAGE_SIZE)            // get 20 items from DataSource
//                .setPrefetchDistance(Constants.DEFAULT_PAGE_SIZE)    // get 20 items from next portion; default == PageSize == setPageSize(15)
                .build()

        val moviesLiveData: LiveData<PagedList<Movie>> = LivePagedListBuilder<Int, Movie>(mMovieSourceFactory, pagedListConfig)
                .setBoundaryCallback(mMovieBoundaryCallback)
//                .setFetchExecutor(executor)
                .build()
        return moviesLiveData
    }

    fun getNetworkState() {

    }

//    fun insert(movie: Movie) = mScope.launch {
//        try {
//            withContext(Dispatchers.Default) {
//                AppDatabase.getInstance().movieDao().insert(movie)
//            }
//        } catch (e: Exception) {
//            Timber.e(e)
//        }
//    }
//
//    fun insert(movieList: List<Movie>) = mScope.launch {
//        try {
//            withContext(Dispatchers.Default) {
//                AppDatabase.getInstance().movieDao().deleteAll()
//                AppDatabase.getInstance().movieDao().insert(movieList)
//            }
//        } catch (e: Exception) {
//            Timber.e(e)
//        }
//    }
//
//    fun deleteAll() = mScope.launch {
//        try {
//            withContext(Dispatchers.Default) {
//                AppDatabase.getInstance().movieDao().deleteAll()
//            }
//        } catch (e: Exception) {
//            Timber.e(e)
//        }
//    }
//
//    fun deleteByGenre(movieGenre: String) = mScope.launch {
//        try {
//            withContext(Dispatchers.Default) {
//                AppDatabase.getInstance().movieDao().deleteByGenre(movieGenre)
//            }
//        } catch (e: Exception) {
//            Timber.e(e)
//        }
//    }
//
//    fun getMovieList(): MutableLiveData<List<Movie>> {
//        val movieLiveDataList: MutableLiveData<List<Movie>> = MutableLiveData()
//        mScope.launch {
//            try {
//                withContext(Dispatchers.Default) {
//                    val list = AppDatabase.getInstance().movieDao().getMovieList()
//                    if (list?.value != null) {
//                        movieLiveDataList.postValue(list.value)
//                    }
//                }
//            } catch (e: Exception) {
//                Timber.e(e)
//            }
//        }
//        return movieLiveDataList
//    }
//
//    fun doLoadMoviesByGenre(movieGenre: String): MutableLiveData<List<Movie>> {
//        var isLoadFromServer = false
//        val movieLiveDataList: MutableLiveData<List<Movie>> = MutableLiveData()
//        mScope.launch {
//            try {
//                withContext(Dispatchers.Default) {
//                    val listMovieByGenre = AppDatabase.getInstance().movieDao().getMoviesByGenre(movieGenre)
//                    if (listMovieByGenre?.value != null) {
//                        movieLiveDataList.postValue(listMovieByGenre.value)
//                    } else {
//                        isLoadFromServer = true
//                    }
//                }
//            } catch (e: Exception) {
//                Timber.e(e)
//            }
//            if (isLoadFromServer) {
//                try {
//                    withContext(Dispatchers.Default) {
//                        val movieService = ServiceGenerator.createService(MovieService::class.java)
//                        movieService.getMovieList(movieGenre, 0, "")
//                            .enqueue(object : Callback<BaseResponse> {
//                                override fun onResponse(@NotNull call: Call<BaseResponse>, @NotNull response: Response<BaseResponse>) {
//                                    if (response.body() != null) {
//                                        val baseResponse = response.body()
//                                        baseResponse?.movieGenre = movieGenre
//                                        val movieList = response.body()?.responseList!!
//
//                                        for (movie in movieList) {
//                                            movie.movieGenre = movieGenre
//                                        }
//
//                                        try {
//                                            AppDatabase.getInstance().movieDao().deleteByGenre(movieGenre)
//                                            AppDatabase.getInstance().movieDao().insert(movieList)
//                                        } catch (e: Exception) {
//                                            Timber.e(e)
//                                        }
//                                        movieLiveDataList.postValue(response.body()?.responseList)
//                                    }
//                                }
//
//                                override fun onFailure(@NotNull call: Call<BaseResponse>, @NotNull t: Throwable) {
//                                    Timber.e(t)
//                                }
//                            })
//                    }
//                } catch (e: Exception) {
//                    Timber.e(e)
//                }
//            }
//        }
//        return movieLiveDataList
//    }
//
//    fun doRefreshMoviesByGenre(movieGenre: String): MutableLiveData<List<Movie>> {
//        val movieLiveDataList: MutableLiveData<List<Movie>> = MutableLiveData()
//        mScope.launch {
//            try {
//                withContext(Dispatchers.Default) {
//                    val movieService = ServiceGenerator.createService(MovieService::class.java)
//                    movieService.getMovieList(movieGenre, 0, "")
//                        .enqueue(object : Callback<BaseResponse> {
//                            override fun onResponse(@NotNull call: Call<BaseResponse>, @NotNull response: Response<BaseResponse>) {
//                                if (response.body() != null) {
//                                    val baseResponse = response.body()
//                                    baseResponse?.movieGenre = movieGenre
//
//                                    if (baseResponse?.responseList?.size == 0) {
//                                        try {
//                                            val list = AppDatabase.getInstance().movieDao().getMoviesByGenre(movieGenre).value
//                                            movieLiveDataList.postValue(list)
//                                        } catch (e: Exception) {
//                                            Timber.e(e)
//                                        }
//                                    } else {
//
//                                        val movieList = response.body()?.responseList!!
//
//                                        for (movie in movieList) {
//                                            movie.movieGenre = movieGenre
//                                        }
//
//                                        try {
//                                            AppDatabase.getInstance().movieDao().deleteByGenre(movieGenre)
//                                            AppDatabase.getInstance().movieDao().insert(movieList)
//                                        } catch (e: Exception) {
//                                            Timber.e(e)
//                                        }
//                                        movieLiveDataList.postValue(response.body()?.responseList)
//                                    }
//                                }
//                            }
//
//                            override fun onFailure(@NotNull call: Call<BaseResponse>, @NotNull t: Throwable) {
//                                Timber.e(t)
//                            }
//                        })
//                }
//            } catch (e: Exception) {
//                Timber.e(e)
//            }
//        }
//        return movieLiveDataList
//    }
}