package com.milushifa.miplayer.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.milushifa.miplayer.R;
import com.milushifa.miplayer.media.model.ModelType;
import com.milushifa.miplayer.receiver.UiDataReceiver;
import com.milushifa.miplayer.ui.fragment.tfragment.ExpanderFragment;
import com.milushifa.miplayer.ui.fragment.tfragment.backstack.BackStack;
import com.milushifa.miplayer.ui.fragment.tfragment.backstack.FragmentTransmitter;
import com.milushifa.miplayer.ui.fragment.tfragment.MainFragment;
import com.milushifa.miplayer.ui.fragment.tfragment.MiPlayerFragment;
import com.milushifa.miplayer.ui.fragment.tfragment.FragmentType;
import com.milushifa.miplayer.util.Flags;

public class MainActivity extends AppCompatActivity implements FragmentTransmitter {
    private FragmentManager manager;

    private UiDataReceiver mReceiver;

    // For Storing or memories the .... MODEL_TYPE and MODEL_ID
    private String model_Type;
    private long model_Id;

    public static int viewPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        registerReceiver();

        transmit(FragmentType.MAIN_FRAGMENT);
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
        // Memories things...
        model_Type = modelType;
        model_Id = contentId;

        switch(fragmentType){
            case FragmentType.MAIN_FRAGMENT:
                BackStack.pushBackStack(FragmentType.MAIN_FRAGMENT);
                transaction(new MainFragment()); break;
            case FragmentType.EXPANDER_FRAGMENT:
                BackStack.pushBackStack(FragmentType.EXPANDER_FRAGMENT);
                ExpanderFragment fragment = new ExpanderFragment(this);
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
    public void transmit(String fragmentType) {
        switch(fragmentType){
            case FragmentType.MAIN_FRAGMENT:
                BackStack.pushBackStack(FragmentType.MAIN_FRAGMENT);
                transaction(new MainFragment()); break;
            case FragmentType.PLAYER_FRAGMENT:
                BackStack.pushBackStack(FragmentType.PLAYER_FRAGMENT);
                transaction(new MiPlayerFragment()); break;
            default:
                finish();
        }
    }

    @Override
    public void onBackPressed() {
        String fragmentType = BackStack.popBackStack();
        if(fragmentType==null){
            BackStack.resetBackStack();
            finish();
        }else if(fragmentType.equals(FragmentType.EXPANDER_FRAGMENT)) {
            BackStack.popBackStack();
            transmit(FragmentType.EXPANDER_FRAGMENT, model_Type, model_Id);
        }else{
            if(FragmentType.MAIN_FRAGMENT.equals(fragmentType)) BackStack.resetBackStack();
            transmit(fragmentType);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver();
    }

//    private void registerReceiver(){
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("com.milushifa.miplayer.receiver.SEND_DURATION");
//        intentFilter.addAction("com.milushifa.miplayer.receiver.SEND_CURRENT_POSITION");
//        intentFilter.addAction("com.milushifa.miplayer.receiver.SEND_PLAYING_STATUS");
//        intentFilter.addAction("com.milushifa.miplayer.receiver.TRACK_CHANGE");
//        intentFilter.setPriority(1000);
//        registerReceiver(mReceiver, intentFilter);
//    }
//    private void unregisterReceiver(){
//        unregisterReceiver(mReceiver);
//    }
}

