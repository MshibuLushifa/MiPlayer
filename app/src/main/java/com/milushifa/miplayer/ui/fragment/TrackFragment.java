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
import com.milushifa.miplayer.adapter.TrackAdapter;
import com.milushifa.miplayer.media.loader.TracksLoader;


public class TrackFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private TrackAdapter mTrackAdapter;

    public TrackFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View trackFragmentView = inflater.inflate(R.layout.fragment_track, container, false);
        initComponent(trackFragmentView);
        return trackFragmentView;
    }
    private void initComponent(View rootView){
        mRecyclerView = rootView.findViewById(R.id.recyclerViewTrackFragment);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        new LoadSong().execute();
    }

    public class LoadSong extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            if (getActivity() != null) {
                mTrackAdapter = new TrackAdapter(getContext(), new TracksLoader().getAllTracks(getContext()));
            }
            return "Execute";
        }

        @Override
        protected void onPostExecute(String s) {
            if (getActivity() != null) {
                mRecyclerView.setAdapter(mTrackAdapter);
            }
        }
    }
}
