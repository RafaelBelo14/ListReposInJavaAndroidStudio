package com.example.pinkroomchallenge;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class gitHubRepoAdapter extends RecyclerView.Adapter<gitHubRepoAdapter.ViewHolder> {

    private final Context context;
    private final List<Repository> list;

    public gitHubRepoAdapter(Context context, List<Repository> list) {
        this.context = context;
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView repoName;
        TextView repoDescription;
        TextView repoOwner;
        TextView repoLang;
        TextView repoStars;
        RelativeLayout parentLayout;

        public ViewHolder(View view) {
            super(view);
            repoName = view.findViewById(R.id.repoName);
            repoDescription = view.findViewById(R.id.repoDescription);
            repoOwner = view.findViewById(R.id.repoOwner);
            repoLang = view.findViewById(R.id.repoLang);
            repoStars = view.findViewById(R.id.repoStars);
            parentLayout = view.findViewById(R.id.parent_layout);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repo_design, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.repoName.setText(list.get(position).getName());
        holder.repoDescription.setText(list.get(position).getDescription());
        holder.repoOwner.setText(list.get(position).getOwner().getLogin());
        holder.repoLang.setText(list.get(position).getLanguage());
        holder.repoStars.setText(list.get(position).getStars());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Data: ", "onClick: clicked on: " + list.get(holder.getAdapterPosition()).getName());
                Toast.makeText(context, list.get(holder.getAdapterPosition()).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}