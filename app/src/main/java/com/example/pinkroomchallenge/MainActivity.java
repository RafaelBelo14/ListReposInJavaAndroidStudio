package com.example.pinkroomchallenge;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ListView dataListView;
    private EditText requestTag;
    private TextView errorMessage;
    private final static String URL_GITHUB = "https://api.github.com/";
    private final static String SORT = "stars";
    private final static String ORDER_BY = "desc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_design);
        initViews();
        dataListView.setEmptyView(errorMessage);
    }

    private void initViews() {
        errorMessage = findViewById(R.id.errorMessage);
        requestTag = findViewById(R.id.requestTag);
        dataListView = findViewById(R.id.repositoriesList);
    }

    public void searchRepo(View view) {
        makeGithubSearchQuery();
    }

    private void makeGithubSearchQuery() {
        String githubQuery = requestTag.getText().toString();
        OkHttpClient client = getOkHttpClient();
        Retrofit retrofit = getRetrofit(client);
        GitHubApiRetrofit service = retrofit.create(GitHubApiRetrofit.class);
        Call<GitHubResponse> call = service.listRepo(githubQuery, SORT, ORDER_BY);
        call.enqueue(new Callback<GitHubResponse>() {
            @Override
            public void onResponse(Call<GitHubResponse> call, Response<GitHubResponse> response) {
                if (response.isSuccessful()) {
                    int statusCode = response.code();
                    System.out.println(statusCode);
                    printResults(response.body());
                    showRepos();
                }
            }

            @Override
            public void onFailure(Call<GitHubResponse> call, Throwable t) {
                showErrorMessage();
                t.printStackTrace();
            }
        });
    }

    @NonNull
    private Retrofit getRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(URL_GITHUB)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    @NonNull
    private OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();
    }

    private void printResults(GitHubResponse repos) {
        dataListView = findViewById(R.id.repositoriesList);
        gitHubRepoAdapter adapter = new gitHubRepoAdapter(this, repos);
        dataListView.setAdapter(adapter);
    }

    private void showErrorMessage() {
        errorMessage.setVisibility(View.VISIBLE);
        dataListView.setVisibility(View.INVISIBLE);
    }

    private void showRepos() {
        dataListView.setVisibility(View.VISIBLE);
        errorMessage.setVisibility(View.INVISIBLE);
    }
}