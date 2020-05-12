package com.milushifa.miplayer.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.milushifa.miplayer.ui.fragment.AlbumFragment;
import com.milushifa.miplayer.ui.fragment.ArtistFragment;
import com.milushifa.miplayer.ui.fragment.GenreFragment;
import com.milushifa.miplayer.ui.fragment.PlaylistFragment;
import com.milushifa.miplayer.ui.fragment.TrackFragment;

public class PagerAdapter extends FragmentStateAdapter {
    public PagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0: return new TrackFragment();
            case 1: return new AlbumFragment();
            case 2: return new ArtistFragment();
            case 3: return new GenreFragment();
            case 4: return new PlaylistFragment();
            default: return null;
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
