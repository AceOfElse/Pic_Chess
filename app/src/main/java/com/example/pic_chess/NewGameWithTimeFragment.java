package com.example.pic_chess;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class NewGameWithTimeFragment extends Fragment {
    public interface OnClickSelected {
        void sendTimeModeToPvpActivity(int mode);
    }

    private Button mTime1Button, mTime2Button, mTime3Button, mTime4Button, mTime5Button, mTime6Button, mCustomButton, mCloseButton;
    private OnClickSelected mOnClickedSelected;

    private static final String TAG = "Time for Game Fragment";

    public NewGameWithTimeFragment() {
        // Required empty public constructor
    }

    public static NewGameWithTimeFragment newInstance() {
        NewGameWithTimeFragment fragment = new NewGameWithTimeFragment();
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
        View view = inflater.inflate(R.layout.fragment_new_game_with_time, container, false);

        //Find views
        mTime1Button = view.findViewById(R.id.timeButton1);
        mTime2Button = view.findViewById(R.id.timeButton2);
        mTime3Button = view.findViewById(R.id.timeButton3);
        mTime4Button = view.findViewById(R.id.timeButton4);
        mTime5Button = view.findViewById(R.id.timeButton5);
        mTime6Button = view.findViewById(R.id.timeButton6);
        mCustomButton = view.findViewById(R.id.customTimeButton);
        mCloseButton = view.findViewById(R.id.closeButton);

        //Set listener;
        mTime1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClickedSelected.sendTimeModeToPvpActivity(0);
            }
        });
        mTime2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClickedSelected.sendTimeModeToPvpActivity(1);
            }
        });
        mTime3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClickedSelected.sendTimeModeToPvpActivity(2);
            }
        });
        mTime4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClickedSelected.sendTimeModeToPvpActivity(3);
            }
        });
        mTime5Button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){mOnClickedSelected.sendTimeModeToPvpActivity(4); }
        });
        mTime6Button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){mOnClickedSelected.sendTimeModeToPvpActivity(5); }
        });
        mCustomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClickedSelected.sendTimeModeToPvpActivity(6);
            }
        });
        mCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClickedSelected.sendTimeModeToPvpActivity(7);
            }
        });
        return view;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnClickedSelected = (NewGameWithTimeFragment.OnClickSelected) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException : " + e.getMessage());
        }
    }
}