package com.example.pinkroomchallenge;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GitHubApi {
    private final static String URL_GITHUB = "https://api.github.com/";
    private final static String SEARCH_TAG = "search/repositories";
    private final static String QUERY = "q"; //query tag
    private final static String SORT = "sort"; //sort por default é decrescente
    private final static String ORDER_BY = "stars"; //sort é dado pelas stars do repo

    /**
     * buildUri -> Constrói o URL do request à API
     * @param searchQuery
     * @return
     */

    private static URL buildUrl(String searchQuery) {
        Uri uri = Uri.parse(URL_GITHUB + SEARCH_TAG).buildUpon()
                .appendQueryParameter(QUERY, searchQuery)
                .appendQueryParameter(SORT, ORDER_BY)
                .build();

        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * getReposFromApi -> get repositories from API throw url of buildUrl function
     * @param url
     * @return
     * @throws IOException
     */

    private static String getReposFromApi(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {

            InputStream in = urlConnection.getInputStream();
            Scanner sc = new Scanner(in);
            sc.useDelimiter("\\A");

            boolean hasInput = sc.hasNext();

            if (hasInput) {
                return sc.next();
            }
            else {
                return null;
            }

        } finally {
            urlConnection.disconnect();
        }
    }

    private static List<Repository> dataFormatter(String response) {
        int listSize = 50;
        List<Repository> repos = new ArrayList<>();

        try {
            JSONObject json = new JSONObject(response);
            JSONArray listRepos = json.getJSONArray("items"); //extrái a lista de repositórios da chave "items"

            if (listRepos.length() < listSize) {
                listSize = listRepos.length();
            }

            for (int i = 0; i < listSize; i ++) {
                JSONObject currentRepo = listRepos.getJSONObject(i);
                String repoName = currentRepo.getString("name");
                String repoDescription = currentRepo.getString("description");
                String repoLanguage = currentRepo.getString("language");
                String repoOwer = currentRepo.getJSONObject("owner").getString("login");
                String repoStars = currentRepo.getString("stargazers_count");

                Log.v("Data -> ", "Repo: " + repoName + " / Owner: " + repoOwer);

                //Repository repository = new Repository(repoName, repoDescription, repoLanguage, repoOwer, repoStars);
                //repos.add(repository);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return repos;

    }

    static List<Repository> getDataFromApi(String query) throws IOException {
        URL url = buildUrl(query);
        String jsonResponse = getReposFromApi(url);
        List<Repository> repos = dataFormatter(jsonResponse);
        return repos;
    }

}