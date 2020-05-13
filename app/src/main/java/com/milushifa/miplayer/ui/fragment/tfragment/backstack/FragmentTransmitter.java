package com.milushifa.miplayer.ui.fragment.tfragment.backstack;

public interface FragmentTransmitter {
    void transmit(String fragmentType, String modelType, long contentId);
    void transmit(String fragmentType);
}
