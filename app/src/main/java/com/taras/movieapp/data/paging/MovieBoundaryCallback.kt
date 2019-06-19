package com.taras.movieapp.data.paging

import androidx.lifecycle.MediatorLiveData
import androidx.paging.PagedList
import com.taras.movieapp.utils.Constants
import com.taras.movieapp.mvvm.datasource.network.ServiceGenerator
import com.taras.movieapp.mvvm.datasource.database.AppDatabase
import com.taras.movieapp.mvvm.model.Movie
import com.taras.movieapp.mvvm.datasource.network.service.MovieService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class MovieBoundaryCallback(movieGenre: String, scope: CoroutineScope) : PagedList.BoundaryCallback<Movie>() {

    private val mMovieGenre = movieGenre
    private val mScope = scope

    private var mList = MediatorLiveData<Movie>()

    private var mPageIndex = 0

    override fun onZeroItemsLoaded() {
//        super.onZeroItemsLoaded()

        mScope.launch {
            withContext(Dispatchers.Default) {
                try {
                    val request = ServiceGenerator.createService(MovieService::class.java)
                            .getMovieList(mMovieGenre, mPageIndex, Constants.DEFAULT_PAGE_SIZE.toString())
                            .execute()

                    if (request.isSuccessful) {
                        request.body()?.let {
                            //                            mList.addSource()
                            doDbInsert(it.responseList)
                        }
                    }
                } catch (e: Exception) {
                    Timber.e(e)
                }
            }
        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: Movie) {
//        super.onItemAtEndLoaded(itemAtEnd)
        mPageIndex += 20
        mScope.launch {
            withContext(Dispatchers.Default) {
                try {
                    val request = ServiceGenerator.createService(MovieService::class.java)
                            .getMovieList(mMovieGenre, mPageIndex, Constants.DEFAULT_PAGE_SIZE.toString())
                            .execute()

                    if (request.isSuccessful) {
                        Timber.d("Movie request is successful")
                        request.body()?.let {
                            //                            mBaseResponse = it
//                            mPageIndex += 1
                            for (movie in it.responseList) {
                                movie.movieGenre = mMovieGenre
                            }
                            AppDatabase.getInstance().movieDao().insert(it.responseList)
//                            callback.onResult(it.responseList, null)
                        }
                    }
                } catch (e: Exception) {
                    Timber.e(e)
                }
            }
        }
    }

    private fun doDbInsert(list: List<Movie>) {
        AppDatabase.getInstance().movieDao().deleteByGenre(mMovieGenre)
        AppDatabase.getInstance().movieDao().insert(list)
    }
}