package com.example.pic_chess;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

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
    private ImageSpan imageSpan;
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

        //Set listeners
        mEraseButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    mGrabButton.setChecked(false);
                    mColorButton.setChecked(false);
                    mOnClickedSelected.sendModeOfToolBar(2);
                } else {
                    mOnClickedSelected.sendModeOfToolBar(3);
                }
            }
        });
        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClickedSelected.sendModeOfToolBar(4);
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
}