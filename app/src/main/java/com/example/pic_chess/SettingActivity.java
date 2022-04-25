package com.example.pic_chess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.ToggleButton;

public class SettingActivity extends AppCompatActivity implements TimerSettingFragment.OnClickSelected {
    private SeekBar volumeBar, sfxBar;
    private ToggleButton timerButton;
    private ImageButton backButton;
    private Button applyButton;
    private long[] timer;

    private TimerSettingFragment timerSettingFragment;
    private FragmentTransaction transaction;

    //Tag for fragment
    private static final String TAG = "Timer Setting Fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //Receive intent
        Intent settingIntent = getIntent();
        timer = settingIntent.getLongArrayExtra("Timer List");

        //Find views
        volumeBar = findViewById(R.id.volumeSeekBar);
        sfxBar = findViewById(R.id.sfxSeekBar);
        backButton = findViewById(R.id.backButtonSetting);
        timerButton = findViewById(R.id.timerSettingButton);
        applyButton = findViewById(R.id.applySettingButton);

        //Set fragment
        timerSettingFragment = TimerSettingFragment.newInstance();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_down, R.anim.slide_up);
        transaction.replace(R.id.timerSettingFragmentContainer, timerSettingFragment);
        transaction.addToBackStack(null);
        transaction.commit();
        transaction.hide(timerSettingFragment);

        //Set button listeners
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(SettingActivity.this, HomeActivity.class);
                homeIntent.putExtra("Timer List Back", timer);
                setResult(19, homeIntent);
                finish();
            }
        });
        timerButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                transaction = getSupportFragmentManager().beginTransaction();
                if (isChecked) {
                    timerSettingFragment.updateTimerText(timer);
                    transaction.show(timerSettingFragment);
                } else {
                    transaction.hide(timerSettingFragment);
                }
                transaction.commit();
            }
        });
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timerSettingFragment.setTimer();
            }
        });
    }

    //Implement interface
    public void sendAllTimerToActivity(long[] timerList) {
        timer = timerList;
    }

    //Set activity life cycles
    protected void onStart() {
        super.onStart();
    }

    protected void onRestart() {
        super.onRestart();
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onStop() {
        super.onStop();
    }

    //Dealing with Android's back button
    public void onBackPressed() {
        Intent homeIntent = new Intent(SettingActivity.this, HomeActivity.class);
        homeIntent.putExtra("Timer List Back", timer);
        setResult(19, homeIntent);
        finish();
    }
}