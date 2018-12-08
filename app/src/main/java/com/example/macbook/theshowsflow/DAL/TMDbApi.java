package com.example.macbook.theshowsflow.DAL;
import com.example.macbook.theshowsflow.Models.ShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDbApi {

    @GET("tv/popular")
    Call<ShowResponse> getPopularShows(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("tv/{id}/similar")
    Call<ShowResponse> getSimilarShows(
            @Path("id") int id,
            @Query("api_key") String apiKey
    );

}
