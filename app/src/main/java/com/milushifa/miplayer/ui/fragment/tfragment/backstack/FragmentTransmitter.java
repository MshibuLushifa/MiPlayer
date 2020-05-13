package com.milushifa.miplayer.ui.fragment.tfragment.backstack;

import androidx.fragment.app.Fragment;

public interface FragmentTransmitter {
    void transmit(String fragmentType, String modelType, long contentId);
}
