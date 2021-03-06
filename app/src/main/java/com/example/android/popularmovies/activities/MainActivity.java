package com.example.android.popularmovies.activities;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.activities.DetailsActivity;
import com.example.android.popularmovies.data.FavoritesViewModel;
import com.example.android.popularmovies.models.Movie;
import com.example.android.popularmovies.adapters.MovieAdapter;
import com.example.android.popularmovies.utilities.MovieDatabaseJsonUtils;
import com.example.android.popularmovies.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity
        implements MovieAdapter.MovieAdapterOnClickHandler {

    private static final String MOST_POPULAR_MOVIES_URL =
            "https://api.themoviedb.org/3/movie/popular?api_key=[YOUR_API_KEY]&language=en-US&page=1";
    private static final String TOP_RATED_MOVIES_URL =
            "https://api.themoviedb.org/3/movie/top_rated?api_key=[YOUR_API_KEY]&language=en-US&page=1";
    private static final String BUNDLE_RECYCLER_LAYOUT = "classname.recycler.layout";

    private Boolean lastPageFavorites;
    private FavoritesViewModel mFavoritesViewModel;
    private ArrayList<Movie> mFavoriteMovies;
    private RecyclerView mMoviesRecyclerView;
    private MovieAdapter mAdapter;
    private TextView mErrorMessageTextView;
    private ProgressBar mLoadingIndicatorProgressBar;
    private GridLayoutManager layoutManager;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("-----------------------", "onCreate() called.");

        lastPageFavorites = false;
        mMoviesRecyclerView = findViewById(R.id.rv_movies);
        mErrorMessageTextView = findViewById(R.id.tv_error_message);
        mLoadingIndicatorProgressBar = findViewById(R.id.pb_loading_indicator);
        mFavoritesViewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);
        mFavoritesViewModel.getAllFavoriteMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                mFavoriteMovies = (ArrayList<Movie>) movies;
                if (lastPageFavorites == true) {
                    mAdapter.setMovieData(mFavoriteMovies);
                }
            }
        });

        // Setting the layout manager for the RecyclerView.
        int NUM_COLS = 3;
        layoutManager = new GridLayoutManager(this, NUM_COLS);
        mMoviesRecyclerView.setLayoutManager(layoutManager);

        mMoviesRecyclerView.setHasFixedSize(true);

        // Setting the adapter for the RecyclerView.
        mAdapter = new MovieAdapter(this);
        mMoviesRecyclerView.setAdapter(mAdapter);

        if (savedInstanceState != null) {
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            mMoviesRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }

        // Get a reference to the ConnectivityManager to check state of network connectivity.
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network.
        NetworkInfo networkInfo = Objects.requireNonNull(connMgr).getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            // Make JSON request to The Movie Database's API.
            makeSearchQuery(MOST_POPULAR_MOVIES_URL);
        } else {
            mLoadingIndicatorProgressBar.setVisibility(View.INVISIBLE);
            mErrorMessageTextView.setText(R.string.no_connection);
            mErrorMessageTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, mMoviesRecyclerView.getLayoutManager().onSaveInstanceState());
    }

    /**
     * This method constructs the URL (using {@link NetworkUtils}), then fires off an AsyncTask to
     * perform the GET request using our {@link QueryTask}.
     */
    private void makeSearchQuery(String urlStr) {
        URL searchUrl = NetworkUtils.buildUrl(urlStr);
        new QueryTask().execute(searchUrl);
    }

    /**
     * This method handles RecyclerView item clicks.
     *
     * @param movie The details for the movie that was clicked.
     */
    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("details", movie);
        startActivity(intent);
    }

    @SuppressLint("StaticFieldLeak")
    class QueryTask extends AsyncTask<URL, Void, ArrayList<Movie>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicatorProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(URL... urls) {
            // If there are no urls then return null.
            if (urls.length == 0) {
                return null;
            }

            URL searchUrl = urls[0];
            try {
                String jsonMovieResponse = NetworkUtils
                        .getResponseFromHttpUrl(searchUrl);

                return MovieDatabaseJsonUtils
                        .getMovieListFromJson(jsonMovieResponse);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movieList) {
            mLoadingIndicatorProgressBar.setVisibility(View.INVISIBLE);
            layoutManager.smoothScrollToPosition(mMoviesRecyclerView, null, 0);

            if (movieList != null) {
                mErrorMessageTextView.setVisibility(View.INVISIBLE);
                mMoviesRecyclerView.setVisibility(View.VISIBLE);
                mAdapter.setMovieData(movieList);
            } else {
                mErrorMessageTextView.setVisibility(View.VISIBLE);
                mMoviesRecyclerView.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.most_popular_movies:
                makeSearchQuery(MOST_POPULAR_MOVIES_URL);
                lastPageFavorites = false;
                return true;
            case R.id.top_rated_movies:
                makeSearchQuery(TOP_RATED_MOVIES_URL);
                lastPageFavorites = false;
                return true;
            case R.id.favorite_movies:
                mAdapter.setMovieData(mFavoriteMovies);
                lastPageFavorites = true;
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
