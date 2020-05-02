package com.amressam.movies.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.amressam.movies.MovieDetialsActivity;
import com.amressam.movies.R;
import com.amressam.movies.Trailer;
import com.amressam.movies.TrailerRecyclerViewAdapter;
import com.amressam.movies.YoutubeActivity;
import com.amressam.movies.databinding.TrailerFragmentBinding;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

import java.util.ArrayList;
import java.util.Objects;

public class TrailerFragment extends Fragment implements TrailerRecyclerViewAdapter.AdapterOnClickHandler {
    TrailerRecyclerViewAdapter mTrailerRecyclerViewAdapter;
    TrailerFragmentBinding mTrailerFragmentBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mTrailerFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.trailer_fragment,container,false);
        View view = mTrailerFragmentBinding.getRoot();
        setTrailerRecyclerViewAdapter();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onTrailerClicked(String video_id) {
        Intent intent = null;
        intent = YouTubeStandalonePlayer.createVideoIntent(Objects.requireNonNull(getActivity()), YoutubeActivity.GOOGLE_API_KEY, video_id, 0, true, true);
        if (intent != null) {
            startActivity(intent);
        }
    }

    private void setTrailerRecyclerViewAdapter(){
        mTrailerRecyclerViewAdapter = new TrailerRecyclerViewAdapter(new ArrayList<Trailer>(),getContext(),this);
        mTrailerFragmentBinding.trailersRecyclerview.setAdapter(mTrailerRecyclerViewAdapter);
        mTrailerRecyclerViewAdapter.loadNewData(MovieDetialsActivity.trailers);
    }

}
