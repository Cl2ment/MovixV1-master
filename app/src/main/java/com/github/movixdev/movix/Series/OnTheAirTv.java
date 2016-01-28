package com.github.movixdev.movix.Series;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.github.movixdev.movix.Movie.MainActivity;
import com.github.movixdev.movix.Movie.UpcomingMovie;
import com.github.movixdev.movix.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Maxence cheillan on 24/01/2016.
 */
public class OnTheAirTv extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private TvAdapter mAdapter;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab1,fab2,fab3;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(
                R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab1 = (FloatingActionButton)findViewById(R.id.fab1);
        fab2 = (FloatingActionButton)findViewById(R.id.fab2);
        fab3= (FloatingActionButton) findViewById(R.id.fab3);


        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);

        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new TvAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        getOnTheAirTvSeries();
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
        TvApiService service = restAdapter.create(TvApiService.class);
        service.getOnTheAirTvSeries(new Callback<Tv.TvResult>() {
            @Override
            public void success(Tv.TvResult tvResult, Response response) {
                mAdapter.setTvList(tvResult.getResults());
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.action_settings2:
                Intent intent = new Intent(this, MainActivity.class);
                this.startActivity(intent);
                return true;

            case R.id.action_settings3:
                Intent intent1 = new Intent(this, UpcomingMovie.class);
                this.startActivity(intent1);
                return true;

            case R.id.action_settings:
                Intent intent2= new Intent(this, PopularTvSeries.class);
                this.startActivity(intent2);
                return true;


            default:

                return super.onOptionsItemSelected(item);
        }
    }
    public static class TvViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TvViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
    public static class TvAdapter extends RecyclerView.Adapter<TvViewHolder> {
        private List<Tv> mTvList;
        private LayoutInflater mInflater;
        private Context mContext;

        public TvAdapter(Context context) {
            this.mContext = context;
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public TvViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
            View view = mInflater.inflate(R.layout.row_movie, parent, false);
            final TvViewHolder viewHolder = new TvViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = viewHolder.getAdapterPosition();
                    Intent intent = new Intent(mContext, TvDetailActivity.class);
                    intent.putExtra(TvDetailActivity.EXTRA_TV, mTvList.get(position));
                    mContext.startActivity(intent);
                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(TvViewHolder holder, int position) {
            Tv tv = mTvList.get(position);
            Picasso.with(mContext)
                    .load(tv.getPoster())
                    .placeholder(R.color.colorAccent)
                    .into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return (mTvList == null) ? 0 : mTvList.size();
        }

        public void setTvList(List<Tv> TvList) {
            this.mTvList = new ArrayList<>();
            this.mTvList.addAll(TvList);
            notifyDataSetChanged();
        }
    }


    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.fab:
                animateFAB();
                break;
            case R.id.fab1:
                mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                mRecyclerView.setLayoutManager(new GridLayoutManager(this,1));
                mAdapter = new TvAdapter(this);
                mRecyclerView.setAdapter(mAdapter);
                getOnTheAirTvSeries();
                break;
            case R.id.fab2:
                mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
                mAdapter = new TvAdapter(this);
                mRecyclerView.setAdapter(mAdapter);
                getOnTheAirTvSeries();
                break;
            case R.id.fab3:
                mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
                mAdapter = new TvAdapter(this);
                mRecyclerView.setAdapter(mAdapter);
                getOnTheAirTvSeries();
                break;
        }
    }

    public void animateFAB(){

        if(isFabOpen){

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab3.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            fab3.setClickable(false);
            isFabOpen = false;


        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab3.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            fab3.setClickable(true);
            isFabOpen = true;

        }
    }
}
