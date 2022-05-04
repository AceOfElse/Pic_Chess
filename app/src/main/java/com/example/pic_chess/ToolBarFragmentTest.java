package com.example.pic_chess;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ToggleButton;

public class ToolBarFragmentTest extends Fragment {
    //Interface for transferring data to activity
    public interface OnClickSelected {
        void sendModeOfToolBar(int mode);
    }

    private ToggleButton mGrabButton, mColorButton, mEraseButton;
    private ImageButton mClearButton;
    private ImageView mColorImage;
    private OnClickSelected mOnClickedSelected;

    //Tag for fragment
    private static final String TAG = "Toolbar Fragment";

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

        //Set initial color image
        setImageColor(Color.BLACK);

        //Set listeners
        mGrabButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    mColorButton.setChecked(false);
                    mEraseButton.setChecked(false);
                    mGrabButton.setBackgroundColor(Color.GRAY);
                    mOnClickedSelected.sendModeOfToolBar(0);
                } else {
                    mGrabButton.setBackgroundColor(Color.WHITE);
                    mOnClickedSelected.sendModeOfToolBar(1);
                }
            }
        });

        mColorButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    mGrabButton.setChecked(false);
                    mEraseButton.setChecked(false);
                    mColorButton.setBackgroundColor(Color.GRAY);
                    mOnClickedSelected.sendModeOfToolBar(2);
                } else {
                    mColorButton.setBackgroundColor(Color.WHITE);
                    mOnClickedSelected.sendModeOfToolBar(3);
                }
            }
        });

        mEraseButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    mGrabButton.setChecked(false);
                    mColorButton.setChecked(false);
                    mEraseButton.setBackgroundColor(Color.GRAY);
                    mOnClickedSelected.sendModeOfToolBar(4);
                } else {
                    mOnClickedSelected.sendModeOfToolBar(5);
                    mEraseButton.setBackgroundColor(Color.WHITE);
                }
            }
        });
        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGrabButton.setChecked(false);
                mColorButton.setChecked(false);
                mEraseButton.setChecked(false);
                mColorButton.setBackgroundColor(Color.WHITE);
                mEraseButton.setBackgroundColor(Color.WHITE);
                mOnClickedSelected.sendModeOfToolBar(6);
            }
        });

        return view;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnClickedSelected = (ToolBarFragmentTest.OnClickSelected) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException : " + e.getMessage());
        }
    }

    public void setImageColor(int color) {
        mColorImage.setBackgroundColor(color);
    }
}