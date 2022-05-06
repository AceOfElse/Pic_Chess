package com.example.pic_chess;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class WinnerFragment extends Fragment {
    public interface OnClickSelected {
        void sendModeFromWinnerFragment(int mode);
    }

    private TextView mWinnerText;
    private ImageView mWinnerImage;
    private Button mNewButton, mReturnButton;
    private OnClickSelected mOnCLickSelected;
    private boolean isWhiteWin;

    private static final String TAG = "Winner Fragment";

    public WinnerFragment() {
        // Required empty public constructor
    }


    public static WinnerFragment newInstance() {
        WinnerFragment fragment = new WinnerFragment();
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
        View view =  inflater.inflate(R.layout.fragment_winner, container, false);

        //Find views
        mWinnerText = view.findViewById(R.id.winnerText);
        mWinnerImage = view.findViewById(R.id.winnerImage);
        mNewButton = view.findViewById(R.id.newButton);
        mReturnButton = view.findViewById(R.id.reaturnButton);

        //Set listeners
        mNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnCLickSelected.sendModeFromWinnerFragment(0);
            }
        });
        mReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnCLickSelected.sendModeFromWinnerFragment(1);
            }
        });
        return view;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnCLickSelected = (WinnerFragment.OnClickSelected) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException : " + e.getMessage());
        }
    }

    public void getData(Bundle bundle) {
        //Retrieve data from activity
        isWhiteWin = bundle.getBoolean("WINNER");

        //Set text and image view based on the data passed by bundle
        if (isWhiteWin) {
            mWinnerText.setText("White won!");
            mWinnerImage.setImageResource(R.drawable.whiteking);
        } else {
            mWinnerText.setText("Black won!");
            mWinnerImage.setImageResource(R.drawable.blackking);
        }
    }
}