package com.example.macbook.theshowsflow.Presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.macbook.theshowsflow.Adapters.ShowsAdapter;
import com.example.macbook.theshowsflow.DAL.OnGetShowsCallback;
import com.example.macbook.theshowsflow.DAL.ShowsRepository;
import com.example.macbook.theshowsflow.Models.Show;
import com.example.macbook.theshowsflow.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowsDashboardActivity extends AppCompatActivity {

    private ShowsAdapter adapter;

    private ShowsRepository showsRepository;
    @BindView(R.id.gridview)
     GridView gridView ;
    private List<Show> listShows =new ArrayList<>();
    private Boolean isFirstListLoading=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shows_dashboard);

        setActionBar();
        showsRepository= ShowsRepository.getInstance();
        getShows();
        ButterKnife.bind(this);
        setGridView(gridView);
    }

    private void setGridView(GridView gridView){

        gridView.setOnScrollListener(new AbsListView.OnScrollListener(){

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {
                if(firstVisibleItem + visibleItemCount >= totalItemCount){
                    showsRepository.nextPage();
                    getShows();
                }
            }
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState){

            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ShowDetailActivity.show = listShows.get(i);
                Intent intent = new Intent(getBaseContext(), ShowDetailActivity.class);
                startActivity(intent);

            }
        });
    }

    private void getShows() {
        showsRepository.getShows(new OnGetShowsCallback() {
            @Override
            public void onSuccess(List<Show> shows) {
                listShows.addAll(shows);
                if(isFirstListLoading) {
                    initView(listShows);
                    isFirstListLoading=false;
                }
                else{
                    adapter.setListAdapter(listShows);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onError() {
                Toast.makeText(ShowsDashboardActivity.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setActionBar(){
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setLogo(R.drawable.shows_flow_bar_icon);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

    private void initView(List<Show> showsList){
        adapter = new ShowsAdapter(getApplicationContext(), showsList);
        gridView.setAdapter(adapter);
    }

}
