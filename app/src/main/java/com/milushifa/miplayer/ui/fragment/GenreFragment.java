package com.milushifa.miplayer.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.milushifa.miplayer.R;



public class GenreFragment extends Fragment {
    private RecyclerView mRecyclerView;

    public GenreFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View genreFragmentView = inflater.inflate(R.layout.fragment_genre, container, false);
        initComponent(genreFragmentView);
        return genreFragmentView;
    }
    private void initComponent(View rootView){
        mRecyclerView = rootView.findViewById(R.id.recyclerViewGenreFragment);
    }
}
