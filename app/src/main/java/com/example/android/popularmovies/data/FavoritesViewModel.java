package com.example.android.popularmovies.data;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.android.popularmovies.models.Movie;

import java.util.List;

public class FavoritesViewModel extends AndroidViewModel {

    private FavoritesRepository mRepository;
    private LiveData<List<Movie>> mAllFavoriteMovies;

    public FavoritesViewModel (Application application) {
        super(application);
        mRepository = new FavoritesRepository(application);
        mAllFavoriteMovies = mRepository.getAllFavoriteMovies();
    }

    public void insert(Movie favoriteMovie) { mRepository.insert(favoriteMovie); }

    public void delete(Movie favoriteMove) { mRepository.delete(favoriteMove); }

    public LiveData<List<Movie>> getAllFavoriteMovies() { return mAllFavoriteMovies; }

    public void checkIfFavoriteAndUpdateDatabase(int id) {
        mRepository.checkIfFavoriteAndUpdateDatabase(id);
    }

    public void checkIfFavoriteAndUpdateImageView(int id) {
        mRepository.checkIfFavoriteAndUpdateImageView(id);
    }
}
