package com.example.pic_chess;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ToggleButton;

public class ToolBarFragmentTest extends Fragment {
    private ToggleButton mGrabButton, mColorButton, mEraseButton, mClearButton;
    private ImageView mColorImage;

    public ToolBarFragmentTest() {
        // Required empty public constructor
    }

    public static ToolBarFragmentTest newInstance() {
        ToolBarFragmentTest fragment = new ToolBarFragmentTest();
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
        View view = inflater.inflate(R.layout.fragment_tool_bar_test, container, false);

        //Find views
        mGrabButton = view.findViewById(R.id.grabButton);
        mColorButton = view.findViewById(R.id.colorButton);
        mEraseButton = view.findViewById(R.id.eraserButton);
        mClearButton = view.findViewById(R.id.clearButton);
        mColorImage = view.findViewById(R.id.colorPreviewImage);

        return view;
    }
}