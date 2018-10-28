package com.example.android.popularmovies.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.popularmovies.activities.DetailsActivity;
import com.example.android.popularmovies.models.Movie;

import java.util.List;

public class FavoritesRepository {

    private FavoritesDao mFavoritesDao;
    private LiveData<List<Movie>> mAllFavoriteMovies;

    FavoritesRepository(Application application) {
        FavoritesRoomDatabase db = FavoritesRoomDatabase.getDatabase(application);
        mFavoritesDao = db.favoriteMovieDao();
        mAllFavoriteMovies = mFavoritesDao.getAllFavoriteMovies();
    }

    public void insert (Movie favoriteMovie) {
        new insertAsyncTask(mFavoritesDao).execute(favoriteMovie);
    }

    public void delete (Movie favoriteMovie) {
        new deleteAsyncTask(mFavoritesDao).execute(favoriteMovie);
    }

    public void checkIfFavoriteAndUpdateDatabase(int id) {
        new queryAndUpdateDatabaseAsyncTask(mFavoritesDao).execute(id);
    }

    public void checkIfFavoriteAndUpdateImageView(int id) {
        new queryAndUpdateImageViewAsyncTask(mFavoritesDao).execute(id);
    }

    LiveData<List<Movie>> getAllFavoriteMovies() { return mAllFavoriteMovies; }
    
    private static class insertAsyncTask extends AsyncTask<Movie, Void, Void> {

        private FavoritesDao mAsyncTaskDao;

        insertAsyncTask(FavoritesDao dao) { mAsyncTaskDao = dao; }

        @Override
        protected Void doInBackground(final Movie... favoriteMovies) {
            mAsyncTaskDao.insert(favoriteMovies[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Movie, Void, Void> {

        private FavoritesDao mAsyncTaskDao;

        deleteAsyncTask(FavoritesDao dao) { mAsyncTaskDao = dao; }

        @Override
        protected Void doInBackground(Movie... favoriteMovies) {
            mAsyncTaskDao.deleteFavoriteMovie(favoriteMovies[0]);
            return null;
        }
    }

    private static class queryAndUpdateDatabaseAsyncTask extends AsyncTask<Integer, Void, Movie> {

        private FavoritesDao mAsyncTaskDao;

        queryAndUpdateDatabaseAsyncTask(FavoritesDao dao) { mAsyncTaskDao = dao; }

        @Override
        protected Movie doInBackground(Integer... ids) {
            Movie queriedMovie;
            queriedMovie = mAsyncTaskDao.checkIfAlreadyFavorite(ids[0]);
            return queriedMovie;
        }

        @Override
        protected void onPostExecute(Movie queriedMovie) {
            super.onPostExecute(queriedMovie);
            DetailsActivity.updateFavoritesDatabase(queriedMovie);
        }
    }

    private static class queryAndUpdateImageViewAsyncTask extends AsyncTask<Integer, Void, Movie> {

        private FavoritesDao mAsyncTaskDao;

        queryAndUpdateImageViewAsyncTask(FavoritesDao dao) { mAsyncTaskDao = dao; }

        @Override
        protected Movie doInBackground(Integer... ids) {
            Movie queriedMovie;
            queriedMovie = mAsyncTaskDao.checkIfAlreadyFavorite(ids[0]);
            return queriedMovie;
        }

        @Override
        protected void onPostExecute(Movie queriedMovie) {
            super.onPostExecute(queriedMovie);
            DetailsActivity.updateFavoritesImageView(queriedMovie);
        }
    }
}
