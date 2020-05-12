package com.milushifa.miplayer.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.milushifa.miplayer.R;


public class AlbumFragment extends Fragment {
    private RecyclerView mRecyclerView;

    public AlbumFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View albumFragmentView = inflater.inflate(R.layout.fragment_album, container, false);
        initComponent(albumFragmentView);
        return albumFragmentView;
    }
    private void initComponent(View rootView){
        mRecyclerView = rootView.findViewById(R.id.recyclerViewTrackFragment);
    }
}
