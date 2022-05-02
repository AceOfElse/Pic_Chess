package com.example.pic_chess;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Random;

public class ChessPicReceiveActivity extends AppCompatActivity {
    private ImageButton backButton, submitButton;
    private TextView timerText;
    private EditText descriptionTextField;
    private AlertDialog.Builder alertDialogue;
    private ImageView receivedImage;

    private CountDownTimer countDownTimer;
    private static final long START_TIME_IN_MILLISECOND = 601000;
    private boolean isTimerRunning;
    private long timeLeftInMilliSecond = START_TIME_IN_MILLISECOND;
    private boolean isTimed;
    private Bitmap receiverBitmap;
    private Random rg;
    private int promptNum;

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
        receivedImage = findViewById(R.id.receivedImg);

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
        receiveImage();

    }
    //Gets image from pictures folder and sets it as the image view
    private void receiveImage() {
        rg = new Random();
        promptNum = rg.nextInt(5)+1;
        Log.d("Value for promptNum", String.valueOf(promptNum));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        switch (promptNum){
            case 1:
                receiverBitmap = BitmapFactory.decodeFile("/storage/emulated/0/Pictures/ChessPic" + promptNum + ".png", options);
                   if(receiverBitmap == null){
                       Log .d("debug loading", "Bitmap is null");
                       receiveImage();
                    } else {
                       receivedImage.setImageBitmap(receiverBitmap);
                      // Toast.makeText(ChessPicReceiveActivity.this, "Image for prompt 1 loaded", Toast.LENGTH_LONG).show();
                   }
                break;
            case 2:
                receiverBitmap = BitmapFactory.decodeFile("/storage/emulated/0/Pictures/ChessPic" + promptNum + ".png", options);
                if(receiverBitmap == null){
                    Log .d("debug loading", "Bitmap is null");
                    receiveImage();
                } else {
                    receivedImage.setImageBitmap(receiverBitmap);
                    //Toast.makeText(ChessPicReceiveActivity.this, "Image for prompt 2 loaded", Toast.LENGTH_LONG).show();
                }
                break;
            case 3:
                    receiverBitmap = BitmapFactory.decodeFile("/storage/emulated/0/Pictures/ChessPic" + promptNum + ".png", options);
                if(receiverBitmap == null){
                    Log .d("debug loading", "Bitmap is null");
                    receiveImage();
                } else {
                    receivedImage.setImageBitmap(receiverBitmap);
                   // Toast.makeText(ChessPicReceiveActivity.this,"Image for prompt 3 loaded", Toast.LENGTH_LONG).show();
               }
                break;
            case 4:
                    receiverBitmap = BitmapFactory.decodeFile("/storage/emulated/0/Pictures/ChessPic" + promptNum + ".png", options);
                if(receiverBitmap == null){
                    Log .d("debug loading", "Bitmap is null");
                    receiveImage();
                } else {
                    receivedImage.setImageBitmap(receiverBitmap);
                  //  Toast.makeText(ChessPicReceiveActivity.this,"Image for prompt 4 loaded", Toast.LENGTH_LONG).show();
                }
                break;
            case 5:
                    receiverBitmap = BitmapFactory.decodeFile("/storage/emulated/0/Pictures/ChessPic" + promptNum + ".png", options);
                if(receiverBitmap == null){
                    Log .d("debug loading", "Bitmap is null");
                    receiveImage();
                } else {
                    receivedImage.setImageBitmap(receiverBitmap);
                    //Toast.makeText(ChessPicReceiveActivity.this,"Image for prompt 5 loaded", Toast.LENGTH_LONG).show();
                }
                break;
        }
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
                goBackViaLoadingActivity();
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
                goBackViaLoadingActivity();
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

    //Loading animation goes up when returning back to Home Activity.
    private void goBackViaLoadingActivity() {
        Intent loadingIntent = new Intent(ChessPicReceiveActivity.this, LoadingActivity.class);
        loadingIntent.putExtra("Class Code", 0);
        startActivity(loadingIntent);
        finish();
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