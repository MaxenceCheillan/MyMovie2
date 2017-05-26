package com.project.movie.mymovie.DetailActivity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.movie.mymovie.R;
import com.project.movie.mymovie.model.SeasonModel;
import com.squareup.picasso.Picasso;


/**
 * Created by Maxence Cheillan on 06/06/2016.
 */

public class TvDetailActivity extends AppCompatActivity {
    public static final String EXTRA_TV = "tvseries";

    private SeasonModel mTv;
    ImageView backdrop;
    ImageView poster;
    TextView title;
    TextView description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        if (getIntent().hasExtra(EXTRA_TV)) {
            mTv = getIntent().getParcelableExtra(EXTRA_TV);
        } else {
            throw new IllegalArgumentException("Detail activity must receive a movie parcelable");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitle(mTv.getTitle());

        backdrop = (ImageView) findViewById(R.id.backdrop);
        title = (TextView) findViewById(R.id.movie_title);
        description = (TextView) findViewById(R.id.movie_description);
        poster = (ImageView) findViewById(R.id.movie_poster);

        title.setText(mTv.getTitle());
        description.setText(mTv.getDescription());
        Picasso.with(this)
                .load(mTv.getPoster())
                .into(poster);
        Picasso.with(this)
                .load(mTv.getBackdrop())
                .into(backdrop);
    }
}