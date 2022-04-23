package com.example.pic_chess;

import android.content.Context;
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

public class ToolBarFragmentTest extends Fragment implements ColorFragment.OnClickSelected {
    //Interface for transferring data to activity
    public interface OnClickSelected {
        void sendModeOfToolBar(int mode);
        void sendColorToChessPic(int color);
        void sendColorRGBToChessPic(int r, int g, int b);
    }

    private ToggleButton mGrabButton, mColorButton, mEraseButton;
    private ImageButton mClearButton;
    private ImageView mColorImage;
    private ImageSpan imageSpan;
    private OnClickSelected mOnClickedSelected;
    private ColorFragment colorFragment;
    private FragmentTransaction transaction;

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

        //Set fragments
        colorFragment = ColorFragment.newInstance();
        transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.colorFragmentContainer, colorFragment);
        transaction.addToBackStack(null);
        transaction.commit();
        transaction.hide(colorFragment);

        //Set listeners
        mColorButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                transaction = getActivity().getSupportFragmentManager().beginTransaction();
                if (isChecked) {
                    transaction.show(colorFragment);
                } else {
                    transaction.hide(colorFragment);
                }
                transaction.commit();
            }
        });

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

    //Implement interface
    public void sendColorToToolbar(int color, int r, int g, int b) {
        switch (color) {
            case 0:
                mOnClickedSelected.sendColorToChessPic(0);
                break;
            case 1:
                mOnClickedSelected.sendColorToChessPic(1);
                break;
            case 2:
                mOnClickedSelected.sendColorToChessPic(2);
                break;
            case 3:
                mOnClickedSelected.sendColorToChessPic(3);
                break;
            case 4:
                mOnClickedSelected.sendColorToChessPic(4);
                break;
            case 5:
                mOnClickedSelected.sendColorToChessPic(5);
                break;
            default:
                mOnClickedSelected.sendColorRGBToChessPic(r, g, b);
                break;
        }
    }
}