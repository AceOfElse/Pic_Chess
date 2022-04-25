package com.example.pic_chess;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ToggleButton;

public class ToolbarFragment extends DialogFragment {
    private ToggleButton mGrabButton, mColorButton, mEraseButton, mClearButton;
    private ImageView mColorImage;

    public ToolbarFragment() {
        // Required empty public constructor
    }

    public static ToolbarFragment newInstance() {
        ToolbarFragment fragment = new ToolbarFragment();
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
        View view = inflater.inflate(R.layout.fragment_toolbar, container, false);

        //Find views
        mGrabButton = view.findViewById(R.id.grabButton);
        mColorButton = view.findViewById(R.id.colorButton);
        mEraseButton = view.findViewById(R.id.eraserButton);
        mClearButton = view.findViewById(R.id.clearButton);
        mColorImage = view.findViewById(R.id.colorPreviewImage);

        return view;
    }
}