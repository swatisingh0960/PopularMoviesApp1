package com.example.swatisingh.popularmoviesapp1.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.support.v7.widget.RecyclerView;


import com.example.swatisingh.popularmoviesapp1.Model.Movie;
import com.example.swatisingh.popularmoviesapp1.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder>{

    private List<Movie> mMovieList;
    Context mContext;
    private final MovieAdapterOnClickHandler mClickHandler;


    public MovieAdapter(Context context, MovieAdapterOnClickHandler clickHandler) {
        mContext = context;
        mClickHandler=clickHandler;
    }

    public interface MovieAdapterOnClickHandler
    {
        void onClick(String title, String releaseDate, String imageUrl, Double rating, String plot);
    }

        public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            public final ImageView mMovieImageView;

            public MovieAdapterViewHolder(View view)
            {
                super(view);
                mMovieImageView= (ImageView) view.findViewById(R.id.iv_movie);
                view.setOnClickListener(this);
            }

            @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Movie movie = mMovieList.get(adapterPosition);
            String mTitle = movie.getmTitle();
            String mReleaseDate = movie.getmReleaseDate();
            String mImageUrl = movie.getmImageUrl();
            Double mRating = movie.getmRating();
            String mPlot = movie.getmPlot();

            mClickHandler.onClick(mTitle,mReleaseDate,mImageUrl,mRating,mPlot);
        }
    }

        @Override
        public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movies_list_item;
            LayoutInflater inflater = LayoutInflater.from(context);
            boolean shouldAttachToParentImmediately = false;

            View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
            return new MovieAdapterViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MovieAdapterViewHolder holder, int position){
            Movie movie = mMovieList.get(position);
            String imageName = movie.getmImageUrl();
            imageName = imageName.replace("/", "");

            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https")
                    .authority("image.tmdb.org")
                    .appendPath("t")
                    .appendPath("p")
                    .appendPath("w185")
                    .appendPath(imageName);

            String url = builder.build().toString();
            Picasso.with(mContext)
                    .load(url)
                    .into(holder.mMovieImageView);
        }

        @Override
        public int getItemCount(){
            if(mMovieList == null) {
                return 0;
            }
            return mMovieList.size();
        }

        public void setMovieData(List<Movie> movieList){
            mMovieList = movieList;
            notifyDataSetChanged();
        }

}

