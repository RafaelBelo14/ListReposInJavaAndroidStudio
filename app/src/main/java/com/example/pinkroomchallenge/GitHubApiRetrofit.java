package com.example.pinkroomchallenge;

import retrofit2.Call;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GitHubApiRetrofit {
    @GET("search/repositories")
    Call<GitHubResponse> listRepo(@Query("q") String query, @Query("sort") String sort, @Query("order") String order);
}
