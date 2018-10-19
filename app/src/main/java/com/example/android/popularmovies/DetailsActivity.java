package com.example.android.popularmovies;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class DetailsActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Movie movie = (Movie) intent.getSerializableExtra("details");

        // Load backdrop image into backdrop ImageView.
        ImageView mBackdropImageView = findViewById(R.id.iv_movie_details_backdrop);
        String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w780/";
        if (movie.getBackdropPath() != null) {
            String backdropPathUrl = IMAGE_BASE_URL + movie.getBackdropPath();
            Picasso.get().load(backdropPathUrl).into(mBackdropImageView);
        } else {
            mBackdropImageView.setVisibility(View.GONE);
        }

        // Load poster image into poster ImageView.
        ImageView mPosterImageView = findViewById(R.id.iv_movie_details_poster);
        if (movie.getPosterPath() != null) {
            String posterPathUrl = IMAGE_BASE_URL + movie.getPosterPath();
            Picasso.get().load(posterPathUrl).into(mPosterImageView);
        } else {
            mPosterImageView.setVisibility(View.INVISIBLE);
        }

        // Set movie title TextView.
        TextView mTitleTextView = findViewById(R.id.tv_movie_details_title);
        mTitleTextView.setText(movie.getTitle());

        // Set release date TextView.
        TextView mReleaseDateTextView = findViewById(R.id.tv_movie_details_release_date);
        mReleaseDateTextView.setText(formatDate(movie.getReleaseDate()));

        // Set vote average TextView.
        TextView mVoteAverageTextView = findViewById(R.id.tv_movie_details_vote_average);
        mVoteAverageTextView.setText(String.format(Locale.getDefault(), getString(R.string.voter_average_formatter), movie.getVoterAverage()));

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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //Convert date back to String from Date.
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("MMM dd, yyyy");
        // Return the formatted date String.
        return df.format(convertedDate);
    }
}
