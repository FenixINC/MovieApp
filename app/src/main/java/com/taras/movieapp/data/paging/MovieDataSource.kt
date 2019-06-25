package com.taras.movieapp.data.paging

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import com.taras.movieapp.utils.Constants
import com.taras.movieapp.mvvm.data.network.ServiceGenerator
import com.taras.movieapp.mvvm.data.database.AppDatabase
import com.taras.movieapp.mvvm.data.entities.BaseResponse
import com.taras.movieapp.mvvm.data.entities.Movie
import com.taras.movieapp.mvvm.data.entities.NetworkState
import com.taras.movieapp.mvvm.data.network.services.MovieService
import kotlinx.coroutines.*
import timber.log.Timber

class MovieDataSource(movieGenre: String, scope: CoroutineScope) : PageKeyedDataSource<Int, Movie>() {

    private lateinit var mBaseResponse: BaseResponse

    private val mMovieGenre = movieGenre
    private val mScope = scope
    private var mNetworkState = MutableLiveData<NetworkState>()
    private var mPostList = MutableLiveData<List<Movie>>()

    private var mPageIndex = 0

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        mScope.launch {
            withContext(Dispatchers.Default) {
                try {
                    val list = Transformations.switchMap(mPostList) {
                        LivePagedListBuilder(AppDatabase.getInstance().movieDao().getPagedMoviesByGenre(mMovieGenre), Constants.DEFAULT_PAGE_SIZE).build()
                    }
                    list.value?.let {
                        mPageIndex += 20
                        callback.onResult(it, null, mPageIndex)
                    }

                    if (list?.value == null) {
                        val request = ServiceGenerator.createService(MovieService::class.java)
                                .getMovieList(mMovieGenre, mPageIndex, Constants.DEFAULT_PAGE_SIZE.toString())
                                .execute()

                        if (request.isSuccessful) {
                            Timber.d("Movie request is successful")
                            request.body()?.let {
                                mBaseResponse = it
                                mPageIndex += 20
//                                AppDatabase.getInstance().movieDao().deleteByGenre(mMovieGenre)
                                for (movie in it.responseList) {
                                    movie.movieGenre = mMovieGenre
                                }
                                AppDatabase.getInstance().movieDao().insert(it.responseList)
                                callback.onResult(it.responseList, null, mPageIndex)
                            }
                        }
                    }
                } catch (e: Exception) {
                    Timber.e(e)
                }
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        mScope.launch {
            withContext(Dispatchers.Default) {
                try {
//                    delay(5000)
                    val request = ServiceGenerator.createService(MovieService::class.java)
                            .getMovieList(mMovieGenre, mPageIndex, Constants.DEFAULT_PAGE_SIZE.toString())
                            .execute()

                    if (request.isSuccessful) {
                        Timber.d("Movie request is successful")
                        request.body()?.let {
                            mBaseResponse = it
                            mPageIndex += 20
                            for (movie in it.responseList) {
                                movie.movieGenre = mMovieGenre
                            }
                            AppDatabase.getInstance().movieDao().insert(it.responseList)
                            callback.onResult(it.responseList, mPageIndex)
                        }
                    }

//                    val list = AppDatabase.getInstance().movieDao().getMovieList()
//                    mPostList.postValue(list.value)
                } catch (e: Exception) {
                    Timber.e(e)
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

    }
}