package com.example.pic_chess;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;


public class SecondMenuFragmentOne extends DialogFragment {
    //Interface for transferring data between fragment an activity
    public interface OnClickSelection {
        void sendMode(int mode);
    }

    private ImageView mFirstChoiceImage;
    private ImageButton mPvpPreviewButton, mPvrPreviewButton, mOnlinePreviewButton, mCustomGamePreviewButton;
    private OnClickSelection mModeSelection;

    //Tags for fragment
    private static final String TAG1 = "SecondMenuFragmentOne";

    public SecondMenuFragmentOne() {
        // Required empty public constructor
    }

    //Invoke fragment
    public static SecondMenuFragmentOne newInstance() {
        SecondMenuFragmentOne fragment = new SecondMenuFragmentOne();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set the dialogue theme to be full screen. Need to modify the theme.xml file
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogueTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second_menu_one, container, false);

        //Find views
        mFirstChoiceImage = view.findViewById(R.id.firstChoiceImage);
        mPvpPreviewButton = view.findViewById(R.id.pvpPreviewButton);
        mPvrPreviewButton = view.findViewById(R.id.pvrPreviewButton);
        mOnlinePreviewButton = view.findViewById(R.id.onlinePreviewButton);
        mCustomGamePreviewButton = view.findViewById(R.id.customGamePreviewButton);

        //Set listeners
        mPvpPreviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Temporarily
                mModeSelection.sendMode(0);
                getDialog().dismiss();
            }
        });
        mPvrPreviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Temporarily
                mModeSelection.sendMode(1);
                getDialog().dismiss();
            }
        });
        mOnlinePreviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Temporarily
                mModeSelection.sendMode(2);
                getDialog().dismiss();
            }
        });
        mCustomGamePreviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Temporarily
                mModeSelection.sendMode(3);
                getDialog().dismiss();
            }
        });

        return view;
    }

    //Get an activity and attach to the fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mModeSelection = (SecondMenuFragmentOne.OnClickSelection) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG1, "onAttach: ClassCastException : " + e.getMessage());
        }
    }
}