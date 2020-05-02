package com.amressam.movies.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.amressam.movies.fragments.OverviewCastFragment;
import com.amressam.movies.fragments.ReviewFragment;
import com.amressam.movies.fragments.TrailerFragment;

public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    public SectionsPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new OverviewCastFragment();
                break;
            case 1:
                fragment = new TrailerFragment();
                break;
            case 2:
                fragment = new ReviewFragment();
                break;
            default:
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title="";
        switch (position) {
            case 0:
                title = "Overview&Cast";
                break;
            case 1:
                title = "Trailers";
                break;
            case 2:
                title = "Reviews";
                break;
            default:
                break;
        }
        return title;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
