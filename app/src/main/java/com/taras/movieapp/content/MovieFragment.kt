package com.taras.movieapp.content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.taras.movieapp.data.Constants
import com.taras.movieapp.data.ServiceGenerator
import com.taras.movieapp.data.model.BaseResponse
import com.taras.movieapp.data.model.Movie
import com.taras.movieapp.data.service.MovieService
import com.taras.movieapp.databinding.FragmentContentBinding
import kotlinx.coroutines.*
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class MovieFragment : Fragment(), CoroutineScope {

    private lateinit var mBinding: FragmentContentBinding
    private lateinit var mAdapter: ContentAdapter

    companion object {
        fun newInstance(): MovieFragment {
            return MovieFragment()
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + SupervisorJob()

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

        mBinding.btnFindFilm.setOnClickListener {
            val textGenre = mBinding.editFindFilm.text.toString()
            if (!textGenre.isEmpty()) {
                doRequest(textGenre)
            }
        }

    }

    private fun doRequest(genre: String) = launch {
        var baseResponse: BaseResponse? = null
        var movieList: List<Movie> = ArrayList()
        try {
            withContext(Dispatchers.Default) {
                val request =
                    ServiceGenerator.createService(MovieService::class.java)
                        .getResponse(genre)
                val response = request.await()

                if (response.isSuccessful) {
                    baseResponse = response.body()!!
                }
            }
        } catch (e: Exception) {
            Timber.e("$e")
        }

        //FIXME: need to rewrite
        if (baseResponse != null && baseResponse?.code == 200 && baseResponse?.reason == "OK") {
            try {
                withContext(Dispatchers.Default) {
                    val request =
                        ServiceGenerator.createService(MovieService::class.java)
                            .getMovieList(Constants.GENRE_HORROR, 0, baseResponse?.totalFound!!)
                    val response = request.await()

                    if (response.isSuccessful) {
                        movieList = response.body()?.responseList!!
                    }
                }
            } catch (e: Exception) {
                Timber.e("$e")
            }
        }

        mAdapter.setList(movieList)
    }

    override fun onStop() {
        super.onStop()
        coroutineContext.cancelChildren()
    }
}