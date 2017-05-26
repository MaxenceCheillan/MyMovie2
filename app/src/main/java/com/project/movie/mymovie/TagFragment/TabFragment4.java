package com.project.movie.mymovie.TagFragment;

/**
 * Created by Maxence Cheillan on 01/06/2016.
 */
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.movie.mymovie.ApiService.MovieApiService;
import com.project.movie.mymovie.DetailActivity.MovieDetailActivity;
import com.project.movie.mymovie.R;
import com.project.movie.mymovie.model.MovieModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TabFragment4 extends Fragment {

    private MoviesAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.tab_fragment_1, container, false);

        final FragmentActivity c = getActivity();
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.movieRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        recyclerView.setLayoutManager(layoutManager);

        new Thread(new Runnable() {
            @Override
            public void run() {
                mAdapter = new MoviesAdapter(getContext());
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(mAdapter);

                        getTopRatedMovies();

                    }
                });
            }
        }).start();

        return view;
    }

    private void getTopRatedMovies() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key", "0d1d0cc3c4aec9ca1c2c8c9e781a7ef1");
                        request.addEncodedQueryParam("&language","fr-FR");

                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        MovieApiService service = restAdapter.create(MovieApiService.class);
        service.getTopRatedMovies(new Callback<MovieModel.MovieResult>() {
            @Override
            public void success(MovieModel.MovieResult movieResult, Response response) {
                mAdapter.setMovieList(movieResult.getResults());
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        public ImageView posterPath;
        public TextView title;
        public TextView releaseDate;
        public TextView description;

        public MovieViewHolder(View itemView) {
            super(itemView);
            posterPath = (ImageView) itemView.findViewById(R.id.posterPath);
            title = (TextView) itemView.findViewById(R.id.title);
            releaseDate = (TextView) itemView.findViewById(R.id.releaseDate);
            description = (TextView) itemView.findViewById(R.id.overview);
        }
    }

    public static class MoviesAdapter extends RecyclerView.Adapter<MovieViewHolder> {
        private List<MovieModel> mMovieList;
        private LayoutInflater mInflater;
        private Context mContext;

        public MoviesAdapter(Context context) {
            this.mContext = context;
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public MovieViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
            View view = mInflater.inflate(R.layout.row, parent, false);
            final MovieViewHolder viewHolder = new MovieViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = viewHolder.getAdapterPosition();
                    Intent intent = new Intent(mContext, MovieDetailActivity.class);
                    intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, mMovieList.get(position));
                    mContext.startActivity(intent);
                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MovieViewHolder holder, int position) {
            MovieModel movie = mMovieList.get(position);
            Picasso.with(mContext)
                    .load(movie.getPoster())
                    .placeholder(R.color.colorAccent)
                    .into(holder.posterPath);
            holder.title.setText(movie.getTitle());
            holder.releaseDate.setText(movie.getRelease_date());
            holder.description.setText(movie.getDescription());
        }

        @Override
        public int getItemCount() {
            return (mMovieList == null) ? 0 : mMovieList.size();
        }

        public void setMovieList(List<MovieModel> movieList) {
            this.mMovieList = new ArrayList<>();
            this.mMovieList.addAll(movieList);
            notifyDataSetChanged();
        }
    }
}

