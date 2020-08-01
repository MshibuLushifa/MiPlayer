package com.milushifa.miplayer.ui.fragment.tfragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.milushifa.miplayer.R;
import com.milushifa.miplayer.receiver.MediaObserver;
import com.milushifa.miplayer.service.player.PlayerTracker;
import com.milushifa.miplayer.receiver.UiDataReceiver;
import com.milushifa.miplayer.receiver.UiUpdater;
import com.milushifa.miplayer.service.MiPlayerService;
import com.milushifa.miplayer.service.ServiceProvider;
import com.milushifa.miplayer.service.player.ControllerConstants;
import com.milushifa.miplayer.util.Flags;

import java.util.Objects;


public class MiPlayerFragment extends Fragment implements View.OnClickListener, UiUpdater, MediaObserver {
    private Toolbar mToolbar;
    private ImageView albumArtMiPlayer;
    private SeekBar mSeekBar;
    private ImageButton playPrevButton, controllerButton, playNextButton;
    private TextView seekingTime, currentTime, currentTrackTitle, currentTrackDetails;

    private boolean isThisActive;

    private PlayerTracker mPlayerTracker;

    private int durationOfCurrentTrack;


    public MiPlayerFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isThisActive = true;

        UiDataReceiver.setUiUpdater(this);
        UiDataReceiver.setMediaObserver(this);

        mPlayerTracker = PlayerTracker.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View miPlayerFragmentView = inflater.inflate(R.layout.fragment_mi_player, container, false);
        initComponents(miPlayerFragmentView);
        return miPlayerFragmentView;
    }

    private void initComponents(View rootView) {
        mToolbar = rootView.findViewById(R.id.toolBarMiPlayer);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        albumArtMiPlayer = rootView.findViewById(R.id.albumArtMiPlayer);
        mSeekBar = rootView.findViewById(R.id.seekBar);
        playPrevButton = rootView.findViewById(R.id.playPrevButton);
        controllerButton = rootView.findViewById(R.id.controllerButton);
        playNextButton = rootView.findViewById(R.id.playNextButton);

        seekingTime = rootView.findViewById(R.id.seekingTime);
        currentTime = rootView.findViewById(R.id.currentTime);

        currentTrackTitle = rootView.findViewById(R.id.currentTrackTitle);
        currentTrackDetails = rootView.findViewById(R.id.currentTrackDetails);

        playPrevButton.setOnClickListener(this);
        controllerButton.setOnClickListener(this);
        playNextButton.setOnClickListener(this);

        setTrackAndDetails(mPlayerTracker.getCurrentTrackTitle(), mPlayerTracker.getCurrentTrackDetails());

        setDuration(mPlayerTracker.getDurationOfCurrentTrack());
        setSeekProgress(mPlayerTracker.getPositionOfCurrentTrack());
        mediaChangePlayingStatus(mPlayerTracker.getPlayingStatus());

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               if(fromUser){
                   Intent intent = new Intent(getContext(), MiPlayerService.class);
                   intent.setAction(ControllerConstants.SET_TRACK_POSITION);
                   intent.putExtra("PROGRESS", progress);
                   Objects.requireNonNull(getContext()).startService(intent);
               }
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setTrackAndDetails(String title, String details){
        if(title!=null && details!=null) {
            if (details.length() > 42) details = details.substring(0, 40);
            currentTrackTitle.setText(title);
            currentTrackDetails.setText(details);
        }
    }

    @Override public void onClick(View v) {
        switch(v.getId()){
            case R.id.playPrevButton:
                getContext().startService(ServiceProvider.getMiServiceIntent(getContext(),
                        ControllerConstants.PREV_TRACK));
                break;
            case R.id.controllerButton:
                getContext().startService(ServiceProvider.getMiServiceIntent(getContext(),
                        ControllerConstants.CONTROL_TRACK));
                break;
            case R.id.playNextButton:
                getContext().startService(ServiceProvider.getMiServiceIntent(getContext(),
                        ControllerConstants.NEXT_TRACK));
                break;
        }
    }

    @Override public void onDestroy() { super.onDestroy(); isThisActive = false; }

    @Override public boolean isActivityActive() {
        return isThisActive;
    }

    @Override public void setDuration(int duration) {
        durationOfCurrentTrack = duration;
        mSeekBar.setMax(duration);
    }

    @Override public void setSeekProgress(int progress) {
        String crntTime, skgTime;
        int duration = durationOfCurrentTrack - progress;
        // For current Time...
        if(duration<3600000){
            long minute = (duration/1000)/60;
            long second = (duration/1000)%60;
            crntTime = minute + ":" + second;
        }else{
            long hours = ((duration/1000)/60)/60;
            long minute = ((duration/1000)/60)%60;
            long second = (duration/1000)%60;
            crntTime = hours + ":" + minute + ":" + second;
        }
        // For seeking Time...
        if(progress<3600000){
            long minute = (progress/1000)/60;
            long second = (progress/1000)%60;
            skgTime = minute + ":" + second;
        }else{
            long hours = ((progress/1000)/60)/60;
            long minute = ((progress/1000)/60)%60;
            long second = (progress/1000)%60;
            skgTime = hours + ":" + minute + ":" + second;
        }
        currentTime.setText(crntTime);
        seekingTime.setText(skgTime);
        mSeekBar.setProgress(progress);
    }
    @Override
    public void trackChange() {
        setTrackAndDetails(mPlayerTracker.getCurrentTrackTitle(), mPlayerTracker.getCurrentTrackDetails());
    }

    @Override
    public void mediaChangePlayingStatus(boolean isPlaying) {
        if(isPlaying){ controllerButton.setImageResource(R.drawable.ic_pause);
        }else{ controllerButton.setImageResource(R.drawable.ic_play); }
    }
}
