package com.example.pic_chess;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MusicLicensingFragment extends Fragment {
    public MusicLicensingFragment() {
        // Required empty public constructor
    }

    public static MusicLicensingFragment newInstance() {
        MusicLicensingFragment fragment = new MusicLicensingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_music_licensing, container, false);
    }
}