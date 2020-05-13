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
import com.milushifa.miplayer.adapter.GenreAdapter;
import com.milushifa.miplayer.media.loader.GenreLoader;
import com.milushifa.miplayer.ui.fragment.tfragment.backstack.FragmentTransmitter;


public class GenreFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private GenreAdapter mGenreAdapter;

    private FragmentTransmitter mFragmentTransmitter;


    public GenreFragment(FragmentTransmitter mFragmentTransmitter){
        this.mFragmentTransmitter = mFragmentTransmitter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View genreFragmentView = inflater.inflate(R.layout.fragment_genre, container, false);
        initComponent(genreFragmentView);
        return genreFragmentView;
    }
    private void initComponent(View rootView){
        mRecyclerView = rootView.findViewById(R.id.recyclerViewGenreFragment);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        new LoadGenre().execute();
    }

    public class LoadGenre extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            if (getActivity() != null) {
                mGenreAdapter = new GenreAdapter(getContext(), new GenreLoader().getAllGenre(getContext()), mFragmentTransmitter);
            }
            return "Execute";
        }

        @Override
        protected void onPostExecute(String s) {
            if (getActivity() != null) {
                mRecyclerView.setAdapter(mGenreAdapter);
            }
        }
    }
}
