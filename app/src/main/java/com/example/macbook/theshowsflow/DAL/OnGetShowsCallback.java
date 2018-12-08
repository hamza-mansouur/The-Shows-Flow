package com.example.macbook.theshowsflow.DAL;

import com.example.macbook.theshowsflow.Models.Show;

import java.util.List;

public interface OnGetShowsCallback {

        void onSuccess(List<Show> shows);
        void onError();

}
