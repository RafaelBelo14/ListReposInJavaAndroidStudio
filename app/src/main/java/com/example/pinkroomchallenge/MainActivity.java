package com.example.pinkroomchallenge;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView dataListView;
    private EditText requestTag;
    private TextView errorMessage;
    private TextView users;
    private TextView repos;
    private boolean UserOrRepo = false;
    private final static String URL_GITHUB = "https://api.github.com/";
    private final static String SORT = "stars";
    private final static String ORDER_BY = "desc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_design);
        initViews();
        initResourceId(false);
        makeGithubSearchQuery("created");
    }

    private void initViews() {
        errorMessage = findViewById(R.id.errorMessage);
        requestTag = findViewById(R.id.requestTag);
        dataListView = findViewById(R.id.repositoriesList);
        users = findViewById(R.id.buttonUsers);
        repos = findViewById(R.id.buttonRepos);
    }

    public void searchRepo(View view) {
        makeGithubSearchQuery("");
    }

    public void changeForUsers(View view) {
        initResourceId(true);
        repos.setTextColor(Color.rgb(129, 129, 129));
        repos.setBackground(null);
        UserOrRepo = true;
        makeGithubSearchQuery("");
    }

    public void changeForRepos(View view) {
        initResourceId(false);
        users.setTextColor(Color.rgb(129, 129, 129));
        users.setBackground(null);
        UserOrRepo = false;
        makeGithubSearchQuery("");
    }

    public void initResourceId(boolean type) {
        Resources resources = this.getResources();
        int resourceId = resources.getIdentifier("choice_background", "drawable", this.getPackageName());

        if (type) {
            users.setBackground(resources.getDrawable(resourceId));
            users.setTextColor(Color.WHITE);
        }
        else {
            repos.setBackground(resources.getDrawable(resourceId));
            repos.setTextColor(Color.WHITE);
        }
    }

    private void makeGithubSearchQuery(String search) {
        String githubQuery = checkStringCondition(search);
        OkHttpClient client = getOkHttpClient();
        Retrofit retrofit = getRetrofit(client);
        GitHubApiRetrofit service = retrofit.create(GitHubApiRetrofit.class);

        if (UserOrRepo) {
            Call<List<Repository>> call = service.listUsers(githubQuery);
            call.enqueue(new Callback<List<Repository>>() {
                @Override
                public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                    if (response.isSuccessful()) {
                        int statusCode = response.code();
                        System.out.println(statusCode);
                        printResults(response.body());
                        showRepos();
                    }
                }

                @Override
                public void onFailure(Call<List<Repository>> call, Throwable t) {
                    showErrorMessage();
                    t.printStackTrace();
                }
            });
        }
        else {
            Call<GitHubResponse> call = service.listRepo(githubQuery, SORT, ORDER_BY);
            call.enqueue(new Callback<GitHubResponse>() {
                @Override
                public void onResponse(Call<GitHubResponse> call, Response<GitHubResponse> response) {
                    if (response.isSuccessful()) {
                        int statusCode = response.code();
                        System.out.println(statusCode);
                        printResults(response.body().getRepositoryList());
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
    }

    @NonNull
    private String checkStringCondition(String search) {
        String githubQuery;
        if (search.equals("")) {
            githubQuery = requestTag.getText().toString();
        } else {
            githubQuery = search;
        }
        return githubQuery;
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

    private void printResults(List<Repository> repos) {
        dataListView = findViewById(R.id.repositoriesList);
        gitHubRepoAdapter adapter = new gitHubRepoAdapter(this, repos);
        dataListView.setAdapter(adapter);
        dataListView.setLayoutManager(new LinearLayoutManager(this));
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