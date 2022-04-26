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
        void sendAllTimerToActivity(long[] timerList);
    }

    private EditText mChessPvpTimerField, mChessPvrTimerField, mChessPicTimerField, mGuessingTimerField;
    private OnClickSelected mOnClickSelected;
    private long[] timer = new long[4];

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
        mChessPvpTimerField = view.findViewById(R.id.chessPvpTimerField);
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

    public void setTimer() {
        timer[0] = convertToTimeMilliSecond(mChessPvpTimerField.getText().toString());
        timer[1] = convertToTimeMilliSecond(mChessPvrTimerField.getText().toString());
        timer[2] = convertToTimeMilliSecond(mChessPicTimerField.getText().toString());
        timer[3] = convertToTimeMilliSecond(mGuessingTimerField.getText().toString());
        mOnClickSelected.sendAllTimerToActivity(timer);
    }

    public void updateTimerText(long[] timerList) {
        for (int i = 0; i < timerList.length; i++) {
            timer[i] = timerList[i];
        }
        mChessPvpTimerField.setText(String.valueOf(convertToTimeMinute(timer[0])));
        mChessPvrTimerField.setText(String.valueOf(convertToTimeMinute(timer[1])));
        mChessPicTimerField.setText(String.valueOf(convertToTimeMinute(timer[2])));
        mGuessingTimerField.setText(String.valueOf(convertToTimeMinute(timer[3])));
    }

    private long convertToTimeMilliSecond(String time) {
        return Long.parseLong(time) * 60 * 1000 + 1000;
    }


    private int convertToTimeMinute(long time) {
        return (int)(((time - 1000) / 1000 / 60));
    }
}