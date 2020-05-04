package com.amressam.movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewRecyclerViewAdapter extends RecyclerView.Adapter<ReviewRecyclerViewAdapter.ViewHolder> {


    private ArrayList<com.amressam.movies.Reviews> mReviewList;

    private Context mContext;




    public ReviewRecyclerViewAdapter(ArrayList<com.amressam.movies.Reviews> castList, Context context) {
        mReviewList = castList;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //called by the layout manager when it needs a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        // called by the layout manager when it wants new data in an existing row
        final com.amressam.movies.Reviews reviewItem = mReviewList.get(position);
        holder.author.setText(reviewItem.getAuthor());
        holder.content.setText(reviewItem.getReview());
    }

    @Override
    public int getItemCount() {
        return ((mReviewList != null) && (mReviewList.size() != 0) ? mReviewList.size() : 0);
    }


    public void loadNewData(ArrayList<com.amressam.movies.Reviews> newReview) {
        this.mReviewList = newReview;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView author;
        public TextView content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
                this.author = (TextView) itemView.findViewById(R.id.review_author);
                this.content = (TextView) itemView.findViewById(R.id.review_content);
        }

    }
}
