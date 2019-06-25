package com.taras.movieapp.mvvm

import android.app.Application
import com.facebook.stetho.Stetho

class MovieApplication : Application() {

    companion object {
        lateinit var sInstance: MovieApplication
            private set

        val getAppInstance: MovieApplication
            get() = sInstance
    }

    override fun onCreate() {
        super.onCreate()
        sInstance = this
        Stetho.initializeWithDefaults(this)
    }
}