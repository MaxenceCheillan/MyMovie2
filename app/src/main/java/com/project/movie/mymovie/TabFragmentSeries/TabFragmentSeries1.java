package com.project.movie.mymovie.TabFragmentSeries;

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

import com.project.movie.mymovie.ApiService.SerieApiService;
import com.project.movie.mymovie.DetailActivity.TvDetailActivity;
import com.project.movie.mymovie.R;
import com.project.movie.mymovie.model.SeasonModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Maxence Cheillan on 06/06/2016.
 */
public class TabFragmentSeries1 extends Fragment {

    private SeriesAdapter mAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.tab_fragment_2, container, false);

        final FragmentActivity c = getActivity();
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.serieRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        recyclerView.setLayoutManager(layoutManager);

        new Thread(new Runnable() {
            @Override
            public void run() {
                mAdapter = new SeriesAdapter(getContext());
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(mAdapter);

                        getOnTheAirTvSeries();

                    }
                });
            }
        }).start();

        return view;
    }

    public static class SerieViewHolder extends RecyclerView.ViewHolder {
        public ImageView posterPath;
        public TextView title;
        public TextView releaseDate;
        public TextView description;

        public SerieViewHolder(View itemView) {
            super(itemView);
            posterPath = (ImageView) itemView.findViewById(R.id.posterPath);
            title = (TextView) itemView.findViewById(R.id.title);
            releaseDate = (TextView) itemView.findViewById(R.id.releaseDate);
            description = (TextView) itemView.findViewById(R.id.overview);
        }
    }

    public static class SeriesAdapter extends RecyclerView.Adapter<SerieViewHolder> {
        private List<SeasonModel> mSerieList;
        private LayoutInflater mInflater;
        private Context mContext;

        public SeriesAdapter(Context context) {
            this.mContext = context;
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public SerieViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
            View view = mInflater.inflate(R.layout.row, parent, false);
            final SerieViewHolder viewHolder = new SerieViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = viewHolder.getAdapterPosition();
                    Intent intent = new Intent(mContext, TvDetailActivity.class);
                    intent.putExtra(TvDetailActivity.EXTRA_TV, mSerieList.get(position));
                    mContext.startActivity(intent);
                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(SerieViewHolder holder, int position) {
            SeasonModel serie = mSerieList.get(position);
            Picasso.with(mContext)
                    .load(serie.getPoster())
                    .placeholder(R.color.colorAccent)
                    .into(holder.posterPath);
            holder.title.setText(serie.getTitle());
            holder.releaseDate.setText(serie.getAirDate());
            holder.description.setText(serie.getDescription());
        }

        @Override
        public int getItemCount() {
            return (mSerieList == null) ? 0 : mSerieList.size();
        }

        public void setTvList(List<SeasonModel> serieList) {
            this.mSerieList = new ArrayList<>();
            this.mSerieList.addAll(serieList);
            notifyDataSetChanged();
        }
    }

    private void getOnTheAirTvSeries() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key", "0d1d0cc3c4aec9ca1c2c8c9e781a7ef1");
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        SerieApiService service = restAdapter.create(SerieApiService.class);
        service.getOnTheAirTvSeries(new Callback<SeasonModel.SeasonResult>() {
            @Override
            public void success(SeasonModel.SeasonResult tvResult, Response response) {
                mAdapter.setTvList(tvResult.getResults());
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }
}