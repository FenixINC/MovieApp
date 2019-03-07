package com.taras.movieapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.taras.movieapp.content.FragmentContentPager
import com.taras.movieapp.data.Constants
import com.taras.movieapp.data.ServiceGenerator
import com.taras.movieapp.data.model.BaseResponse
import com.taras.movieapp.data.model.Movie
import com.taras.movieapp.data.service.MovieService
import kotlinx.coroutines.*
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + SupervisorJob()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val toolbar = findViewById<Toolbar>(R.id.toolbar)
//        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        toolbar.setNavigationOnClickListener {
//            finish()
//        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.content, FragmentContentPager.newInstance())
            .commit()
    }

    private fun doRequest() = launch {
        var baseResponse: BaseResponse? = null
        var movieList: List<Movie> = ArrayList()
        try {
            withContext(Dispatchers.Default) {
                val request =
                    ServiceGenerator.createService(MovieService::class.java)
                        .getResponse(Constants.GENRE_HORROR)
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
    }

    override fun onStop() {
        super.onStop()
        coroutineContext.cancelChildren()
    }
}