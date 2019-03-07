package com.taras.movieapp;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class MovieApplication extends Application {

    public static MovieApplication sInstance;

    public MovieApplication() {
        sInstance = this;
    }

    public static MovieApplication getMovieAppContext() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
