package com.example.pic_chess;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


public class SecondMenuChessPicFragment extends DialogFragment {
    //Interface for transferring data between fragment an activity
    public interface OnClickSelection {
        void sendModeToThirdFromSecondChessPic(int mode);
    }

    private ImageButton mDrawingButton, mGuessingButton;
    private OnClickSelection mModeSelection;

    //Tags for fragment
    private static final String TAG2 = "SecondMenuFragmentTwo";

    public SecondMenuChessPicFragment() {
        // Required empty public constructor
    }

    //Invoke fragment
    public static SecondMenuChessPicFragment newInstance() {
        SecondMenuChessPicFragment fragment = new SecondMenuChessPicFragment();
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
        View view = inflater.inflate(R.layout.fragment_second_menu_chess_pic, container, false);

        //Find views
        mDrawingButton = view.findViewById(R.id.drawingPreviewButton);
        mGuessingButton = view.findViewById(R.id.guessingPreviewButton);


        //Set listeners
        mDrawingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Temporarily
                mModeSelection.sendModeToThirdFromSecondChessPic(0);
            }
        });
        mGuessingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Temporarily
                mModeSelection.sendModeToThirdFromSecondChessPic(1);
            }
        });

        return view;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mModeSelection = (SecondMenuChessPicFragment.OnClickSelection) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG2, "onAttach: ClassCastException : " + e.getMessage());
        }
    }
}