package com.example.pinkroomchallenge;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GitHubResponse {
    @SerializedName("items")
    private List<Repository> repositoryList;

    public List<Repository> getRepositoryList() {
        System.out.println(repositoryList);
        return repositoryList;
    }
}
