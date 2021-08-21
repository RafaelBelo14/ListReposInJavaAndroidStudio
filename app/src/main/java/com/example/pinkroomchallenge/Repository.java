package com.example.pinkroomchallenge;

import com.google.gson.annotations.SerializedName;

public class Repository {
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("language")
    private String language;
    @SerializedName("owner")
    private Owner owner;
    @SerializedName("stargazers_count")
    private String stars;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }
}
