package com.example.macbook.theshowsflow.Presentation;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.macbook.theshowsflow.Adapters.SmilarShowAdapter;
import com.example.macbook.theshowsflow.DAL.OnGetShowsCallback;
import com.example.macbook.theshowsflow.DAL.ShowsRepository;
import com.example.macbook.theshowsflow.Models.Show;
import com.example.macbook.theshowsflow.R;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;
import com.takusemba.multisnaprecyclerview.OnSnapListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowDetailActivity extends AppCompatActivity {


    @BindView(R.id.detail_show_name)
    TextView showTitle;
    @BindView(R.id.detail_show_date)
    TextView showDate;
    @BindView(R.id.ratingBar)
    RatingBar showRate;
    @BindView(R.id.detail_show_cover)
    ImageView showCover;
    @BindView(R.id.detail_show_description)
    TextView showDescription;
    @BindView(R.id.simalarShowsList)
    MultiSnapRecyclerView multiSnapRecyclerView;

    static Show show;
    ShowsRepository showsRepository;
    private SmilarShowAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        setActivityComponents();
        setActionBar();
        showsRepository= ShowsRepository.getInstance();
        getSimilarShows(showsRepository);

    }

    private void getSimilarShows(ShowsRepository showsRepositorysRepository){

        showsRepository.getSimilarShows(show.getId(),new OnGetShowsCallback() {
            @Override
            public void onSuccess(List<Show> shows) {
                adapter = new SmilarShowAdapter(getApplicationContext(), shows);
                multiSnapRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onError() {
                Toast.makeText(ShowDetailActivity.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        multiSnapRecyclerView.setLayoutManager(layoutManager);
        multiSnapRecyclerView.setAdapter(adapter);
        multiSnapRecyclerView.setOnSnapListener(new OnSnapListener() {
            @Override
            public void snapped(int position) {
            }
        });
    }

    private void setActivityComponents(){

        ButterKnife.bind(this);
        showTitle.setText(show.getTitle());
        ButterKnife.bind(this);
        showDate.setText(show.getReleaseDate());
        ButterKnife.bind(this);
        LayerDrawable stars = (LayerDrawable) showRate.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        showRate.setRating(show.getRating());
        ButterKnife.bind(this);
        showDescription.setText(show.getOverview());
        showDescription.setMovementMethod(new ScrollingMovementMethod());
        ButterKnife.bind(this);
        Glide.with(this)
                .load("http://image.tmdb.org/t/p/w500" + show.getPosterPath())
                //.apply(options)//RequestOptions.placeholderOf(R.color.colorPrimary))
                .into(showCover);
    }
    private void setActionBar(){
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setLogo(R.drawable.shows_flow_bar_icon);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }
}
