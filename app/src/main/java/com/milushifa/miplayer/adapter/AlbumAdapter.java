package com.milushifa.miplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.milushifa.miplayer.R;
import com.milushifa.miplayer.media.model.Album;
import com.milushifa.miplayer.ui.fragment.tfragment.ExpanderFragment;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumHolder> {
    private List<Album> albumList;
    private Context context;

    public AlbumAdapter(Context context, List<Album> albumList){
        this.context = context;
        this.albumList = albumList;
    }


    @NonNull
    @Override
    public AlbumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AlbumHolder(LayoutInflater.from(context).inflate(R.layout.layout_album, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumHolder holder, int position) {
        Album album = albumList.get(position);

        String details = album.artist + ", tracks: " + album.numberOfSong;

        holder.setAlbum(album.album, details);
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }





    class AlbumHolder extends RecyclerView.ViewHolder {
        private ImageView albumAtrView;
        private TextView albumTitle, albumDetails;

        public AlbumHolder(@NonNull View itemView) {
            super(itemView);
            albumAtrView = itemView.findViewById(R.id.albumArt);
            albumTitle = itemView.findViewById(R.id.albumTitle);
            albumDetails = itemView.findViewById(R.id.albumDetails);
        }

        ImageView getAlbumAtrView(){
            return albumAtrView;
        }

        void setAlbum(String title, String details){
            if(title.length()>25)title=title.substring(0, 22) + "...";
            if(details.length()>35)details=details.substring(0, 30) + "...";

             albumTitle.setText(title);
             albumDetails.setText(details);
        }
    }
}
