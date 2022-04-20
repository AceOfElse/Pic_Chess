package com.example.pic_chess;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;


public class ColorFragment extends Fragment {
    private ImageButton blackButton, redButton, blueButton, greenButton, yellowButton, purpleButton;
    private EditText rTextField, gTextField, bTextField;

    public ColorFragment() {
        // Required empty public constructor
    }

    public static ColorFragment newInstance() {
        ColorFragment fragment = new ColorFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_color, container, false);

        //Find views
        blackButton = view.findViewById(R.id.blackColorButton);
        redButton = view.findViewById(R.id.redColorButton);
        blueButton = view.findViewById(R.id.blueColorButton);
        greenButton = view.findViewById(R.id.greenColorButton);
        yellowButton = view.findViewById(R.id.yellowColorButton);
        purpleButton = view.findViewById(R.id.purpleColorButton);
        rTextField = view.findViewById(R.id.rTextField);
        gTextField = view.findViewById(R.id.gTextField);
        bTextField = view.findViewById(R.id.bTextField);

        return view;
    }
}