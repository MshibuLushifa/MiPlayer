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
import com.milushifa.miplayer.media.loader.AlbumLoader;
import com.milushifa.miplayer.ui.fragment.tfragment.backstack.FragmentTransmitter;


public class AlbumFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private AlbumAdapter mAlbumAdapter;

    private FragmentTransmitter mFragmentTransmitter;

    public AlbumFragment(){}

    public AlbumFragment(FragmentTransmitter mFragmentTransmitter) {
        this.mFragmentTransmitter = mFragmentTransmitter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View albumFragmentView = inflater.inflate(R.layout.fragment_album, container, false);
        initComponent(albumFragmentView);
        return albumFragmentView;
    }
    private void initComponent(View rootView){
        mRecyclerView = rootView.findViewById(R.id.recyclerViewAlbumFragment);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        new LoadAlbum().execute();
    }

    public class LoadAlbum extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            if (getActivity() != null) {
                mAlbumAdapter = new AlbumAdapter(getContext(), new AlbumLoader().getAllAlbums(getContext()), mFragmentTransmitter);
            }
            return "Execute";
        }

        @Override
        protected void onPostExecute(String s) {
            if (getActivity() != null) {
                mRecyclerView.setAdapter(mAlbumAdapter);
            }
        }
    }
}
