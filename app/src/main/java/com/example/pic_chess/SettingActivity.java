package com.example.pic_chess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.ToggleButton;

public class SettingActivity extends AppCompatActivity implements TimerSettingFragment.OnClickSelected {
    private SeekBar volumeBar, sfxBar;
    private AudioManager audioManager;
    private MediaPlayer sfxMediaPlayer;
    private ToggleButton timerButton;
    private ImageButton backButton;
    private Button applyButton;
    private long[] timer;
    private int maxVolume, currentVolume;
    private float sfxVolume;

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
        sfxVolume = settingIntent.getFloatExtra("VOLUME", 50f);

        //Find views
        volumeBar = findViewById(R.id.volumeSeekBar);
        sfxBar = findViewById(R.id.sfxSeekBar);
        backButton = findViewById(R.id.backButtonSetting);
        timerButton = findViewById(R.id.timerSettingButton);
        applyButton = findViewById(R.id.applySettingButton);

        //Set audio
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        volumeBar.setMax(maxVolume);
        volumeBar.setProgress(currentVolume);
        sfxBar.setMax(maxVolume);
        sfxBar.setProgress((int)sfxVolume);

        //Set fragment
        timerSettingFragment = TimerSettingFragment.newInstance();
        transaction = getSupportFragmentManager().beginTransaction();
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
                homeIntent.putExtra("VOLUME", sfxVolume);
                setResult(19, homeIntent);
                finish();
            }
        });
        timerButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_down_top, R.anim.slide_up_top);
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

        //Set seek bar
        volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sfxBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                sfxVolume = seekBar.getProgress();
                playSFX(sfxVolume);
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
        stopSFX();
    }

    //Dealing with Android's back button
    public void onBackPressed() {
        Intent homeIntent = new Intent(SettingActivity.this, HomeActivity.class);
        homeIntent.putExtra("Timer List Back", timer);
        homeIntent.putExtra("VOLUME", sfxVolume);
        setResult(19, homeIntent);
        finish();
    }

    //Set sample sfx sound
    private void playSFX(float progress) {
        float realVolume = (float)(1 - (Math.log(maxVolume - progress) / Math.log(maxVolume)));
        if (sfxMediaPlayer == null) {
            sfxMediaPlayer = MediaPlayer.create(SettingActivity.this, R.raw.chess_slam_sfx);
            Log.d("volume", realVolume + "");
            sfxMediaPlayer.setVolume(realVolume, realVolume);
            sfxMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopSFX();
                }
            });
        }
        sfxMediaPlayer.start();
    }

    private void stopSFX() {
        if (sfxMediaPlayer != null) {
            sfxMediaPlayer.release();
            sfxMediaPlayer = null;
        }
    }
}