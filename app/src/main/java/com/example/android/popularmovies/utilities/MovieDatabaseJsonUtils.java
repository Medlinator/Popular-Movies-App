package com.example.android.popularmovies.utilities;

import com.example.android.popularmovies.models.Movie;
import com.example.android.popularmovies.models.Review;
import com.example.android.popularmovies.models.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public final class MovieDatabaseJsonUtils {

    public static ArrayList<Movie> getMovieListFromJson(String jsonMoviesObjectStr)
            throws JSONException {

        final int NUM_OF_MOVIES = 18;

        final String TMDB_STATUS_CODE = "status_code";
        final String TMDB_RESULTS = "results";
        final String TMDB_ID = "id";
        final String TMDB_TITLE = "title";
        final String TMDB_RELEASE_DATE = "release_date";
        final String TMDB_MOVIE_POSTER = "poster_path";
        final String TMDB_MOVIE_BACKDROP = "backdrop_path";
        final String TMDB_OVERVIEW = "overview";
        final String TMDB_VOTE_AVERAGE = "vote_average";

        ArrayList<Movie> movieArrayList = new ArrayList<>();

        JSONObject jsonMoviesObject = new JSONObject(jsonMoviesObjectStr);

        if (jsonMoviesObject.has(TMDB_STATUS_CODE)) {
            return null;
        }

        JSONArray jsonReviewsArray = jsonMoviesObject.getJSONArray(TMDB_RESULTS);

        for (int i = 0; i < NUM_OF_MOVIES; i++) {
            int id;
            String title;
            String releaseDate;
            String posterPath;
            String backdropPath;
            String overview;
            double voteAverage;

            JSONObject movieObject = jsonReviewsArray.getJSONObject(i);

            // Get id.
            id = movieObject.getInt(TMDB_ID);
            // Get title.
            title = movieObject.getString(TMDB_TITLE);
            // Get release date.
            releaseDate = movieObject.getString(TMDB_RELEASE_DATE);
            // Get poster.
            posterPath = movieObject.getString(TMDB_MOVIE_POSTER);
            // Get backdrop.
            backdropPath = movieObject.getString(TMDB_MOVIE_BACKDROP);
            // Get overview.
            overview = movieObject.getString(TMDB_OVERVIEW);
            // Get vote average.
            voteAverage = movieObject.getDouble(TMDB_VOTE_AVERAGE);

            // Instantiate movie object.
            Movie currentMovie = new Movie(id, title, releaseDate, posterPath, backdropPath, overview, voteAverage);

            // Add movie object to ArrayList.
            movieArrayList.add(currentMovie);
        }

        return movieArrayList;
    }

    public static ArrayList<Trailer> getTrailerListFromJson(String jsonReviewsObjectStr)
            throws JSONException {

        final String TMDB_STATUS_CODE = "status_code";
        final String TMDB_RESULTS = "results";
        final String TMDB_NAME = "name";
        final String TMDB_KEY = "key";

        ArrayList<Trailer> trailerArrayList = new ArrayList<>();

        JSONObject jsonTrailersObject = new JSONObject(jsonReviewsObjectStr);

        if (jsonTrailersObject.has(TMDB_STATUS_CODE)) {
            return null;
        }

        JSONArray jsonTrailersArray = jsonTrailersObject.getJSONArray(TMDB_RESULTS);

        for (int i = 0; i < jsonTrailersArray.length() - 1; i++) {
            String name;
            String key;

            JSONObject trailerObject = jsonTrailersArray.getJSONObject(i);

            // Get content.
            name = trailerObject.getString(TMDB_NAME);
            // Get author.
            key = trailerObject.getString(TMDB_KEY);

            // Instantiate trailer object.
            Trailer currentTrailer = new Trailer(name, key);

            // Add trailer object to ArrayList.
            trailerArrayList.add(currentTrailer);
        }
        return trailerArrayList;
    }

    public static ArrayList<Review> getReviewListFromJson(String jsonReviewsObjectStr)
            throws JSONException {

        final String TMDB_STATUS_CODE = "status_code";
        final String TMDB_RESULTS = "results";
        final String TMDB_CONTENT = "content";
        final String TMDB_AUTHOR = "author";

        ArrayList<Review> reviewArrayList = new ArrayList<>();

        JSONObject jsonReviewsObject = new JSONObject(jsonReviewsObjectStr);

        if (jsonReviewsObject.has(TMDB_STATUS_CODE)) {
            return null;
        }

        JSONArray jsonReviewsArray = jsonReviewsObject.getJSONArray(TMDB_RESULTS);

        for (int i = 0; i < jsonReviewsArray.length() - 1; i++) {
            String content;
            String author;

            JSONObject reviewObject = jsonReviewsArray.getJSONObject(i);

            // Get content.
            content = reviewObject.getString(TMDB_CONTENT);
            // Get author.
            author = reviewObject.getString(TMDB_AUTHOR);

            // Instantiate review object.
            Review currentReview = new Review(content, author);

            // Add review object to ArrayList.
            reviewArrayList.add(currentReview);
        }
        return reviewArrayList;
    }
}
