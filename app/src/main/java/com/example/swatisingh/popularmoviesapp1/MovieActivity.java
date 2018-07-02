package com.example.swatisingh.popularmoviesapp1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.swatisingh.popularmoviesapp1.Model.Movie;
import com.example.swatisingh.popularmoviesapp1.adapters.MovieAdapter;
import com.example.swatisingh.popularmoviesapp1.utilities.NetworkUtils;
import com.example.swatisingh.popularmoviesapp1.utilities.TheMovieDBJsonUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.w3c.dom.Text;

import java.net.URL;
import java.util.List;

import static com.example.swatisingh.popularmoviesapp1.R.menu;

public class MovieActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;
    private GridLayoutManager mLayoutManager;
    private boolean button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mLayoutManager = new GridLayoutManager(MovieActivity.this, 2);
        mMovieAdapter = new MovieAdapter(this, (MovieAdapter.MovieAdapterOnClickHandler) this);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movies);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mMovieAdapter);

        button = true;
        loadMoviesData();
    }
//          String url = "https://api.themoviedb.org/3/movie/550?api_key=0c49a00bd4eaa7eddaeb3f9c924637c3\n";
//        AsyncHttpClient client = new AsyncHttpClient();
//        client.get(url, JsonHttpResponseHandler(){})
     public boolean connect() {

         ConnectivityManager coMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
         NetworkInfo info = coMgr.getActiveNetworkInfo();

         if (info != null && info.isConnected()) {
             return true;
         } else {
             return false;
         }
     }

         private void loadMoviesData(){
             if(connect()) {
                 new FetchMoviesData().execute();
             }
             else
             {
                 showErrorMessage();
             }
         }

         @Override
         public boolean onCreateOptionsMenu(Menu menu) {
             MenuInflater inflater = getMenuInflater();
             inflater.inflate(R.menu.main,menu);
             return true;
         }

         @Override
         public boolean onOptionsItemSelected(MenuItem item){
            int id = item.getItemId();
            if(id == R.id.action_popular){
                button = true;
                mMovieAdapter.setMovieData(null);
                loadMoviesData();
                setTitle(getString(R.string.app_name));
                return true;
            }

            else if (id == R.id.action_top_rated){
                button = false;
                mMovieAdapter.setMovieData(null);
                loadMoviesData();
                setTitle(getString(R.string.top_rated_movies));
                return true;
            }

//            else if (id == R.id.action_upcoming){
//                button = false;
//                mMovieAdapter.setMovieData(null);
//                loadMoviesData();
//                setTitle(getString(R.string.upcoming_movies));
//                return true;
//            }

//            else if (id == R.id.action_now_playing){
//                button = false;
//                mMovieAdapter.setMovieData(null);
//                loadMoviesData();
//                setTitle(getString(R.string.now_playing_movies));
//                return true;
//            }

            return super.onOptionsItemSelected(item);
            }

            @Override
            public void onStart(){
                super.onStart();
            }

            private void showMoviesDataView(){
                mErrorMessageDisplay.setVisibility(View.INVISIBLE);
                mRecyclerView.setVisibility(View.VISIBLE);
            }

            private void showErrorMessage(){
                mRecyclerView.setVisibility(View.INVISIBLE);
                mErrorMessageDisplay.setVisibility(View.VISIBLE);
            }

    @Override
    public void onClick(String title, String releaseDate, String imageUrl, Double rating, String plot) {
        Class destinationClass = DetailActivity.class;
        Intent intent = new Intent(MovieActivity.this,destinationClass);
//        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//        intent.setClassName(MovieActivity.this, String.valueOf(destinationClass));
        intent.putExtra("title", title);
        intent.putExtra("releaseDate",releaseDate);
        intent.putExtra("imageUrl",imageUrl);
        intent.putExtra("rating",rating);
        intent.putExtra("plot", plot);
        startActivity(intent);
    }

    public class FetchMoviesData extends AsyncTask<Void,Void,List<Movie>>{

                @Override
                protected void onPreExecute(){
                   super.onPreExecute();
                   mLoadingIndicator.setVisibility(View.VISIBLE);
                }

                @Override
                protected List<Movie> doInBackground(Void... voids) {

                    URL moviesRequestUrl;
                    if (button) {
                        moviesRequestUrl = NetworkUtils.buildPopularUrl();
                    }
//                    else if (button) {
//                        moviesRequestUrl = NetworkUtils.buildNowPlaying();
//                    } else if (button) {
//                        moviesRequestUrl = NetworkUtils.buildUpcoming();

                    else {
                        moviesRequestUrl = NetworkUtils.buildTopRatedUrl();
                    }

                    try {
                        String jsonMoviesResponse = NetworkUtils.getResponseFromHttpUrl(moviesRequestUrl);
                        return TheMovieDBJsonUtils
                                .getJson(MovieActivity.this, jsonMoviesResponse);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }

                    @Override
                    protected void onPostExecute (List < Movie > movieData) {
                        mLoadingIndicator.setVisibility(View.INVISIBLE);
                        if (!movieData.isEmpty() && connect()) {
                            showMoviesDataView();
                            mMovieAdapter.setMovieData(movieData);
                        } else {
                            showErrorMessage();
                        }
                    }

                }

}
