package com.project.movie.mymovie.DetailActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.movie.mymovie.R;
import com.project.movie.mymovie.model.MovieModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by Maxence Cheillan on 02/06/2016.
 */

public class MovieDetailActivity extends AppCompatActivity {
    public static  String EXTRA_MOVIE = "movie";
    public static final String TAG = "TAG";

    private static MovieModel mMovie;
    ImageView backdrop;
    ImageView poster;
    TextView title;
    TextView description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        if (getIntent().hasExtra(EXTRA_MOVIE)) {
            mMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
            Log.d("test", String.valueOf(mMovie.getId()));
        } else {
            throw new IllegalArgumentException("Detail activity must receive a movie parcelable");
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitle(mMovie.getTitle());




        backdrop = (ImageView) findViewById(R.id.backdrop);
        title = (TextView) findViewById(R.id.movie_title);
        description = (TextView) findViewById(R.id.movie_description);
        poster = (ImageView) findViewById(R.id.movie_poster);

        title.setText(mMovie.getTitle());
        description.setText(mMovie.getRelease_date());

        Picasso.with(this)
                .load(mMovie.getPoster())
                .into(poster);
        Picasso.with(this)
                .load(mMovie.getBackdrop())
                .into(backdrop);
/*
        OkHttpClient okHttpClient = new OkHttpClient();
        Request myGetRequest = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/" + mMovie.getId() + "?language=fr-FR&api_key=0d1d0cc3c4aec9ca1c2c8c9e781a7ef1")
                .build();

        okHttpClient.newCall(myGetRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //le retour est effectué dans un thread différent
                Log.d("OkHTTPclient response", "la reponse de mon body est :" + response.body().string());
                final int statusCode = response.code();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });
*/

        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                OkHttpClient client = new OkHttpClient();

                MediaType mediaType = MediaType.parse("application/octet-stream");
                RequestBody body = RequestBody.create(mediaType, "{}");
                Request request = new Request.Builder()
                        .url("https://api.themoviedb.org/3/movie/" + mMovie.getId() + "?language=fr-FR&api_key=0d1d0cc3c4aec9ca1c2c8c9e781a7ef1")
                        .get()
                        .build();

                try {
                    okhttp3.Response response = client.newCall(request).execute();
                    Log.d("OkHTTPclient response", "la reponse de mon body est :" + response.body().string());
                } catch (IOException e) {
                    e.getMessage();
                }
                return null;
            }
        }.execute();
    }
}
