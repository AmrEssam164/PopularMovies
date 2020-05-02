package com.amressam.movies.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.amressam.movies.MovieDetialsActivity;
import com.amressam.movies.R;
import com.amressam.movies.ReviewRecyclerViewAdapter;
import com.amressam.movies.Reviews;
import com.amressam.movies.databinding.ReviewFragmentBinding;

import java.util.ArrayList;


public class ReviewFragment extends Fragment {
    ReviewRecyclerViewAdapter mReviewRecyclerViewAdapter;
    ReviewFragmentBinding mReviewFragmentBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mReviewFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.review_fragment,container,false);
        View view = mReviewFragmentBinding.getRoot();
        return view;
    }

    private void setReviewRecyclerViewAdapter(){
        mReviewRecyclerViewAdapter = new ReviewRecyclerViewAdapter(new ArrayList<Reviews>(),getContext());
        mReviewFragmentBinding.reviewsRecyclerview.setAdapter(mReviewRecyclerViewAdapter);
        mReviewRecyclerViewAdapter.loadNewData(MovieDetialsActivity.reviews);
    }

}
