package com.milushifa.miplayer.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.milushifa.miplayer.R;
import com.milushifa.miplayer.media.model.Album;
import com.milushifa.miplayer.media.model.ModelType;
import com.milushifa.miplayer.ui.MainActivity;
import com.milushifa.miplayer.ui.fragment.tfragment.FragmentType;
import com.milushifa.miplayer.ui.fragment.tfragment.backstack.FragmentTransmitter;
import com.milushifa.miplayer.util.Flags;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumHolder> {
    private List<Album> albumList;
    private Context context;

    private FragmentTransmitter mFragmentTransmitter;

    public AlbumAdapter(Context context, List<Album> albumList, FragmentTransmitter mFragmentTransmitter){
        this.context = context;
        this.albumList = albumList;
        this.mFragmentTransmitter = mFragmentTransmitter;
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





    class AlbumHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView albumAtrView;
        private TextView albumTitle, albumDetails;

        public AlbumHolder(@NonNull View itemView) {
            super(itemView);
            albumAtrView = itemView.findViewById(R.id.albumArt);
            albumTitle = itemView.findViewById(R.id.albumTitle);
            albumDetails = itemView.findViewById(R.id.albumDetails);

            itemView.setOnClickListener(this);
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

        @Override
        public void onClick(View v) {
            MainActivity.viewPosition = 1;
            mFragmentTransmitter.transmit(FragmentType.EXPANDER_FRAGMENT, ModelType.ALBUM, albumList.get(getAdapterPosition()).id);
        }
    }
}
