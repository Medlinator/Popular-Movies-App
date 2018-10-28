package com.example.android.popularmovies.models;

/**
 * A {@link Trailer} object contains trailers related to a movie.
 */
public class Trailer {

    /**
     * Name of the trailer.
     */
    private final String mName;

    /**
     * Key for the trailer.
     */
    private final String mKey;

    /**
     * Constructs a new {@link Trailer} object.
     *
     * @param name is the name of the trailer.
     * @param key is the key for the trailer.
     */
    public Trailer(String name, String key) {
        mName = name;
        mKey = key;
    }

    /**
     * @return the name of the trailer.
     */
    public String getName() { return mName; }

    /**
     * @return the key for the trailer.
     */
    public String getKey() { return mKey; }
}
