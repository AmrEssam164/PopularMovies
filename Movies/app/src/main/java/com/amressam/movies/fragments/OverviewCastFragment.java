package com.amressam.movies.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.amressam.movies.Cast;
import com.amressam.movies.CastRecyclerViewAdapter;
import com.amressam.movies.MovieDetialsActivity;
import com.amressam.movies.R;
import com.amressam.movies.databinding.OverviewCastFragmentBinding;

import java.util.ArrayList;


public class OverviewCastFragment extends Fragment {
    OverviewCastFragmentBinding mOverviewCastFragmentBinding;
    CastRecyclerViewAdapter mCastRecyclerViewAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mOverviewCastFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.overview_cast_fragment,container,false);
        View view = mOverviewCastFragmentBinding.getRoot();
        setOverview();
        setCastRecyclerViewAdapter();
        return view;
    }


    private void setOverview(){
        mOverviewCastFragmentBinding.rateProgress.setMax(10);
        mOverviewCastFragmentBinding.rateProgress.incrementProgressBy(MovieDetialsActivity.movieItem.getRate());
        mOverviewCastFragmentBinding.rateNumber.setText(MovieDetialsActivity.movieItem.getRate()+".0");
        mOverviewCastFragmentBinding.voteCount.setText("From "+ MovieDetialsActivity.movieItem.getVote_count()+" people");
        mOverviewCastFragmentBinding.date.setText(MovieDetialsActivity.movieItem.getReleaseDate());
        mOverviewCastFragmentBinding.overview.setText(MovieDetialsActivity.movieItem.getOverView());
    }
    private void setCastRecyclerViewAdapter(){
        mCastRecyclerViewAdapter = new CastRecyclerViewAdapter(new ArrayList<Cast>(),getContext());
        mOverviewCastFragmentBinding.castRecyclerView.setAdapter(mCastRecyclerViewAdapter);
        mCastRecyclerViewAdapter.loadNewData(MovieDetialsActivity.cast);
    }


}
