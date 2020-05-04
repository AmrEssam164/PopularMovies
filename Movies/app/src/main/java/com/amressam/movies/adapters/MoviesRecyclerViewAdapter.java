package com.amressam.movies;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MoviesRecyclerViewAdapter extends RecyclerView.Adapter<MoviesRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "MoviesRecyclerViewAdapt";

    private ArrayList<com.amressam.movies.Movie> mMovies;

    private Context mContext;




    public MoviesRecyclerViewAdapter(ArrayList<com.amressam.movies.Movie> movies,Context context) {
        mMovies=movies;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //called by the layout manager when it needs a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_movies, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final com.amressam.movies.Movie movieItem = mMovies.get(position);
        Picasso.get().load("http://image.tmdb.org/t/p/w500/"+movieItem.getImage())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.thumbnail);
        String date = movieItem.getReleaseDate();
        String year = String.valueOf(date.charAt(0)) + String.valueOf(date.charAt(1)) + String.valueOf(date.charAt(2)) + String.valueOf(date.charAt(3));
        holder.date.setText(year);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, com.amressam.movies.MovieDetialsActivity.class);
                intent.putExtra("NIDO", (Serializable) movieItem);
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(holder.thumbnail, "thumbnail");
                pairs[1] = new Pair<View, String>(holder.date, "date");
                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext, pairs);
                mContext.startActivity(intent, activityOptions.toBundle());
            }
        };
        holder.thumbnail.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        if (null == mMovies) return 0;
        return mMovies.size();
    }

    void loadNewData(ArrayList<com.amressam.movies.Movie> movies) {
        this.mMovies = movies;
        notifyDataSetChanged();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;
        public CircleImageView circleImageView;
        public TextView title;
        public TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            this.title = (TextView) itemView.findViewById(R.id.title);
            this.date = (TextView) itemView.findViewById(R.id.date);
        }

    }
}
