package com.example.pinkroomchallenge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class gitHubRepoAdapter extends ArrayAdapter {

    private final GitHubResponse list;

    public gitHubRepoAdapter(@NonNull Context context, GitHubResponse list) {
        super(context, 0);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.getRepositoryList().size();
    }

    @Override
    public Repository getItem(int pos) {
        return list.getRepositoryList().get(pos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.repo_design, parent, false);
        }

        TextView repoName = view.findViewById(R.id.repoName);
        TextView repoDescription = view.findViewById(R.id.repoDescription);
        TextView repoOwner = view.findViewById(R.id.repoOwner);
        TextView repoLang = view.findViewById(R.id.repoLang);
        TextView repoStars = view.findViewById(R.id.repoStars);

        Repository currentRepo = getItem(position);

        repoName.setText(currentRepo.getName());
        repoDescription.setText(currentRepo.getDescription());
        repoOwner.setText(currentRepo.getOwner().getLogin());
        repoLang.setText(currentRepo.getLanguage());
        repoStars.setText(currentRepo.getStars());

        return view;
    }
}