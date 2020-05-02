package com.amressam.movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TrailerRecyclerViewAdapter extends RecyclerView.Adapter<TrailerRecyclerViewAdapter.ViewHolder> {


    private List<com.amressam.movies.Trailer> mTrailerList;

    private Context mContext;


    private final TrailerRecyclerViewAdapter.AdapterOnClickHandler mClickHandler;

    public interface AdapterOnClickHandler {
        void onTrailerClicked(String video_id);
    }


    public TrailerRecyclerViewAdapter(List<com.amressam.movies.Trailer> castList, Context context, TrailerRecyclerViewAdapter.AdapterOnClickHandler clickHandler) {
        mTrailerList = castList;
        mContext = context;
        mClickHandler=clickHandler;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //called by the layout manager when it needs a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_trailers, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        // called by the layout manager when it wants new data in an existing row
        final com.amressam.movies.Trailer trailerItem = mTrailerList.get(position);
        Picasso.get().load(trailerItem.getImage())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.trailer_photo);
        holder.name.setText(trailerItem.getTitle());

    }

    @Override
    public int getItemCount() {
        return ((mTrailerList != null) && (mTrailerList.size() != 0) ? mTrailerList.size() : 0);
    }


    public void loadNewData(List<com.amressam.movies.Trailer> newTrailer) {
        this.mTrailerList = newTrailer;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView trailer_photo;
        public TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
                this.trailer_photo = (ImageView) itemView.findViewById(R.id.trailer_thumbnail);
                this.name = (TextView) itemView.findViewById(R.id.trailer_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String id = mTrailerList.get(adapterPosition).getTrailer_id();
            mClickHandler.onTrailerClicked(id);
        }
    }
}
