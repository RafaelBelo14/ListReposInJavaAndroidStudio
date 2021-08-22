package com.example.pinkroomchallenge;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GitHubResponseUsers {
    @SerializedName("items")
    private List<Repository> usersList;

    public List<Repository> getUsersList() {
        System.out.println(usersList);
        return usersList;
    }
}
