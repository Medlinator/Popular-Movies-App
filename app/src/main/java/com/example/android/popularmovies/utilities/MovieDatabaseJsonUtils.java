package com.example.android.popularmovies.utilities;

import com.example.android.popularmovies.Movie;

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

        JSONArray jsonMoviesArray = jsonMoviesObject.getJSONArray(TMDB_RESULTS);

        for (int i = 0; i < NUM_OF_MOVIES; i++) {
            String title;
            String releaseDate;
            String posterPath;
            String backdropPath;
            String overview;
            Double voteAverage;

            JSONObject movieObject = jsonMoviesArray.getJSONObject(i);
            title = movieObject.getString(TMDB_TITLE);
            releaseDate = movieObject.getString(TMDB_RELEASE_DATE);
            posterPath = movieObject.getString(TMDB_MOVIE_POSTER);
            backdropPath = movieObject.getString(TMDB_MOVIE_BACKDROP);
            overview = movieObject.getString(TMDB_OVERVIEW);
            voteAverage = movieObject.getDouble(TMDB_VOTE_AVERAGE);

            Movie currentMovie = new Movie(title, releaseDate, posterPath, backdropPath, overview, voteAverage);

            movieArrayList.add(currentMovie);
        }

        return movieArrayList;
    }
}
