package com.milushifa.miplayer.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import com.milushifa.miplayer.R;
import com.milushifa.miplayer.ui.fragment.tfragment.ExpanderFragment;
import com.milushifa.miplayer.ui.fragment.tfragment.FragmentTransmitter;
import com.milushifa.miplayer.ui.fragment.tfragment.MainFragment;
import com.milushifa.miplayer.ui.fragment.tfragment.MiPlayerFragment;
import com.milushifa.miplayer.util.Flags;
import com.milushifa.miplayer.util.FragmentType;

public class MainActivity extends AppCompatActivity implements FragmentTransmitter {
    public static final String ROOT_FRAGMENT = "root_fragment";

    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainFragment mainFragment = new MainFragment();
        ExpanderFragment expanderFragment = new ExpanderFragment();
        MiPlayerFragment miPlayerFragment = new MiPlayerFragment();

        transaction(mainFragment, ROOT_FRAGMENT);
    }

    private void transaction(Fragment fragment, String root){
        manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.mainContainer, fragment, root)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void transmit(String fragmentType) {
        switch(fragmentType){
            case FragmentType.MAIN_FRAGMENT:
                transaction(new MainFragment(), null); break;
            case FragmentType.EXPANDER_FRAGMENT:
                transaction(new ExpanderFragment(), null); break;
            case FragmentType.PLAYER_FRAGMENT:
                transaction(new MiPlayerFragment(), null); break;
        }
    }

    @Override
    public void onBackPressed() {
        if(manager.findFragmentByTag(ROOT_FRAGMENT)==null){
            transaction(new MainFragment(), ROOT_FRAGMENT);
        }else{
            finish();
        }
    }
}

