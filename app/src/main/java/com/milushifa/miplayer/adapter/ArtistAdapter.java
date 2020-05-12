package com.milushifa.miplayer.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.milushifa.miplayer.R;
import com.milushifa.miplayer.media.model.Album;
import com.milushifa.miplayer.media.model.Artist;

import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistHolder> {
    private List<Artist> artistList;
    private Context context;

    public ArtistAdapter(Context context, List<Artist> artistList){
        this.context = context;
        this.artistList = artistList;
    }


    @NonNull
    @Override
    public ArtistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ArtistHolder(LayoutInflater.from(context).inflate(R.layout.layout_artist, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistHolder holder, int position) {
        Artist artist = artistList.get(position);
        holder.setAlbum(artist.artist, "tracks: " + artist.songCount + ", album: " + artist.albumCount);

    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }

    class ArtistHolder extends RecyclerView.ViewHolder{
        private ImageView artistAtrView;
        private TextView artistTitle, artistDetails;

        public ArtistHolder(@NonNull View itemView) {
            super(itemView);
            artistAtrView = itemView.findViewById(R.id.artistArt);
            artistTitle = itemView.findViewById(R.id.artistTitle);
            artistDetails = itemView.findViewById(R.id.artistDetails);
        }

        ImageView getArtistAtrView(){
            return artistAtrView;
        }

        void setAlbum(String title, String details){
            if(title.length()>25)title=title.substring(0, 23) + "...";
            if(details.length()>35)details=details.substring(0, 30) + "...";

            artistTitle.setText(title);
            artistDetails.setText(details);
        }
    }
}
