package com.example.swatisingh.popularmoviesapp1.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String KEY_PARAM = "api_key";
    private static final String LANGUAGE_PARAM = "language";
    private static final String PAGE_PARAM = "page";


    private static final String apikey = "YOUR_MOVIE_DB_API_KEY"; //Please enter your TMDB (The Movie DB Api key here to run the App)
    private static final String language = "en-US";
    private static final String page = "1";


    public static URL buildPopularUrl(){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath("popular")
                .appendQueryParameter(KEY_PARAM, apikey)
                .appendQueryParameter(LANGUAGE_PARAM,language)
                .appendQueryParameter(PAGE_PARAM, page);

        URL url = null;
        try{
            url = new URL(builder.build().toString());
        }
        catch (MalformedURLException me){
            me.printStackTrace();
        }
        return url;
    }

    public static URL buildTopRatedUrl(){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath("top_rated")
                .appendQueryParameter(KEY_PARAM, apikey)
                .appendQueryParameter(LANGUAGE_PARAM, language)
                .appendQueryParameter(PAGE_PARAM, page);

        URL url = null;
        try{
            url = new URL(builder.build().toString());
        }
        catch (MalformedURLException me){
            me.printStackTrace();
        }
        return url;
    }

    public static URL buildUpcoming(){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath("upcoming")
                .appendQueryParameter(KEY_PARAM, apikey)
                .appendQueryParameter(LANGUAGE_PARAM, language)
                .appendQueryParameter(PAGE_PARAM, page);
        URL url = null;
        try{
            url = new URL(builder.build().toString());
        }
        catch (MalformedURLException me){
            me.printStackTrace();
        }
        return url;
    }

    public static URL buildNowPlaying(){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath("now_playing")
                .appendQueryParameter(KEY_PARAM, apikey)
                .appendQueryParameter(LANGUAGE_PARAM,language)
                .appendQueryParameter(PAGE_PARAM,page);
        URL url = null;
        try{
            url = new URL(builder.build().toString());
        }
        catch (MalformedURLException me){
            me.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException{
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try{
            InputStream inputStream = urlConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput){
                return scanner.next();
            }else {
                return null;
            }
        }finally {
            urlConnection.disconnect();
        }
    }

}
