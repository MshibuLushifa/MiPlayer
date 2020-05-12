package com.milushifa.miplayer.ui.fragment.tfragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.milushifa.miplayer.R;


public class ExpanderFragment extends Fragment {
    private ImageView trackAlbumArtExpander;
    private TextView titleExpander, detailsExpander;
    private Toolbar mToolbar;
    private RecyclerView recyclerViewExpander;

    public ExpanderFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View expanderFragmentView = inflater.inflate(R.layout.fragment_expander, container, false);
        initComponents(expanderFragmentView);
        return expanderFragmentView;
    }
    private void initComponents(View rootView){
        trackAlbumArtExpander = rootView.findViewById(R.id.trackAlbumArtExpander);
        titleExpander = rootView.findViewById(R.id.titleExpander);
        detailsExpander = rootView.findViewById(R.id.detailsExpander);

        // Temporary
        mToolbar = rootView.findViewById(R.id.toolBarExpander);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        recyclerViewExpander = rootView.findViewById(R.id.recyclerViewExpander);
    }
}
