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
import com.milushifa.miplayer.media.model.Track;
import com.milushifa.miplayer.service.ServiceProvider;
import com.milushifa.miplayer.service.player.ControllerConstants;
import com.milushifa.miplayer.service.player.MPPlayable;
import com.milushifa.miplayer.service.player.datasaver.CookieDBHelper;
import com.milushifa.miplayer.service.player.datasaver.CookieHelper;
import com.milushifa.miplayer.ui.fragment.tfragment.FragmentType;
import com.milushifa.miplayer.ui.fragment.tfragment.backstack.FragmentTransmitter;
import com.milushifa.miplayer.util.Flags;

import java.util.List;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.TrackHolder> {

    private List<Track> trackList;
    private Context context;

    private MPPlayable mpPlayable;

    private FragmentTransmitter mFragmentTransmitter;

    public TrackAdapter(Context context, List<Track> trackList, FragmentTransmitter mFragmentTransmitter){
        this.context = context;
        this.trackList = trackList;
        this.mpPlayable = MPPlayable.getInstance();
        this.mFragmentTransmitter = mFragmentTransmitter;
    }

    @NonNull
    @Override
    public TrackHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TrackHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_track, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TrackHolder holder, int position) {
        Track track = trackList.get(position);

        if(track.duration<3600000){
//            float minuteWithSecond = ((float)(track.duration / 1000) / 60);
//            int minute = (int) minuteWithSecond;
//            float rawSecond = minuteWithSecond-minute;
//            int second = (int) ((rawSecond*1000)/10);
            long minute = (track.duration/1000)/60;
            long second = (track.duration/1000)%60;

            holder.setTrack(track.title, track.album + ", " + track.artist, minute + ":" + second);
        }else{
            long hours = ((track.duration/1000)/60)/60;
            long minute = ((track.duration/1000)/60)%60;
            long second = (track.duration/1000)%60;
            holder.setTrack(track.title, track.album + ", " + track.artist, hours + ":" + minute + ":" + second);
        }
//            holder.setTrack(track.title, track.album + ", " + track.artist, String.valueOf(track.duration));
    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }

    class TrackHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView trackTitle, trackDetails, trackDuration;

        TrackHolder(@NonNull View rootView) {
            super(rootView);
            trackTitle = rootView.findViewById(R.id.trackTitle);
            trackDetails = rootView.findViewById(R.id.trackDetails);
            trackDuration = rootView.findViewById(R.id.trackDuration);
            rootView.setOnClickListener(this);
        }

        void setTrack(String title, String details, String duration){
            if(title.length()>25)title=title.substring(0, 23) + "...";
            if(details.length()>35)details=details.substring(0, 30) + "...";

            trackDetails.setText(details);
            trackTitle.setText(title);
            trackDuration.setText(duration);
        }

        @Override
        public void onClick(View v) {
            CookieDBHelper mCookie = CookieHelper.getHelper(context);
            mCookie.storeThisAsCookie(trackList);

            mpPlayable.updateCurrentPlayable(trackList, getAdapterPosition());
            context.startService(ServiceProvider.getMiServiceIntent(context, ControllerConstants.PLAY_TRACK));
            mFragmentTransmitter.transmit(FragmentType.PLAYER_FRAGMENT);
        }
    }
}
