package com.example.android.popularmovies.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * A {@link Movie} object contains information related to a movie.
 */
@Entity(tableName = "favorites_table")
public class Movie implements Serializable {

    /**
     * Id of the movie
     */
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private final int mId;

    /**
     * Title of the movie
     */
    @NonNull
    @ColumnInfo(name = "title")
    private final String mTitle;

    /**
     * Release date of the movie
     */
    @NonNull
    @ColumnInfo(name = "release_date")
    private final String mReleaseDate;

    /**
     * Poster image for the movie
     */
    @NonNull
    @ColumnInfo(name = "poster_path")
    private final String mPosterPath;

    /**
     * Backdrop image for the movie
     */
    @NonNull
    @ColumnInfo(name = "backdrop_path")
    private final String mBackdropPath;

    /**
     * Overview of the movie
     */
    @NonNull
    @ColumnInfo(name = "overview")
    private final String mOverview;

    /**
     * Voter average for the movie
     */
    @NonNull
    @ColumnInfo(name = "vote_average")
    private final double mVoteAverage;

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
    public Movie(int id, String title, String releaseDate, String posterPath, String backdropPath,
                 String overview, double voteAverage) {
        mId = id;
        mTitle = title;
        mReleaseDate = releaseDate;
        mPosterPath = posterPath;
        mBackdropPath = backdropPath;
        mVoteAverage = voteAverage;
        mOverview = overview;
    }

    /**
     * @return the id of the movie.
     */
    public int getId() {
        return mId;
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
    public double getVoteAverage() {
        return mVoteAverage;
    }

    /**
     * @return the overview of the movie.
     */
    public String getOverview() {
        return mOverview;
    }
}
