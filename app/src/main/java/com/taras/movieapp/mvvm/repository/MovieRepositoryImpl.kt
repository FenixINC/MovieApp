package com.taras.movieapp.mvvm.repository


import androidx.lifecycle.MutableLiveData
import com.taras.movieapp.mvvm.datasource.database.AppDatabase
import com.taras.movieapp.mvvm.datasource.network.ServiceGenerator
import com.taras.movieapp.mvvm.datasource.network.service.MovieService
import com.taras.movieapp.mvvm.model.BaseResponse
import com.taras.movieapp.mvvm.model.Movie
import com.taras.movieapp.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class MovieRepositoryImpl {

    private var mMutableLiveData = MutableLiveData<List<Movie>>()
    private var mList = mutableListOf<BaseResponse>()

    private val mService by lazy {
        ServiceGenerator.createService(MovieService::class.java)
    }

    fun getMovieListNetwork(movieGenre: String): MutableLiveData<List<Movie>> {
        CoroutineScope(Dispatchers.Main).launch {
            val request = mService.getMovieListDeferred(movieGenre, 0, Constants.DEFAULT_PAGE_SIZE.toString())

            withContext(Dispatchers.IO) {
                try {
                    val response = request.await()
                    if (response.isSuccessful) {
                        response.body()?.let {
                            for (movie in it.responseList) {
                                movie.movieGenre = movieGenre
                            }
                            AppDatabase.getInstance().movieDao().deleteByGenre(movieGenre)
                            AppDatabase.getInstance().movieDao().insert(it.responseList)

                            mMutableLiveData.postValue(it.responseList)
                        }
                    }
                } catch (e: Exception) {
                    Timber.e(e)
                }
            }
        }

        return mMutableLiveData
    }

    fun getMovieListDatabase(movieGenre: String): MutableLiveData<List<Movie>> {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                try {
                    when (val movieListLiveData = AppDatabase.getInstance().movieDao().getMoviesByGenreSuspend(movieGenre)) {
                        null -> getMovieListNetwork(movieGenre)
                        else -> mMutableLiveData.postValue(movieListLiveData)
                    }
                } catch (e: Exception) {
                    Timber.e(e)
                }
            }
        }

        return mMutableLiveData
    }
}