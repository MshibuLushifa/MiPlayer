package com.milushifa.miplayer.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.milushifa.miplayer.R;
import com.milushifa.miplayer.media.model.Genre;
import com.milushifa.miplayer.media.model.ModelType;
import com.milushifa.miplayer.ui.MainActivity;
import com.milushifa.miplayer.ui.fragment.tfragment.FragmentType;
import com.milushifa.miplayer.ui.fragment.tfragment.backstack.FragmentTransmitter;
import com.milushifa.miplayer.util.Flags;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreHolder> {
    private List<Genre> genreList;
    private Context context;

    private FragmentTransmitter mFragmentTransmitter;

    public GenreAdapter(Context context, List<Genre> genreList, FragmentTransmitter mFragmentTransmitter){
        this.context = context;
        this.genreList = genreList;
        this.mFragmentTransmitter = mFragmentTransmitter;
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




    class GenreHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView genreTitle;

        public GenreHolder(@NonNull View itemView) {
            super(itemView);
            genreTitle = itemView.findViewById(R.id.genreTitle);

            itemView.setOnClickListener(this);
        }
        void setGenre(String title){
            if(title.length()>24) title=title.substring(0, 24) + "...";
            genreTitle.setText(title);
        }

        @Override
        public void onClick(View v) {
            MainActivity.viewPosition = 3;
            mFragmentTransmitter.transmit(FragmentType.EXPANDER_FRAGMENT, ModelType.GENRE, genreList.get(getAdapterPosition()).id);
        }
    }
}
