package com.taras.movieapp.coroutines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.taras.movieapp.utils.Constants
import com.taras.movieapp.mvvm.data.network.ServiceGenerator
import com.taras.movieapp.mvvm.data.database.AppDatabase
import com.taras.movieapp.mvvm.data.entities.Movie
import com.taras.movieapp.mvvm.data.network.services.MovieService
import com.taras.movieapp.databinding.FragmentCoroutineBinding
import kotlinx.android.synthetic.main.fragment_coroutine.*
import kotlinx.coroutines.*
import timber.log.Timber
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.system.measureTimeMillis

class CoroutineFragment : Fragment(), CoroutineScope {
    /*
    The real reason is that withContext() a simpler and more direct API, especially in terms of exception handling:

    An exception that isn't handled within async { ... } causes its parent job to get cancelled. This happens regardless of how you handle exceptions from the matching await().
    If you haven't prepared a coroutineScope for it, it may bring down your entire application.
    An exception not handled within withContext { ... } simply gets thrown by the withContext call, you handle it just like any other.

    withContext also happens to be optimized, leveraging the fact that you're suspending the parent coroutine and awaiting on the child, but that's just an added bonus.
    async-await should be reserved for those cases where you actually want concurrency, so that you launch several coroutines in the background and only then await on them. In short:

    - async-await-async-await — same as withContext-withContext
    - async-async-await-await — that's the way to use it.

     */

    private lateinit var mBinding: FragmentCoroutineBinding
    private val mSupervisionJob = SupervisorJob()
    private val mJob = Job()

    private var mActionList = ArrayList<Movie>()
    private var mDramaList = ArrayList<Movie>()

