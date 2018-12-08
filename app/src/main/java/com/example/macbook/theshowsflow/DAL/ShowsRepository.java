package com.example.macbook.theshowsflow.DAL;

import android.support.annotation.NonNull;

import com.example.macbook.theshowsflow.Models.ShowResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowsRepository {

        private String TMDB_API_KEY="2db3e6260b9410f8584ab6c0d19a8bea";
        private static final String BASE_URL = "https://api.themoviedb.org/3/";
        private static final String LANGUAGE = "en-US";
        private static    int page = 1;
        private static ShowsRepository repository;
        private TMDbApi api;

    private ShowsRepository(TMDbApi api) {
        this.api = api;
    }

    // Singleton Pattern. We don’t want to have multiple instances of ShowsRepository
    public static ShowsRepository getInstance() {
        if (repository == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            repository = new ShowsRepository(retrofit.create(TMDbApi.class));
        }
        return repository;
    }

    public void getShows(final OnGetShowsCallback callback) {
        api.getPopularShows(TMDB_API_KEY, LANGUAGE, page)
                .enqueue(new Callback<ShowResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ShowResponse> call, @NonNull Response<ShowResponse> response) {
                        if (response.isSuccessful()) {
                            ShowResponse showsResponse = response.body();
                            if (showsResponse != null && showsResponse.getShows() != null) {
                                callback.onSuccess(showsResponse.getShows());
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }
                    @Override
                    public void onFailure(Call<ShowResponse> call, Throwable t) {
                        callback.onError();
                    }
                });

    }

    public void getSimilarShows(int id,final OnGetShowsCallback callback) {
        api.getSimilarShows(id ,TMDB_API_KEY)
                .enqueue(new Callback<ShowResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ShowResponse> call, @NonNull Response<ShowResponse> response) {
                        if (response.isSuccessful()) {
                            ShowResponse showsResponse = response.body();
                            if (showsResponse != null && showsResponse.getShows() != null) {
                                callback.onSuccess(showsResponse.getShows());
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<ShowResponse> call, Throwable t) {
                        callback.onError();
                    }
                });

    }
    public void nextPage(){
        page++ ;
    }
}
