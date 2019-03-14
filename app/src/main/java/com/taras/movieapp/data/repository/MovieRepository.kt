package com.taras.movieapp.data.repository

import androidx.lifecycle.MutableLiveData
import com.taras.movieapp.data.ServiceGenerator
import com.taras.movieapp.data.database.AppDatabase
import com.taras.movieapp.data.model.BaseResponse
import com.taras.movieapp.data.model.Movie
import com.taras.movieapp.data.service.MovieService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.annotations.NotNull
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class MovieRepository(scope: CoroutineScope) {

    private val mScope = scope

    fun insert(movie: Movie) = mScope.launch {
        try {
            withContext(Dispatchers.Default) {
                AppDatabase.getInstance().movieDao().insert(movie)
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    fun insert(movieList: List<Movie>) = mScope.launch {
        try {
            withContext(Dispatchers.Default) {
                AppDatabase.getInstance().movieDao().deleteAll()
                AppDatabase.getInstance().movieDao().insert(movieList)
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    fun deleteAll() = mScope.launch {
        try {
            withContext(Dispatchers.Default) {
                AppDatabase.getInstance().movieDao().deleteAll()
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    fun deleteByGenre(movieGenre: String) = mScope.launch {
        try {
            withContext(Dispatchers.Default) {
                AppDatabase.getInstance().movieDao().deleteByGenre(movieGenre)
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    fun getMovieList(): MutableLiveData<List<Movie>> {
        val movieLiveDataList: MutableLiveData<List<Movie>> = MutableLiveData()
        mScope.launch {
            try {
                withContext(Dispatchers.Default) {
                    val list = AppDatabase.getInstance().movieDao().getMovieList()
                    if (list?.value != null) {
                        movieLiveDataList.postValue(list.value)
                    }
                }
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
        return movieLiveDataList
    }

    fun doLoadMoviesByGenre(movieGenre: String): MutableLiveData<List<Movie>> {
        var isLoadFromServer = false
        val movieLiveDataList: MutableLiveData<List<Movie>> = MutableLiveData()
        mScope.launch {
            try {
                withContext(Dispatchers.Default) {
                    val listMovieByGenre = AppDatabase.getInstance().movieDao().getMoviesByGenre(movieGenre)
                    if (listMovieByGenre?.value != null) {
                        movieLiveDataList.postValue(listMovieByGenre.value)
                    } else {
                        isLoadFromServer = true
                    }
                }
            } catch (e: Exception) {
                Timber.e(e)
            }
            if (isLoadFromServer) {
                try {
                    withContext(Dispatchers.Default) {
                        val movieService = ServiceGenerator.createService(MovieService::class.java)
                        movieService.getMovieList(movieGenre, 0, "")
                            .enqueue(object : Callback<BaseResponse> {
                                override fun onResponse(@NotNull call: Call<BaseResponse>, @NotNull response: Response<BaseResponse>) {
                                    if (response.body() != null) {
                                        val baseResponse = response.body()
                                        baseResponse?.movieGenre = movieGenre
                                        val movieList = response.body()?.responseList!!

                                        for (movie in movieList) {
                                            movie.movieGenre = movieGenre
                                        }

                                        try {
                                            AppDatabase.getInstance().movieDao().deleteByGenre(movieGenre)
                                            AppDatabase.getInstance().movieDao().insert(movieList)
                                        } catch (e: Exception) {
                                            Timber.e(e)
                                        }
                                        movieLiveDataList.postValue(response.body()?.responseList)
                                    }
                                }

                                override fun onFailure(@NotNull call: Call<BaseResponse>, @NotNull t: Throwable) {
                                    Timber.e(t)
                                }
                            })
                    }
                } catch (e: Exception) {
                    Timber.e(e)
                }
            }
        }
        return movieLiveDataList
    }

    fun doRefreshMoviesByGenre(movieGenre: String): MutableLiveData<List<Movie>> {
        val movieLiveDataList: MutableLiveData<List<Movie>> = MutableLiveData()
        mScope.launch {
            try {
                withContext(Dispatchers.Default) {
                    val movieService = ServiceGenerator.createService(MovieService::class.java)
                    movieService.getMovieList(movieGenre, 0, "")
                        .enqueue(object : Callback<BaseResponse> {
                            override fun onResponse(@NotNull call: Call<BaseResponse>, @NotNull response: Response<BaseResponse>) {
                                if (response.body() != null) {
                                    val baseResponse = response.body()
                                    baseResponse?.movieGenre = movieGenre

                                    if (baseResponse?.responseList?.size == 0) {
                                        try {
                                            val list = AppDatabase.getInstance().movieDao().getMoviesByGenre(movieGenre).value
                                            movieLiveDataList.postValue(list)
                                        } catch (e: Exception) {
                                            Timber.e(e)
                                        }
                                    } else {

                                        val movieList = response.body()?.responseList!!

                                        for (movie in movieList) {
                                            movie.movieGenre = movieGenre
                                        }

                                        try {
                                            AppDatabase.getInstance().movieDao().deleteByGenre(movieGenre)
                                            AppDatabase.getInstance().movieDao().insert(movieList)
                                        } catch (e: Exception) {
                                            Timber.e(e)
                                        }
                                        movieLiveDataList.postValue(response.body()?.responseList)
                                    }
                                }
                            }

                            override fun onFailure(@NotNull call: Call<BaseResponse>, @NotNull t: Throwable) {
                                Timber.e(t)
                            }
                        })
                }
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
        return movieLiveDataList
    }
}