package com.milushifa.miplayer.ui.fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.milushifa.miplayer.R;
import com.milushifa.miplayer.adapter.AlbumAdapter;
import com.milushifa.miplayer.adapter.ArtistAdapter;
import com.milushifa.miplayer.media.loader.AlbumLoader;
import com.milushifa.miplayer.media.loader.ArtistLoader;
import com.milushifa.miplayer.ui.fragment.tfragment.backstack.FragmentTransmitter;


public class ArtistFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ArtistAdapter mArtistAdapter;

    private FragmentTransmitter mFragmentTransmitter;

    public ArtistFragment(FragmentTransmitter mFragmentTransmitter){
        this.mFragmentTransmitter = mFragmentTransmitter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View artistFragmentView = inflater.inflate(R.layout.fragment_artist, container, false);
        initComponent(artistFragmentView);
        return artistFragmentView;
    }
    private void initComponent(View rootView){
        mRecyclerView = rootView.findViewById(R.id.recyclerViewArtistFragment);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        new LoadArtist().execute();
    }

    public class LoadArtist extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            if (getActivity() != null) {
                mArtistAdapter = new ArtistAdapter(getContext(), new ArtistLoader().getAllArtist(getContext()), mFragmentTransmitter);
            }
            return "Execute";
        }

        @Override
        protected void onPostExecute(String s) {
            if (getActivity() != null) {
                mRecyclerView.setAdapter(mArtistAdapter);
            }
        }
    }
}
