package com.example.pic_chess;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ToggleButton;


public class ColorFragment extends Fragment {
    public interface OnClickSelected {
        void sendColorToToolbar(int color, int r, int g, int b);
    }

    private ToggleButton blackButton, redButton, blueButton, greenButton, yellowButton, purpleButton;
    private ImageButton applyButton;
    private EditText rTextField, gTextField, bTextField;
    private OnClickSelected mOnClickSelected;

    //Tag for fragment
    private static final String TAG = "Color fragment";


    public ColorFragment() {
        // Required empty public constructor
    }

    public static ColorFragment newInstance() {
        ColorFragment fragment = new ColorFragment();
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
        View view = inflater.inflate(R.layout.fragment_color, container, false);

        //Find views
        blackButton = view.findViewById(R.id.blackColorButton);
        redButton = view.findViewById(R.id.redColorButton);
        blueButton = view.findViewById(R.id.blueColorButton);
        greenButton = view.findViewById(R.id.greenColorButton);
        yellowButton = view.findViewById(R.id.yellowColorButton);
        purpleButton = view.findViewById(R.id.purpleColorButton);
        applyButton = view.findViewById(R.id.applyButton);
        rTextField = view.findViewById(R.id.rTextField);
        gTextField = view.findViewById(R.id.gTextField);
        bTextField = view.findViewById(R.id.bTextField);

        //Set listeners
        blackButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    mOnClickSelected.sendColorToToolbar(0, 0, 0, 0);
            }
        });
        redButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    mOnClickSelected.sendColorToToolbar(1, 0, 0, 0);
            }
        });
        blueButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    mOnClickSelected.sendColorToToolbar(2, 0, 0, 0);
            }
        });
        greenButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    mOnClickSelected.sendColorToToolbar(3, 0, 0, 0);
            }
        });
        yellowButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    mOnClickSelected.sendColorToToolbar(4, 0, 0, 0);
            }
        });
        purpleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    mOnClickSelected.sendColorToToolbar(5, 0, 0, 0);
            }
        });
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClickSelected.sendColorToToolbar(6, Integer.parseInt(rTextField.getText().toString()), Integer.parseInt(gTextField.getText().toString()), Integer.parseInt(bTextField.getText().toString()));
            }
        });

        return view;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnClickSelected = (ColorFragment.OnClickSelected) context;
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException : " + e.getMessage());
        }
    }
}