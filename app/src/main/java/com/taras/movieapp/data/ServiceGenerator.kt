package com.taras.movieapp.data

import android.os.Build
import android.text.TextUtils
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.taras.movieapp.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

object ServiceGenerator {
    private val httpClient: OkHttpClient.Builder

    private val builder: Retrofit.Builder

    init {
        Timber.d("[API BASE URL] %s", BuildConfig.API_BASE_URL)

//        val instanceID = FirebaseInstanceId.getInstance().id
//
//        var pInfo: PackageInfo? = null
//        try {
//            pInfo = App.getInstance().packageManager.getPackageInfo(
//                App.getInstance().packageName, 0)
//        } catch (e: PackageManager.NameNotFoundException) {
//            Timber.e(e)
//        }
//
//        val version = pInfo?.versionName ?: "unknown"
//        Timber.d("Version: %s", version)

        httpClient = OkHttpClient.Builder().apply {
            addInterceptor { chain ->
                val token = "0e43dbd87790051276266d53a0a8b4f3"
                val requestBuilder = chain.request().newBuilder()
//                requestBuilder
//                    .addHeader("APP-INSTANCE-ID", instanceID)
//                    .addHeader("APP-VERSION", version)
//                    .addHeader("DEVICE-OS", "ANDROID")
//                    .addHeader("DEVICE-OS-VERSION", Build.VERSION.RELEASE)
//                    .addHeader("DEVICE-GENERAL-INFO", Build.MODEL)
//                if (!TextUtils.isEmpty(token)) {
//                    requestBuilder.addHeader("api_key", token)
//                }
                chain.proceed(requestBuilder.build())
            }

//            if (BuildConfig.DEBUG) {
//                addNetworkInterceptor(StethoInterceptor())
//            }
        }

        builder = Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(createGsonConverterFactory())
            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
    }

    private fun createGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    fun <S> createService(serviceClass: Class<S>): S {
        Timber.d("Call <%s>", serviceClass.simpleName)
        val client = httpClient
            .build()
        val retrofit = builder
            .client(client)
            .build()
        return retrofit.create(serviceClass)
    }
}