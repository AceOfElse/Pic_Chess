package com.example.pic_chess;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NewCanvasPromptFragment extends DialogFragment {

    //Interface for transferring data between fragment an activity
    public interface OnInputSelected {
        void sendInput(String inputName);
        void sendNewCanvasState(boolean promptState);
    }

    private TextView mPromptTitle;
    private Button mActionCreate, mActionCancel;
    private EditText mNameTextField;
    private String inputName;
    private OnInputSelected mOnInputSelected;

    //Tags for fragment
    private static final String TAG = "NewCanvasPromptFragment";

    public NewCanvasPromptFragment() {
        // Required empty public constructor
    }

    public static NewCanvasPromptFragment newInstance() {
        NewCanvasPromptFragment fragment = new NewCanvasPromptFragment();
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
        View view = inflater.inflate(R.layout.fragment_new_canvas_prompt, container, false);

        //Find views
        mPromptTitle = view.findViewById(R.id.promptTitleNCF);
        mNameTextField = view.findViewById(R.id.fileNameTextFieldNCF);
        mActionCreate = view.findViewById(R.id.createButtonNCF);
        mActionCancel = view.findViewById(R.id.cancelButtonNCF);

        //Set listeners
        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mActionCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputName = mNameTextField.getText().toString();
                if (!inputName.equals("")) {
                    mOnInputSelected.sendInput(inputName);
                    mOnInputSelected.sendNewCanvasState(true);
                    dismiss();
                } else
                    Toast.makeText(getContext(), "No name has been detected.", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnInputSelected = (OnInputSelected) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException : " + e.getMessage());
        }
    }
}