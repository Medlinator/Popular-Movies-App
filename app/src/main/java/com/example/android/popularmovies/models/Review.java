package com.example.android.popularmovies.models;

/**
 * A {@link Review} object contains reviews relate4d to a movie.
 */
public class Review {

    /**
     * Content of the review.
     */
    private final String mContent;

    /**
     * Author of the review.
     */
    private final String mAuthor;

    /**
     * Constructs a new {@link Review} object.
     *
     * @param content is the content of the review.
     * @param author is the author of the review.
     */
    public Review(String content, String author) {
        mContent = content;
        mAuthor = author;
    }

    /**
     * @return the content of the review.
     */
    public String getContent() { return mContent; }

    /**
     * @return the author of the review.
     */
    public String getAuthor() { return mAuthor; }
}
