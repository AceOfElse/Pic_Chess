package com.example.pic_chess;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;


public class ThirdMenuTimeFragment extends DialogFragment {
    //Interface for transferring data between fragment an activity
    public interface OnClickSelection {
        void sendModeToActivityFromThirdTime(int mode);
    }

    private ImageView mSecondChoiceImage;
    private ImageButton mTimedPreviewButton, mUnlimitedPreviewButton;
    private OnClickSelection mModeSelection;

    //Tag for fragment
    private static final String TAG3 = "ThirdMenuTimeFragment";


    public ThirdMenuTimeFragment() {
        // Required empty public constructor
    }

    //Invoke fragment
    public static ThirdMenuTimeFragment newInstance() {
        ThirdMenuTimeFragment fragment = new ThirdMenuTimeFragment();
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
        View view = inflater.inflate(R.layout.fragment_third_menu_time, container, false);

        //Find views
        mSecondChoiceImage = view.findViewById(R.id.secondChoiceImageTF);
        mTimedPreviewButton = view.findViewById(R.id.timedPreviewButton);
        mUnlimitedPreviewButton = view.findViewById(R.id.unlimitedPreviewButton);

        //Set listeners
        mTimedPreviewButton .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mModeSelection.sendModeToActivityFromThirdTime(0);
                getDialog().dismiss();
                //SecondMenuChessFragment.newInstance().getDialog().dismiss();
                //SecondMenuChessPicFragment.newInstance().getDialog().dismiss();
            }
        });
        mUnlimitedPreviewButton .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mModeSelection.sendModeToActivityFromThirdTime(1);
                getDialog().dismiss();
                //SecondMenuChessFragment.newInstance().getDialog().dismiss();
                //SecondMenuChessPicFragment.newInstance().getDialog().dismiss();
            }
        });

        return view;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mModeSelection= (ThirdMenuTimeFragment.OnClickSelection) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG3, "onAttach: ClassCastException : " + e.getMessage());
        }
    }
}
