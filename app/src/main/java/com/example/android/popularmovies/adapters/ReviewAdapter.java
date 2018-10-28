package com.example.android.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.models.Review;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder> {

    private ArrayList<Review> mReviewData;

    /**
     * Constructor for our adapter.
     */
    public ReviewAdapter() {}

    /**
     * Class declaration for ViewHolder. ViewHolder holds a cache of views that will be used and
     * reused for list items.
     */
    public class ReviewAdapterViewHolder extends RecyclerView.ViewHolder {

        final TextView mReviewContent;
        final TextView mReviewAuthor;

        ReviewAdapterViewHolder(View view) {
            super(view);
            mReviewContent = view.findViewById(R.id.tv_movie_details_review_content);
            mReviewAuthor = view.findViewById(R.id.tv_movie_details_review_author);
        }
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType If your RecyclerView has more than one type of item you can use this viewType
     *                 integer to provide a different layout.
     * @return A new ReviewAdapterViewHolder that holds the View for each list item.
     */
    @NonNull
    @Override
    public ReviewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.review_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup,
                shouldAttachToParentImmediately);

        return new ReviewAdapterViewHolder(view);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified position.
     *
     * @param reviewAdapterHolder The ViewHolder which should be updated to represent the contents
     *                            of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ReviewAdapterViewHolder reviewAdapterHolder, int position) {
        Review review = mReviewData.get(position);
        String reviewContent = review.getContent();
        String reviewAuthor = review.getAuthor();

        Log.v("YUUUUH", reviewAuthor + reviewContent);

        reviewAdapterHolder.mReviewContent.setText(reviewContent);
        reviewAdapterHolder.mReviewAuthor.setText(reviewAuthor);
    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes to
     * help layout our Views and for animations.
     *
     * @return The number of items available in our reviews list.
     */
    @Override
    public int getItemCount() {
        if (mReviewData == null) return 0;
        return mReviewData.size();
    }

    /**
     * Method that will set new data from the web in the ReviewAdapter without creating a new one.
     *
     * @param reviewData The new review data to be displayed.
     */
    public void setReviewData(ArrayList<Review> reviewData) {
        mReviewData = reviewData;
        notifyDataSetChanged();
    }
}
