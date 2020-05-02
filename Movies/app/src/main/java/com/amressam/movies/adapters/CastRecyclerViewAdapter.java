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
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CastRecyclerViewAdapter extends RecyclerView.Adapter<CastRecyclerViewAdapter.ViewHolder> {


    private List<Cast> mCastList;

    private Context mContext;




    public CastRecyclerViewAdapter(List<Cast> castList, Context context) {
        mCastList = castList;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //called by the layout manager when it needs a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cast, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        // called by the layout manager when it wants new data in an existing row
        final Cast castItem = mCastList.get(position);
        Picasso.get().load(castItem.getImage())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.circleImageView);
        holder.name.setText(castItem.getName());

    }

    @Override
    public int getItemCount() {
        return ((mCastList != null) && (mCastList.size() != 0) ? mCastList.size() : 0);
    }


    public void loadNewData(List<Cast> newCast) {
        this.mCastList = newCast;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public CircleImageView circleImageView;
        public TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
                this.circleImageView = (CircleImageView) itemView.findViewById(R.id.thumbnail3);
                this.name = (TextView) itemView.findViewById(R.id.cast_name);
        }

    }
}
