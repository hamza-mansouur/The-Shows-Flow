package com.example.macbook.theshowsflow.Adapters;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class SmilarShowAdapter extends RecyclerView.Adapter<SmilarShowAdapter.ViewHolder> {

          private List<Show> shows;
          private final Context mContext;
          private String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w500";

        public SmilarShowAdapter(Context mContext, List<Show> shows) {
            this.shows = shows;
            this.mContext=mContext;
        }

        @Override
        public SmilarShowAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_similar_show, viewGroup, false);
            return new SmilarShowAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final SmilarShowAdapter.ViewHolder holder, int position) {
            holder.showName.setText(shows.get(position).getTitle());
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .override(150,250)
                    .error(R.mipmap.ic_launcher_round);

            Glide.with(this.mContext)
                    .load(IMAGE_BASE_URL + shows.get(position).getPosterPath())
                    .apply(options)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, com.bumptech.glide.request.target.Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, com.bumptech.glide.request.target.Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progressView.setVisibility(View.INVISIBLE) ;
                            return false;
                        }


                    })
                    .into(holder.showCover);
        }

        @Override
        public int getItemCount() {
            return shows.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView showCover;
            private TextView showName ;
            private ProgressBar progressView;


            ViewHolder(final View itemView) {
                super(itemView);
                this.showName = (TextView) itemView.findViewById(R.id.tit);
                this.showCover = (ImageView) itemView.findViewById(R.id.cover);
                this.progressView= (ProgressBar) itemView.findViewById(R.id.loadingPanelSimilarShow);
                progressView.setVisibility(View.VISIBLE) ;
            }
        }

}
