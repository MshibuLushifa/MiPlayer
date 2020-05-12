package com.milushifa.miplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.milushifa.miplayer.R;
import com.milushifa.miplayer.media.model.Genre;
import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreHolder> {
    private List<Genre> genreList;
    private Context context;

    public GenreAdapter(Context context, List<Genre> genreList){
        this.context = context;
        this.genreList = genreList;
    }


    @NonNull
    @Override
    public GenreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GenreHolder(LayoutInflater.from(context).inflate(R.layout.layout_genre, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GenreHolder holder, int position) {
        Genre genre = genreList.get(position);

        holder.setGenre(genre.genre);
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }

    class GenreHolder extends RecyclerView.ViewHolder{
        private TextView genreTitle;

        public GenreHolder(@NonNull View itemView) {
            super(itemView);
            genreTitle = itemView.findViewById(R.id.genreTitle);
        }
        void setGenre(String title){
            if(title.length()>24) title=title.substring(0, 24) + "...";
            genreTitle.setText(title);
        }
    }
}
