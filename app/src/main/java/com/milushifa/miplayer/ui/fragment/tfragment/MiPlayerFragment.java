package com.milushifa.miplayer.ui.fragment.tfragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.milushifa.miplayer.receiver.PlayerTracker;
import com.milushifa.miplayer.receiver.UiDataReceiver;
import com.milushifa.miplayer.receiver.UiUpdater;
import com.milushifa.miplayer.service.MiPlayerService;
import com.milushifa.miplayer.service.ServiceProvider;
import com.milushifa.miplayer.service.player.ControllerConstants;
import com.milushifa.miplayer.util.Flags;


public class MiPlayerFragment extends Fragment implements View.OnClickListener, UiUpdater {
    private Toolbar mToolbar;
    private ImageView albumArtMiPlayer;
    private SeekBar mSeekBar;
    private ImageButton playPrevButton, controllerButton, playNextButton;
    private TextView seekingTime, currentTime, currentTrackTitle;

    private boolean isThisActive;

    private PlayerTracker mPlayerTracker;

    public MiPlayerFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isThisActive = true;

        UiDataReceiver.setUiUpdater(this);

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

        playPrevButton.setOnClickListener(this);
        controllerButton.setOnClickListener(this);
        playNextButton.setOnClickListener(this);

        setDuration(mPlayerTracker.getDurationOfCurrentTrack());
        setSeekProgress(mPlayerTracker.getPositionOfCurrentTrack());

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               if(fromUser){
                   Intent intent = new Intent(getContext(), MiPlayerService.class);
                   intent.setAction(ControllerConstants.SET_TRACK_POSITION);
                   intent.putExtra("PROGRESS", progress);
                   getContext().startService(intent);
               }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
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

    @Override public void onDestroy() {
        super.onDestroy();
        isThisActive = false;
    }

    @Override public boolean isActivityActive() {
        return isThisActive;
    }

    @Override public void setDuration(int duration) {
        mSeekBar.setMax(duration);
    }

    @Override public void setSeekProgress(int progress) {
        mSeekBar.setProgress(progress);
    }
}
