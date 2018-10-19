package com.example.android.popularmovies;

import java.io.Serializable;

/**
 * A {@link Movie} object contains information related to a movie.
 */
public class Movie implements Serializable {

    /**
     * Title of the movie
     */
    private final String mTitle;

    /**
     * Release date of the movie
     */
    private final String mReleaseDate;

    /**
     * Poster image for the movie
     */
    private final String mPosterPath;

    /**
     * Backdrop image for the movie
     */
    private final String mBackdropPath;

    /**
     * Overview of the movie
     */
    private final String mOverview;

    /**
     * Voter average for the movie
     */
    private final Double mVoteAverage;

    /**
     * Constructs a new {@link Movie} object.
     *
     * @param title       is the title of the movie.
     * @param releaseDate is the release date of the movie.
     * @param posterPath is the poster for the movie.
     * @param backdropPath is the backdrop for the movie.
     * @param overview    is the overview of the movie.
     * @param voteAverage is the voter average for the movie.
     */
    public Movie(String title, String releaseDate, String posterPath, String backdropPath, String overview, Double voteAverage) {
        mTitle = title;
        mReleaseDate = releaseDate;
        mPosterPath = posterPath;
        mBackdropPath = backdropPath;
        mVoteAverage = voteAverage;
        mOverview = overview;
    }

    /**
     * @return the title of the movie.
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * @return the release date of the movie.
     */
    public String getReleaseDate() {
        return mReleaseDate;
    }

    /**
     * @return the poster path for the movie.
     */
    public String getPosterPath() {
        return mPosterPath;
    }

    /**
     * @return the backdrop path for the movie.
     */
    public String getBackdropPath() {
        return mBackdropPath;
    }

    /**
     * @return the voter average for the movie.
     */
    public Double getVoterAverage() {
        return mVoteAverage;
    }

    /**
     * @return the overview of the movie.
     */
    public String getOverview() {
        return mOverview;
    }
}
