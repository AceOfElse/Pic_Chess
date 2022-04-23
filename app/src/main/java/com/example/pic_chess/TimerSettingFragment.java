package com.example.pic_chess;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class TimerSettingFragment extends Fragment {
    public interface OnClickSelected {
        void sendTimerToActivity(int timeInMilliseconds);
    }

    private EditText mChessPvpTimerField, mChessPvrTimerField, mChessPicTimerField, mGuessingTimerField;
    private OnClickSelected mOnClickSelected;

    private static final String TAG = "Timer Setting Fragment";

    public TimerSettingFragment() {
        // Required empty public constructor
    }

    public static TimerSettingFragment newInstance() {
        TimerSettingFragment fragment = new TimerSettingFragment();
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
        View view =  inflater.inflate(R.layout.fragment_timer_setting, container, false);

        //Find views
        mChessPicTimerField = view.findViewById(R.id.chessPvpTimerField);
        mChessPvrTimerField = view.findViewById(R.id.chessPvrTimerField);
        mChessPicTimerField = view.findViewById(R.id.chessPicTimerField);
        mGuessingTimerField = view.findViewById(R.id.guessingTimerField);

        return view;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnClickSelected = (TimerSettingFragment.OnClickSelected) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException : " + e.getMessage());
        }
    }
}