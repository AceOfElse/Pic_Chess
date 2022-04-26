package com.example.pic_chess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class LoadingActivity extends AppCompatActivity {
    private ProgressBar loadingBar;
    private int classCode = 0, timer;
    private boolean isTimed;
    private Class activityClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        loadingBar = findViewById(R.id.loadingProgressBar);
        loadingBar.setMax(100);
        loadingBar.setScaleY(3f);

        Intent inputIntent = getIntent();
        classCode = inputIntent.getIntExtra("Class Code", 0);
        isTimed = inputIntent.getBooleanExtra("TIME", false);
        timer = inputIntent.getIntExtra("TIMER", 601000);

        progressAnimation();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public void progressAnimation() {
        switch (classCode) {
            case 0:
                activityClass = HomeActivity.class;
                break;
            case 1:
                activityClass = PvpChessActivity.class;
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                activityClass = ChessPicActivity.class;
                break;
            case 6:
                activityClass = ChessPicReceiveActivity.class;
                break;
            default:
                activityClass = HomeActivity.class;
                break;

        }
        Intent targetIntent = new Intent(this, activityClass);
        targetIntent.putExtra("TIME", isTimed);
        targetIntent.putExtra("TIMER", timer);
        ProgressBarAnimation progressBarAnimation = new ProgressBarAnimation(this, loadingBar, 0f, 100f, targetIntent);
        progressBarAnimation.setDuration(1000);         //1 sec is only for test purpose, can make any seconds as wanted
        loadingBar.setAnimation(progressBarAnimation);
    }
}