package com.example.macbook.theshowsflow.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.example.macbook.theshowsflow.Models.Show;
import com.example.macbook.theshowsflow.R;

import java.util.List;

public class ShowsAdapter extends BaseAdapter {

    private final Context mContext;
    private List<Show> shows;
    private String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w500";

    public ShowsAdapter(Context context, List<Show> shows) {
        this.mContext = context;
        this.shows = shows;
    }
    public void setListAdapter(List<Show> shows){
        this.shows = shows;
    }


    @Override
    public int getCount() {
        return shows.size();
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Show show = shows.get(position);
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.linear_layout_show, null);
        }
        final ImageView imageView = (ImageView)convertView.findViewById(R.id.show_cover);
        final TextView nameTextView = (TextView)convertView.findViewById(R.id.show_name);
        final ProgressBar progressView = (ProgressBar) convertView.findViewById(R.id.loadingPanel);
        progressView.setVisibility(View.VISIBLE);

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .override(250,350)
                .error(R.mipmap.ic_launcher_round);

        final View finalConvertView = convertView;
        Glide.with(this.mContext)
               .load(IMAGE_BASE_URL + show.getPosterPath())
               .apply(options)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, com.bumptech.glide.request.target.Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, com.bumptech.glide.request.target.Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressView.setVisibility(View.INVISIBLE) ;
                        return false;
                    }
                })
               .into(imageView);

            nameTextView.setText(show.getTitle());

        return convertView;
    }


}
