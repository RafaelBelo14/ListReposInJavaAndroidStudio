package com.example.pinkroomchallenge;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitHubApiRetrofit {
    @GET("search/repositories")
    Call<GitHubResponse> listRepo(@Query("q") String query, @Query("sort") String sort, @Query("order") String order);
    @GET("users/{user}/repos")
    Call<List<Repository>> listUsers(@Path("user") String user);
}
