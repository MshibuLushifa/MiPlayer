package com.milushifa.miplayer.ui.fragment.tfragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.milushifa.miplayer.R;
import com.milushifa.miplayer.adapter.PagerAdapter;
import com.milushifa.miplayer.ui.fragment.tfragment.backstack.BackStack;
import com.milushifa.miplayer.ui.fragment.tfragment.backstack.FragmentTransmitter;


public class MainFragment extends Fragment {
    private Toolbar mToolbar;
    private ImageView lastTrackAlbumArt, lastTrackControllerToggleImageAsButton;
    private TextView lastTrackTitle, lastTrackDetails;
    private TabLayout mTabLayout;
    private ViewPager2 mViewPager;

    private View lastTrackView;

    private FragmentTransmitter mFragmentTransmitter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof FragmentTransmitter){
            this.mFragmentTransmitter = (FragmentTransmitter) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainFragmentView = inflater.inflate(R.layout.fragment_main, container, false);
        initComponents(mainFragmentView);
        return mainFragmentView;
    }
    private void initComponents(View rootView){
        mToolbar = rootView.findViewById(R.id.toolbarMain);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        // LastTrack MiPlayer...
        lastTrackAlbumArt = rootView.findViewById(R.id.lastTrackAlbumArt);
        lastTrackTitle = rootView.findViewById(R.id.lastTrackTitle);
        lastTrackDetails = rootView.findViewById(R.id.lastTrackDetails);
        lastTrackControllerToggleImageAsButton = rootView.findViewById(R.id.lastTrackControllerToggleImageAsButton);

        // TabLayout and ViewPager2...
        mTabLayout = rootView.findViewById(R.id.tabLayoutMain);
        mViewPager = rootView.findViewById(R.id.viewPagerMain);
        PagerAdapter pagerAdapter = new PagerAdapter(getActivity(), mFragmentTransmitter);
        mViewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(mTabLayout, mViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch(position){
                    case 0: tab.setText("tracks");break;
                    case 1: tab.setText("Albums");break;
                    case 2: tab.setText("Artists");break;
                    case 3: tab.setText("Genres");break;
                    case 4: tab.setText("Playlists");break;
                }
            }
        }).attach();

        lastTrackView = rootView.findViewById(R.id.layoutLastTrackPlayer);

        lastTrackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentTransmitter.transmit(FragmentType.PLAYER_FRAGMENT, null, 0);
            }
        });
    }
}
