package com.milushifa.miplayer.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;

import com.milushifa.miplayer.R;
import com.milushifa.miplayer.media.model.ModelType;
import com.milushifa.miplayer.ui.fragment.tfragment.ExpanderFragment;
import com.milushifa.miplayer.ui.fragment.tfragment.backstack.BackStack;
import com.milushifa.miplayer.ui.fragment.tfragment.backstack.FragmentTransmitter;
import com.milushifa.miplayer.ui.fragment.tfragment.MainFragment;
import com.milushifa.miplayer.ui.fragment.tfragment.MiPlayerFragment;
import com.milushifa.miplayer.ui.fragment.tfragment.FragmentType;
import com.milushifa.miplayer.util.Flags;

public class MainActivity extends AppCompatActivity implements FragmentTransmitter {

    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainFragment mainFragment = new MainFragment();
        ExpanderFragment expanderFragment = new ExpanderFragment();
        MiPlayerFragment miPlayerFragment = new MiPlayerFragment();


        Log.i(Flags.TAG, "onCreate: is called!");
        transmit(FragmentType.MAIN_FRAGMENT, null, 0);
    }

    private void transaction(Fragment fragment){
        manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.mainContainer, fragment)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void transmit(String fragmentType, String modelType, long contentId) {
        switch(fragmentType){
            case FragmentType.MAIN_FRAGMENT:
                BackStack.pushBackStack(FragmentType.MAIN_FRAGMENT);
                transaction(new MainFragment()); break;
            case FragmentType.EXPANDER_FRAGMENT:
                BackStack.pushBackStack(FragmentType.EXPANDER_FRAGMENT);
                ExpanderFragment fragment = new ExpanderFragment();
                Bundle data = new Bundle();
                data.putString(ModelType.MODEL_TYPE, modelType);
                data.putLong(ModelType.MODEL_ID, contentId);
                fragment.setArguments(data);
                transaction(fragment); break;
            case FragmentType.PLAYER_FRAGMENT:
                BackStack.pushBackStack(FragmentType.PLAYER_FRAGMENT);
                transaction(new MiPlayerFragment()); break;
            default:
                finish();
        }
    }

    @Override
    public void onBackPressed() {
        String frgmnt = BackStack.popBackStack();
        Log.i(Flags.TAG, "onBackPressed: fragment: " + frgmnt);
        if(frgmnt==null){
            BackStack.resetBackStack();
            finish();
        }else{
            if(FragmentType.MAIN_FRAGMENT.equals(frgmnt)) BackStack.resetBackStack();
            transmit(frgmnt, null, 0);
        }
    }
}

