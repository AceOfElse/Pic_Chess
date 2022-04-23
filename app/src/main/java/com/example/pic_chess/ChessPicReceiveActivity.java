package com.example.pic_chess;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Locale;

public class ChessPicReceiveActivity extends AppCompatActivity {
    private ImageButton backButton, submitButton;
    private TextView timerText;
    private EditText descriptionTextField;
    private AlertDialog.Builder alertDialogue;

    private CountDownTimer countDownTimer;
    private static final long START_TIME_IN_MILLISECOND = 601000;
    private boolean isTimerRunning;
    private long timeLeftInMilliSecond = START_TIME_IN_MILLISECOND;
    private boolean isTimed;

//////Start Creation of Activity and Relevant Connections\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess_pic_receive);

        //Receive intent from home activity
        Intent chessPic  = getIntent();
        boolean isTimed = chessPic.getBooleanExtra("TIME", false);

        //Find views
        backButton = findViewById(R.id.backButtonCR);
        submitButton = findViewById(R.id.submitFileCR);
        timerText = findViewById(R.id.timerTextCR);
        descriptionTextField = findViewById(R.id.descriptionTextField);

        //Set up alert dialogue
        alertDialogue = new AlertDialog.Builder(ChessPicReceiveActivity.this);

        //Set button listeners
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //Set time textview
        if (isTimed) {
            timerText.setVisibility(View.VISIBLE);
            startTimer();
        } else {
            timerText.setVisibility(View.INVISIBLE);
        }
        updateCountDownText();
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

    //Dealing with app's back button
    private void onClickShowAlert(View view) {
        alertDialogue.setMessage(R.string.prompt_back_text);
        alertDialogue.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                finish();
            }
        });
        alertDialogue.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                startTimer();
            }
        });
        alertDialogue.create();
        alertDialogue.show();
    }

    //Dealing with Android's back button
    public void onBackPressed() {
        alertDialogue.setMessage(R.string.prompt_back_text);
        alertDialogue.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                finish();
            }
        });
        alertDialogue.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                startTimer();
            }
        });
        alertDialogue.create();
        alertDialogue.show();
    }
//////End Creation of Activity and Relevant Connections\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

//////Start Handling Timer\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMilliSecond, 1000) {
            @Override
            public void onTick(long milliSecondsUntilFinished) {
                timeLeftInMilliSecond = milliSecondsUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                isTimerRunning = false;
            }
        }.start();
        isTimerRunning = true;
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        isTimerRunning = false;
    }

    private void resetTimer() {
        timeLeftInMilliSecond = START_TIME_IN_MILLISECOND;
        updateCountDownText();
        isTimerRunning = false;
        startTimer();
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMilliSecond / 1000) / 60;
        int seconds = (int) (timeLeftInMilliSecond / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timerText.setText("TIME\n" + timeLeftFormatted);
    }
//////End Handling Timer\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
}