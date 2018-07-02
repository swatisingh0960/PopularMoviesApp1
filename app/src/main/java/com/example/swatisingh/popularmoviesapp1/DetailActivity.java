package com.example.swatisingh.popularmoviesapp1;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.swatisingh.popularmoviesapp1.Model.Movie;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class DetailActivity extends AppCompatActivity {

    private ImageView movieImage;
    private TextView titleTextView;
    private TextView ratingBar;
    private TextView releaseTextView;
    private TextView plotTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        String title = intent.getStringExtra("title");
        String releaseDate = intent.getStringExtra("releaseDate");
        String imageUrl = intent.getStringExtra("imageUrl");
        imageUrl = imageUrl.replace("/", "");
        String rating = String.valueOf(intent.getDoubleExtra("rating", 0.0));
        String plot = intent.getStringExtra("plot");

        movieImage = (ImageView)findViewById(R.id.iv_details_image);
        titleTextView=(TextView) findViewById(R.id.tv_detais_title);
        ratingBar = (TextView) findViewById(R.id.rbRating);

        releaseTextView = (TextView)findViewById(R.id.tv_details_release);
        plotTextView = (TextView)findViewById(R.id.tv_details_plot);


        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("image.tmdb.org")
                .appendPath("t")
                .appendPath("p")
                .appendPath("w185")
                .appendPath(imageUrl);

        String url = builder.build().toString();
        Picasso.with(this)
                .load(url)
                .into(movieImage);

        titleTextView.setText(title);
//        ratingBar.setNumStars(5);
        ratingBar.setText(rating);
        releaseTextView.setText(releaseDate);
        plotTextView.setText(plot);
    }
}
