package com.example.android.popularmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.models.Trailer;

import java.util.ArrayList;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder> {

    private Context context;
    private ArrayList<Trailer> mTrailerData;

    /**
     * Constructor for our adapter.
     */
    public TrailerAdapter(Context context) {
        this.context = context;
    }

    public class TrailerAdapterViewHolder extends RecyclerView.ViewHolder
            implements OnClickListener {

        final TextView mTrailerName;

        /**
         * Constructor for our ViewHolder.
         */
        TrailerAdapterViewHolder(View view) {
            super(view);
            mTrailerName = view.findViewById(R.id.tv_movie_details_trailer_name);
            view.setOnClickListener(this);
        }

        /**
         * This gets called by the child view that was clicked on.
         *
         * @param view The View that was clicked.
         */
        @Override
        public void onClick(View view) {
            String youtubeBaseUrl = "https://youtu.be/";
            int adapterPosition = getAdapterPosition();
            Trailer trailer = mTrailerData.get(adapterPosition);
            String key = trailer.getKey();
            String fullUrl = youtubeBaseUrl + key;

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(fullUrl));
            context.startActivity(intent);
        }
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If your RecyclerView has more than one type of item you can use this
     *                  viewType integer to provide a different layout.
     * @return A new TrailerAdapterViewHolder that holds the View for each list item
     */
    @NonNull
    @Override
    public TrailerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.trailer_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup,
                shouldAttachToParentImmediately);

        return new TrailerAdapterViewHolder(view);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position.
     *
     * @param trailerAdapterViewHolder The ViewHolder which should be updated to represent the
     *                               contents of the item at the given position in the data set.
     * @param position               The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull TrailerAdapterViewHolder trailerAdapterViewHolder,
                                 int position) {
        Trailer trailer = mTrailerData.get(position);
        String trailerName = trailer.getName();

        trailerAdapterViewHolder.mTrailerName.setText(trailerName);
    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes to
     * help layout our Views and for animations.
     *
     * @return The number of items available in our trailers list.
     */
    @Override
    public int getItemCount() {
        if (mTrailerData == null) return 0;
        return mTrailerData.size();
    }

    /**
     * Method that will set new data from the web in the TrailerAdapter without creating a new one.
     *
     * @param trailerData The new review data to be displayed.
     */
    public void setTrailerData(ArrayList<Trailer> trailerData) {
        mTrailerData = trailerData;
        notifyDataSetChanged();
    }
}