    companion object {
        fun newInstance() = CoroutineFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentCoroutineBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        doSingleRequestAction()
//        doSingleRequestDrama()

        start_coroutine.setOnClickListener {
            //            message1.text = ""
//            message2.text = ""
//            message1.text = measureTimeMillis { doSingleRequestAction() }.toString()
//            message2.text = measureTimeMillis { doSingleRequestDrama() }.toString()
            launch {
                //                doWork()
//                doWork2()

//                workParallel2()
            }
            doSingleRequestAction()
//            doSingleRequestDrama()
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + mSupervisionJob


    //============

    private suspend fun workParallel2() = supervisorScope {
        val task1 = async {
            loadWork1Async()
        }
        val task2 = async {
            loadWork2Async()
        }

        try {
            val result1 = task1.await()
        } catch (e: Exception) {
            Timber.e(e)
        }
        try {
            task2.await()
        } catch (e: Exception) {
            Timber.e(e)
        }
        val r = "sfsf"
    }

    private suspend fun loadWork1Async() = withContext(Dispatchers.IO) {
        delay(TimeUnit.SECONDS.toMillis(6))
        ServiceGenerator.createService(MovieService::class.java)
                .getMovieListDeferred(Constants.GENRE_DRAMA, 0, Constants.DEFAULT_PAGE_SIZE.toString())
    }

    private suspend fun loadWork2Async() = withContext(Dispatchers.IO) {
        delay(TimeUnit.SECONDS.toMillis(2))
        ServiceGenerator.createService(MovieService::class.java)
                .getMovieListDeferred(Constants.GENRE_ACTION, 0, Constants.DEFAULT_PAGE_SIZE.toString())
    }

    //============

    private suspend fun workParallel() = supervisorScope {
        val task1 = ServiceGenerator.createService(MovieService::class.java)
//                .getMovieListDeferred(Constants.GENRE_DRAMA, 0, Constants.DEFAULT_PAGE_SIZE.toString())
//
//        val task2 = ServiceGenerator.createService(MovieService::class.java)
//                .getMovieListDeferred(Constants.GENRE_ACTION, 0, Constants.DEFAULT_PAGE_SIZE.toString())
//
//        var result1: Response<BaseResponse>? = null
//        var result2: Response<BaseResponse>? = null
//        try {
//            result1 = task1.await()
//            if (result1.isSuccessful) {
//                val list = result1.body()
//            }
//        } catch (e: Exception) {
//            Timber.e(e)
//        }
//        try {
//            result2 = task2.await()
//            if (result2.isSuccessful) {
//                val list = result2.body()
//            }
//        } catch (e: Exception) {
//            Timber.e(e)
//        }
//        val res = result1
    }

    //============ Parallel requests:

    private suspend fun doWork() = supervisorScope {
        val request1 = async(Dispatchers.IO) {
            //            ServiceGenerator.createService(MovieService::class.java)
//                    .getMovieListRequestAsync(Constants.GENRE_ACTION, 0, Constants.DEFAULT_PAGE_SIZE.toString())
            AppDatabase.getInstance().movieDao().getMoviesByGenreSuspend(Constants.GENRE_ACTION) as ArrayList<Movie>
        }
        val request2 = async(Dispatchers.IO) {
            //            throw IllegalArgumentException("TEST EXCEPTION!!!")

            //            ServiceGenerator.createService(MovieService::class.java)
//                    .getMovieListRequestAsync(Constants.GENRE_DRAMA, 0, Constants.DEFAULT_PAGE_SIZE.toString())
            AppDatabase.getInstance().movieDao().getMoviesByGenreSuspend(Constants.GENRE_DRAMA) as ArrayList<Movie>
        }

        try {
            message1.text = measureTimeMillis {
                delay(7000)
                mActionList = request1.await()
            }.toString()
        } catch (e: Exception) {
            Timber.e(e)
            message1.text = e.toString()
        }
        try {
            message2.text = measureTimeMillis {
                mDramaList = request2.await()
            }.toString()
        } catch (e: Exception) {
            Timber.e(e)
            message2.text = e.toString()
        }

        val result = mDramaList

    }

    //============

    private suspend fun doRequestWithResume(): List<Movie> {
        // Returns with "android.os.NetworkOnMainThreadException"
        // Needs to switch Coroutine Context
        return suspendCoroutine { continuation ->
            try {
                val request = ServiceGenerator.createService(MovieService::class.java)
                        .getMovieList(Constants.GENRE_ACTION, 0, Constants.DEFAULT_PAGE_SIZE.toString())
                        .execute()

                if (request.isSuccessful) {
                    request.body()?.let {
                        mActionList.addAll(it.responseList)
                    }

                    continuation.resume(mActionList)
                }
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    //============

    private fun doSequential() = launch {
        withContext(Dispatchers.IO) {
            try {
//            throw IllegalArgumentException("TEST EXCEPTION!!!")
                val request = ServiceGenerator.createService(MovieService::class.java)
                        .getMovieList(Constants.GENRE_ACTION, 0, Constants.DEFAULT_PAGE_SIZE.toString())
                        .execute()

                if (request.isSuccessful) {
                    request.body()?.let {
                        mActionList.addAll(it.responseList)
                    }
                }
            } catch (e: Exception) {
                Timber.e(e)
            }
        }

        withContext(Dispatchers.IO) {
            try {
//            throw IllegalArgumentException("ERROR!!!")
                val request = ServiceGenerator.createService(MovieService::class.java)
                        .getMovieList(Constants.GENRE_DRAMA, 0, Constants.DEFAULT_PAGE_SIZE.toString())
                        .execute()

                if (request.isSuccessful) {
                    request.body()?.let {
                        mDramaList.addAll(it.responseList)
                    }
                }
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    //============ Parallel requests:

    private fun doSingleRequestAction() = launch {
        val async1 = async { suspendRequestAction() }
        val async2 = async { suspendRequestDrama() }

        try {
            val result1 = async1.await()
        } catch (e: Exception) {
            Timber.e(e)
        }
        try {
            val result2 = async2.await()
        } catch (e: Exception) {
            Timber.e(e)
        }
        val res = "sff"
    }

    private fun doSingleRequestDrama() = launch {
        suspendRequestDrama()
    }

    private suspend fun suspendRequestAction() = withContext(Dispatchers.IO) {
        try {
            delay(2000)
//            throw IllegalArgumentException("ERROR!!!")
            val request = ServiceGenerator.createService(MovieService::class.java)
                    .getMovieList(Constants.GENRE_ACTION, 0, Constants.DEFAULT_PAGE_SIZE.toString())
                    .execute()

            if (request.isSuccessful) {
                request.body()?.let {
                    mActionList.addAll(it.responseList)
                }
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    private suspend fun suspendRequestDrama() = withContext(Dispatchers.IO) {
        try {
            delay(9000)
//            cancelJob()
            val request = ServiceGenerator.createService(MovieService::class.java)
                    .getMovieList(Constants.GENRE_DRAMA, 0, Constants.DEFAULT_PAGE_SIZE.toString())
                    .execute()

            if (request.isSuccessful) {
                request.body()?.let {
                    mDramaList.addAll(it.responseList)
                }
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    //============

    private fun cancelJob() {
        try {
//            mSupervisionJob.cancel()
            mSupervisionJob.cancelChildren()
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    override fun onStop() {
        super.onStop()
        coroutineContext.cancelChildren()
    }
}