package com.taras.movieapp.content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.taras.movieapp.data.ServiceGenerator
import com.taras.movieapp.data.database.AppDatabase
import com.taras.movieapp.data.model.BaseResponse
import com.taras.movieapp.data.model.Movie
import com.taras.movieapp.data.service.MovieService
import com.taras.movieapp.databinding.FragmentContentBinding
import kotlinx.coroutines.*
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class ContentFragment : Fragment(), CoroutineScope {

    private lateinit var mBinding: FragmentContentBinding
    private lateinit var mAdapter: ContentAdapter

    private var mMovieGenre = ""

    companion object {
        private const val MOVIE_GENRE = "movie_genre"

        fun newInstance(movieGenre: String): ContentFragment {
            val bundle = bundleOf(MOVIE_GENRE to movieGenre)
            return ContentFragment().apply { arguments = bundle }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + SupervisorJob()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.run {
            mMovieGenre = getString(MOVIE_GENRE)!!
        }
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentContentBinding.inflate(inflater, container, false)
        mAdapter = ContentAdapter()
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rv = mBinding.recyclerView
        rv.layoutManager = LinearLayoutManager(activity)
        rv.setHasFixedSize(true)
        rv.adapter = mAdapter

        mBinding.swipeRefresh.setOnRefreshListener {
            mBinding.swipeRefresh.isRefreshing = true
            doMovieRequest(mMovieGenre)
        }

        doMovieLoad(mMovieGenre)
    }

    private fun doMovieRequest(movieGenre: String) = launch {
        var baseResponse: BaseResponse? = null
        var movieList: List<Movie> = ArrayList()
        try {
            withContext(Dispatchers.Default) {
                val request =
                    ServiceGenerator.createService(MovieService::class.java)
                        .getMovieList(movieGenre, 0, "")
                val response = request.await()

                if (response.isSuccessful) {
                    baseResponse = response.body()
                    baseResponse?.movieGenre = movieGenre
                    movieList = response.body()?.responseList!!

                    for (movie in movieList) {
                        movie.movieGenre = movieGenre
                    }

                    try {
                        AppDatabase.getInstance().baseResponseDao().deleteByGenre(movieGenre)
                        AppDatabase.getInstance().baseResponseDao().insert(baseResponse!!)

                        AppDatabase.getInstance().movieDao().deleteByGenre(movieGenre)
                        AppDatabase.getInstance().movieDao().insert(movieList)
                    } catch (e: Exception) {
                        Timber.e("$e")
                    }
                    mAdapter.setList(movieList, true)
                }
            }
        } catch (e: Exception) {
            Timber.e("$e")
        }
        mBinding.swipeRefresh.isRefreshing = false
    }

    private fun doMovieLoad(movieGenre: String) = launch {
        var movieList: List<Movie> = ArrayList()
        try {
            withContext(Dispatchers.Default) {
                movieList = AppDatabase.getInstance().movieDao().getMoviesByType(movieGenre)
                if (movieList.isEmpty()) {
                    doMovieRequest(movieGenre)
                } else {
                    mAdapter.setList(movieList, true)
                    mBinding.swipeRefresh.isRefreshing = false
                }
            }
        } catch (e: Exception) {
            Timber.e("$e")
            mBinding.swipeRefresh.isRefreshing = false
        }
    }

    override fun onStop() {
        super.onStop()
        coroutineContext.cancelChildren()
    }
}