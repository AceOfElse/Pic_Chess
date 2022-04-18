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


public class SecondMenuChessFragment extends DialogFragment {
    //Interface for transferring data between fragment an activity
    public interface OnClickSelection {
        void sendModeToThirdFromSecondChess(int mode);
    }

    private ImageView mFirstChoiceImage;
    private ImageButton mPvpPreviewButton, mPvrPreviewButton, mOnlinePreviewButton, mCustomGamePreviewButton;
    private OnClickSelection mModeSelection;

    //Tags for fragment
    private static final String TAG1 = "SecondMenuFragmentOne";

    public SecondMenuChessFragment() {
        // Required empty public constructor
    }

    //Invoke fragment
    public static SecondMenuChessFragment newInstance() {
        SecondMenuChessFragment fragment = new SecondMenuChessFragment();
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
        View view = inflater.inflate(R.layout.fragment_second_menu_chess, container, false);

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
                mModeSelection.sendModeToThirdFromSecondChess(0);
            }
        });
        mPvrPreviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Temporarily
                mModeSelection.sendModeToThirdFromSecondChess(1);
            }
        });
        mOnlinePreviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Temporarily
                mModeSelection.sendModeToThirdFromSecondChess(2);
            }
        });
        mCustomGamePreviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Temporarily
                mModeSelection.sendModeToThirdFromSecondChess(3);
            }
        });

        return view;
    }

    //Get an activity and attach to the fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mModeSelection = (SecondMenuChessFragment.OnClickSelection) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG1, "onAttach: ClassCastException : " + e.getMessage());
        }
    }
}