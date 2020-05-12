package com.milushifa.miplayer.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.milushifa.miplayer.R;


public class ArtistFragment extends Fragment {
    private RecyclerView mRecyclerView;

    public ArtistFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View artistFragmentView = inflater.inflate(R.layout.fragment_artist, container, false);
        initComponent(artistFragmentView);
        return artistFragmentView;
    }
    private void initComponent(View rootView){
        mRecyclerView = rootView.findViewById(R.id.recyclerViewArtistFragment);
    }
}
