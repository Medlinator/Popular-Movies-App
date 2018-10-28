package com.example.android.popularmovies.activities;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.FavoritesViewModel;
import com.example.android.popularmovies.models.Movie;
import com.example.android.popularmovies.models.Review;
import com.example.android.popularmovies.adapters.ReviewAdapter;
import com.example.android.popularmovies.models.Trailer;
import com.example.android.popularmovies.adapters.TrailerAdapter;
import com.example.android.popularmovies.utilities.MovieDatabaseJsonUtils;
import com.example.android.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";
    private static final String MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String REVIEWS_URL = "/reviews?api_key=[YOUR_API_KEY]&language=en-US&page=1";
    private static final String TRAILERS_URL = "/videos?api_key=[YOUR_API_KEY]&language=en-US";

    private int mId;
    private String mTitle;
    private String mPosterPath;
    private static Movie movie;
    private Movie queryMovie;
    private static ImageView mFavoritesImageView;
    private RecyclerView mReviewsRecyclerView;
    private RecyclerView mTrailersRecyclerView;
    private ReviewAdapter mReviewAdapter;
    private TrailerAdapter mTrailerAdapter;
    private LinearLayoutManager mReviewLayoutManager;
    private LinearLayoutManager mTrailerLayoutManager;
    private DividerItemDecoration mReviewDividerItemDecoration;
    private DividerItemDecoration mTrailerDividerItemDecoration;
    private static FavoritesViewModel mFavoritesViewModel;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        movie = (Movie) intent.getSerializableExtra("details");

        mId = movie.getId();

        mFavoritesImageView = findViewById(R.id.tv_movie_details_favorite_star);

        mFavoritesViewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);
        mFavoritesViewModel.checkIfFavoriteAndUpdateImageView(mId);

        mFavoritesImageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mFavoritesViewModel.checkIfFavoriteAndUpdateDatabase(mId);
            }
        });

        mReviewsRecyclerView = findViewById(R.id.rv_reviews);

        mTrailersRecyclerView = findViewById(R.id.rv_trailers);

        mReviewLayoutManager = new LinearLayoutManager(this);
        mReviewsRecyclerView.setLayoutManager(mReviewLayoutManager);
        mReviewDividerItemDecoration = new DividerItemDecoration(mReviewsRecyclerView.getContext(),
                mReviewLayoutManager.getOrientation());
        mReviewsRecyclerView.addItemDecoration(mReviewDividerItemDecoration);

        mTrailerLayoutManager = new LinearLayoutManager(this);
        mTrailersRecyclerView.setLayoutManager(mTrailerLayoutManager);
        mTrailerDividerItemDecoration = new DividerItemDecoration(
                mTrailersRecyclerView.getContext(),
                mTrailerLayoutManager.getOrientation());
        mTrailersRecyclerView.addItemDecoration(mTrailerDividerItemDecoration);

        mReviewsRecyclerView.setHasFixedSize(true);
        mTrailersRecyclerView.setHasFixedSize(true);

        mReviewAdapter = new ReviewAdapter();

        mTrailerAdapter = new TrailerAdapter(this);

        mReviewsRecyclerView.setAdapter(mReviewAdapter);
        mTrailersRecyclerView.setAdapter(mTrailerAdapter);

        // Get a reference to the ConnectivityManager to check state of network connectivity.
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network.
        NetworkInfo networkInfo = Objects.requireNonNull(connMgr).getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected())

        {
            // Make JSON request to The Movie Database's API.
            makeTrailerSearchQuery(MOVIE_BASE_URL);
            makeReviewSearchQuery(MOVIE_BASE_URL);
        }

        // Load backdrop image into backdrop ImageView.
        ImageView mBackdropImageView = findViewById(R.id.iv_movie_details_backdrop);
        String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w780/";
        if (movie.getBackdropPath() != null)

        {
            String backdropPathUrl = IMAGE_BASE_URL + movie.getBackdropPath();
            Picasso.get().load(backdropPathUrl).into(mBackdropImageView);
        } else

        {
            mBackdropImageView.setVisibility(View.GONE);
        }

        // Load poster image into poster ImageView.
        ImageView mPosterImageView = findViewById(R.id.iv_movie_details_poster);
        if (movie.getPosterPath() != null)

        {
            String posterPathUrl = IMAGE_BASE_URL + movie.getPosterPath();
            Picasso.get().load(posterPathUrl).into(mPosterImageView);
        } else

        {
            mPosterImageView.setVisibility(View.INVISIBLE);
        }

        // Set movie title TextView.
        TextView mTitleTextView = findViewById(R.id.tv_movie_details_title);
        mTitleTextView.setText(movie.getTitle());

        // Set release date TextView.
        TextView mReleaseDateTextView = findViewById(R.id.tv_movie_details_release_date);
        mReleaseDateTextView.setText(

                formatDate(movie.getReleaseDate()));

        // Set vote average TextView.
        TextView mVoteAverageTextView = findViewById(R.id.tv_movie_details_vote_average);
        mVoteAverageTextView.setText(String.format(Locale.getDefault(),

                getString(R.string.voter_average_formatter), movie.

                        getVoteAverage()));

        // Set overview TextView.
        TextView mOverviewTextView = findViewById(R.id.tv_movie_details_overview);
        mOverviewTextView.setText(movie.getOverview());
    }

    private String formatDate(String date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Convert date back to String from Date.
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("MMM dd, yyyy");
        // Return the formatted date String.
        return df.format(convertedDate);
    }

    /**
     * This method constructs the URL (using {@link NetworkUtils}), then fires off an AsyncTask to
     * perform the GET request using our {@link DetailsActivity.TrailerQueryTask}.
     */
    private void makeTrailerSearchQuery(String baseUrlStr) {
        String urlStr = baseUrlStr + mId + TRAILERS_URL;
        URL searchUrl = NetworkUtils.buildUrl(urlStr);
        new TrailerQueryTask().execute(searchUrl);
    }

    /**
     * This method constructs the URL (using {@link NetworkUtils}), then fires off an AsyncTask to
     * perform the GET request using our {@link DetailsActivity.ReviewQueryTask}.
     */
    private void makeReviewSearchQuery(String baseUrlStr) {
        String urlStr = baseUrlStr + mId + REVIEWS_URL;
        URL searchUrl = NetworkUtils.buildUrl(urlStr);
        new ReviewQueryTask().execute(searchUrl);
    }

    @SuppressLint("StaticFieldLeak")
    class TrailerQueryTask extends AsyncTask<URL, Void, ArrayList<Trailer>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Trailer> doInBackground(URL... urls) {
            // If there are no urls then return null.
            if (urls.length == 0) {
                return null;
            }

            URL searchUrl = urls[0];
            try {
                String jsonTrailerResponse = NetworkUtils
                        .getResponseFromHttpUrl(searchUrl);

                return MovieDatabaseJsonUtils
                        .getTrailerListFromJson(jsonTrailerResponse);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Trailer> trailerList) {
            mTrailerAdapter.setTrailerData(trailerList);
        }
    }

    @SuppressLint("StaticFieldLeak")
    class ReviewQueryTask extends AsyncTask<URL, Void, ArrayList<Review>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Review> doInBackground(URL... urls) {
            // If there are no urls then return null.
            if (urls.length == 0) {
                return null;
            }

            URL searchUrl = urls[0];
            try {
                String jsonReviewResponse = NetworkUtils
                        .getResponseFromHttpUrl(searchUrl);

                return MovieDatabaseJsonUtils
                        .getReviewListFromJson(jsonReviewResponse);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Review> reviewList) {
            mReviewAdapter.setReviewData(reviewList);
        }
    }

    public static void updateFavoritesImageView(Movie queryMovie) {
        if (queryMovie != null) {
            mFavoritesImageView.setImageResource(R.drawable.favorite_star);
        } else {
            mFavoritesImageView.setImageResource(R.drawable.unfavorite_star);
        }
    }

    public static void updateFavoritesDatabase(Movie queryMovie) {
        if (queryMovie != null) {
            mFavoritesViewModel.delete(queryMovie);
            mFavoritesImageView.setImageResource(R.drawable.unfavorite_star);
        } else {
            mFavoritesViewModel.insert(movie);
            mFavoritesImageView.setImageResource(R.drawable.favorite_star);
        }
    }
}
