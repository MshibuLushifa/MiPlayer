package com.milushifa.miplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.milushifa.miplayer.R;
import com.milushifa.miplayer.media.model.Artist;
import com.milushifa.miplayer.media.model.ModelType;
import com.milushifa.miplayer.ui.MainActivity;
import com.milushifa.miplayer.ui.fragment.tfragment.FragmentType;
import com.milushifa.miplayer.ui.fragment.tfragment.backstack.FragmentTransmitter;

import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistHolder> {
    private List<Artist> artistList;
    private Context context;

    private FragmentTransmitter mFragmentTransmitter;

    public ArtistAdapter(Context context, List<Artist> artistList, FragmentTransmitter mFragmentTransmitter){
        this.context = context;
        this.artistList = artistList;
        this.mFragmentTransmitter = mFragmentTransmitter;
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





    class ArtistHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView artistAtrView;
        private TextView artistTitle, artistDetails;

         ArtistHolder(@NonNull View itemView) {
            super(itemView);
            artistAtrView = itemView.findViewById(R.id.artistArt);
            artistTitle = itemView.findViewById(R.id.artistTitle);
            artistDetails = itemView.findViewById(R.id.artistDetails);

            itemView.setOnClickListener(this);
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

        @Override
        public void onClick(View v) {
            MainActivity.viewPosition = 2;
            mFragmentTransmitter.transmit(FragmentType.EXPANDER_FRAGMENT, ModelType.ARTIST, artistList.get(getAdapterPosition()).id);
        }
    }
}
