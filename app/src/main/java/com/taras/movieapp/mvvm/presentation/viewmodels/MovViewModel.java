package com.taras.movieapp.mvvm.presentation.viewmodels;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.taras.movieapp.mvvm.MovieApplication;
import com.taras.movieapp.mvvm.data.entities.Movie;
import com.taras.movieapp.mvvm.domain.usecases.MovieUseCase;

import java.util.List;

public class MovViewModel extends AndroidViewModel{

    private String mMovieGenre = "";
    private MovieUseCase mMobieCase;

    public MovViewModel(MovieApplication application) {
        super(application);
    }

    public MovViewModel(MovieApplication application, MovieUseCase movieUseCase) {
        super(application);
        mMobieCase = movieUseCase;
    }

    public LiveData<List<Movie>> getMovieList() {
        return mMobieCase.getMovieList(mMovieGenre);
    }
}
