package com.milushifa.miplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.milushifa.miplayer.R;
import com.milushifa.miplayer.media.model.Track;

import java.util.List;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.TrackHolder> {

    private List<Track> trackList;
    private Context context;

    public TrackAdapter(Context context, List<Track> trackList){
        this.context = context;
        this.trackList = trackList;
    }


    @NonNull
    @Override
    public TrackHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TrackHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_track, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TrackHolder holder, int position) {
        Track track = trackList.get(position);

        holder.setTrack(track.title, track.album + ", " + track.artist, String.valueOf(track.duration));
    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }

    class TrackHolder extends RecyclerView.ViewHolder{

        private TextView trackTitle, trackDetails, trackDuration;

        TrackHolder(@NonNull View rootView) {
            super(rootView);
            trackTitle = rootView.findViewById(R.id.trackTitle);
            trackDetails = rootView.findViewById(R.id.trackDetails);
            trackDuration = rootView.findViewById(R.id.trackDuration);
        }

        void setTrack(String title, String details, String duration){
            trackTitle.setText(title);
            trackDetails.setText(details);
            trackDuration.setText(duration);
        }
    }
}
