package com.taras.movieapp.data.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.taras.movieapp.data.Constants
import com.taras.movieapp.data.ServiceGenerator
import com.taras.movieapp.data.model.BaseResponse
import com.taras.movieapp.data.model.Movie
import com.taras.movieapp.data.model.NetworkState
import com.taras.movieapp.data.service.MovieService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber


class MovieDataSource() : PageKeyedDataSource<Int, Movie>() {

    private var mNetworkState = MutableLiveData<Any>()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {

        val currentPage = 1
        val nextPage = currentPage + 1

//        mNetworkState.postValue(NetworkState.LOADING)

        val request = ServiceGenerator.createService(MovieService::class.java).getMovieList(Constants.GENRE_HOTTIES, 0, "")
        request.enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        callback.onResult(it.responseList, null, 1)
//                        mNetworkState.postValue(NetworkState.LOADED)
                    }
                } else {
                    mNetworkState.postValue(NetworkState.error("Failed response!"))
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Timber.e(t)
            }
        })

//        try {
//
//
//        } catch (e: Exception) {
//            Timber.e(e)
//        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

    }
}