package com.milushifa.miplayer.ui.fragment.tfragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.milushifa.miplayer.R;


public class MiPlayerFragment extends Fragment {
    private Toolbar mToolbar;
    private ImageView albumArtMiPlayer;
    private SeekBar mSeekBar;
    private ImageButton playPrevButton, controllerButton, playNextButton;
    private TextView seekingTime, currentTime, currentTrackTitle;

    public MiPlayerFragment() {}

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
    }


}
