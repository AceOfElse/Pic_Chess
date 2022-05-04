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
    private ImageButton backButton, submitButton,hintButton;
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
    private int promptNum,endAttempt,hintVisible;
    private String correctGuess;

//////Start Creation of Activity and Relevant Connections\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess_pic_receive);

        //Receive intent from home activity
        Intent chessPic  = getIntent();
        boolean isTimed = chessPic.getBooleanExtra("TIME", false);

        //Sets value for endAttempt which stops the program from infinitely calling receiveImage
        //This number will be increase as more prompts are added.
        endAttempt = 100;

        //This is the number of guesses the user makes until the hint button appears
        hintVisible = 5;

        //Find views
        backButton = findViewById(R.id.backButtonCR);
        submitButton = findViewById(R.id.submitFileCR);
        timerText = findViewById(R.id.timerTextCR);
        descriptionTextField = findViewById(R.id.descriptionTextField);
        receivedImage = findViewById(R.id.receivedImg);
        hintButton = findViewById(R.id.hintCR);

        //Set up alert dialogue
        alertDialogue = new AlertDialog.Builder(ChessPicReceiveActivity.this);

        //Set button listeners
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickShowAlert(view);
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkGuess();
            }
        });
        hintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                giveHint();
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

    private void giveHint() {
        switch (promptNum) {
            case 1:
                Toast.makeText(ChessPicReceiveActivity.this, getString(R.string.hint1), Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toast.makeText(ChessPicReceiveActivity.this, getString(R.string.hint2), Toast.LENGTH_LONG).show();
                break;
            case 3:
                Toast.makeText(ChessPicReceiveActivity.this, getString(R.string.hint3), Toast.LENGTH_LONG).show();
                break;
            case 4:
                Toast.makeText(ChessPicReceiveActivity.this, getString(R.string.hint4), Toast.LENGTH_LONG).show();
                break;
            case 5:
                Toast.makeText(ChessPicReceiveActivity.this, getString(R.string.hint5), Toast.LENGTH_LONG).show();
                break;
            case 6:
                Toast.makeText(ChessPicReceiveActivity.this, getString(R.string.hint6), Toast.LENGTH_LONG).show();
                break;
            case 7:
                Toast.makeText(ChessPicReceiveActivity.this, getString(R.string.hint7), Toast.LENGTH_LONG).show();
                break;
            case 8:
                Toast.makeText(ChessPicReceiveActivity.this, getString(R.string.hint8), Toast.LENGTH_LONG).show();
                break;
            case 9:
                Toast.makeText(ChessPicReceiveActivity.this, getString(R.string.hint9), Toast.LENGTH_LONG).show();
                break;
            case 10:
                Toast.makeText(ChessPicReceiveActivity.this, getString(R.string.hint10), Toast.LENGTH_LONG).show();
                break;
            case 11:
                Toast.makeText(ChessPicReceiveActivity.this, getString(R.string.hint11), Toast.LENGTH_LONG).show();
                break;
            case 12:
                Toast.makeText(ChessPicReceiveActivity.this, getString(R.string.hint12), Toast.LENGTH_LONG).show();
                break;
            case 13:
                Toast.makeText(ChessPicReceiveActivity.this, getString(R.string.hint13), Toast.LENGTH_LONG).show();
                break;
            case 14:
                Toast.makeText(ChessPicReceiveActivity.this, getString(R.string.hint14), Toast.LENGTH_LONG).show();
                break;
            case 15:
                Toast.makeText(ChessPicReceiveActivity.this, getString(R.string.hint15), Toast.LENGTH_LONG).show();
                break;
            case 16:
                Toast.makeText(ChessPicReceiveActivity.this, getString(R.string.hint16), Toast.LENGTH_LONG).show();
                break;
            case 17:
                Toast.makeText(ChessPicReceiveActivity.this, getString(R.string.hint17), Toast.LENGTH_LONG).show();
                break;
            case 18:
                Toast.makeText(ChessPicReceiveActivity.this, getString(R.string.hint18), Toast.LENGTH_LONG).show();
                break;
            case 19:
                Toast.makeText(ChessPicReceiveActivity.this, getString(R.string.hint19), Toast.LENGTH_LONG).show();
                break;
            case 20:
                Toast.makeText(ChessPicReceiveActivity.this, getString(R.string.hint20), Toast.LENGTH_LONG).show();
                break;
            case 21:
                Toast.makeText(ChessPicReceiveActivity.this, getString(R.string.hint21), Toast.LENGTH_LONG).show();
                break;
            case 22:
                Toast.makeText(ChessPicReceiveActivity.this, getString(R.string.hint22), Toast.LENGTH_LONG).show();
                break;
            case 23:
                Toast.makeText(ChessPicReceiveActivity.this, getString(R.string.hint23), Toast.LENGTH_LONG).show();
                break;
        }
    }

    //Checks the users input and sees if it matches the original prompt
    private void checkGuess() {
        int numChanged =0;
        if(hintVisible == 0){
            hintButton.setVisibility(View.VISIBLE);
        }
        String userInput = descriptionTextField.getText().toString();
        Log.d("Value for userinput", userInput);
        //First if sees if userInput and correctGuess are the same length.
        //If so then it moves on the check the letters. If not then it fails and tells the user their guess is wrong
        if(correctGuess.length() == userInput.length()){
            for(int x =0;x <correctGuess.length(); x++) {
                if(!userInput.equalsIgnoreCase(correctGuess)){
                    numChanged++;
                }
            }
            if(numChanged > 0 ){
                Toast.makeText(ChessPicReceiveActivity.this, "Your guess was WRONG. Please Guess again.", Toast.LENGTH_LONG).show();
                hintVisible--;
            } else{
                Toast.makeText(ChessPicReceiveActivity.this, "Your guess was Right!", Toast.LENGTH_LONG).show();
                receiveImage();
            }
        } else {
            Toast.makeText(ChessPicReceiveActivity.this, "Your guess was WRONG. Please Guess again.", Toast.LENGTH_LONG).show();
        }
    }//End of method!



    //Gets image from pictures folder and sets it as the image view
    private void receiveImage() {
        rg = new Random();
        promptNum = rg.nextInt(23)+1;
        Log.d("Value for promptNum", String.valueOf(promptNum));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        String temp;
        if(endAttempt == 0){
            receivedImage.setImageResource(R.drawable.default_receive);
            Toast.makeText(ChessPicReceiveActivity.this, "No Images! You must draw some first!", Toast.LENGTH_LONG).show();
        } else {
            switch (promptNum) {
                case 1:
                    receiverBitmap = BitmapFactory.decodeFile("/storage/emulated/0/Pictures/ChessPic" + promptNum + ".png", options);
                    if (receiverBitmap == null) {
                        Log.d("debug loading", "Bitmap is null");
                        endAttempt--;
                        receiveImage();
                    } else {
                        receivedImage.setImageBitmap(receiverBitmap);
                        temp = getString(R.string.Prompt1);
                        correctGuess = temp.substring(temp.lastIndexOf(" ") + 1);
                        Log.d("Value of correctGuess", correctGuess);
                        // Toast.makeText(ChessPicReceiveActivity.this, "Image for prompt 1 loaded", Toast.LENGTH_LONG).show();
                    }
                    break;
                case 2:
                    receiverBitmap = BitmapFactory.decodeFile("/storage/emulated/0/Pictures/ChessPic" + promptNum + ".png", options);
                    if (receiverBitmap == null) {
                        Log.d("debug loading", "Bitmap is null");
                        endAttempt--;
                        receiveImage();
                    } else {
                        receivedImage.setImageBitmap(receiverBitmap);
                        temp = getString(R.string.Prompt2);
                        correctGuess = temp.substring(temp.lastIndexOf(" ") + 1);
                        Log.d("Value of correctGuess", correctGuess);
                        //Toast.makeText(ChessPicReceiveActivity.this, "Image for prompt 2 loaded", Toast.LENGTH_LONG).show();
                    }
                    break;
                case 3:
                    receiverBitmap = BitmapFactory.decodeFile("/storage/emulated/0/Pictures/ChessPic" + promptNum + ".png", options);
                    if (receiverBitmap == null) {
                        Log.d("debug loading", "Bitmap is null");
                        endAttempt--;
                        receiveImage();
                    } else {
                        receivedImage.setImageBitmap(receiverBitmap);
                        temp = getString(R.string.Prompt3);
                        correctGuess = temp.substring(temp.lastIndexOf(" ") + 1);
                        Log.d("Value of correctGuess", correctGuess);
                        // Toast.makeText(ChessPicReceiveActivity.this,"Image for prompt 3 loaded", Toast.LENGTH_LONG).show();
                    }
                    break;
                case 4:
                    receiverBitmap = BitmapFactory.decodeFile("/storage/emulated/0/Pictures/ChessPic" + promptNum + ".png", options);
                    if (receiverBitmap == null) {
                        Log.d("debug loading", "Bitmap is null");
                        endAttempt--;
                        receiveImage();
                    } else {
                        receivedImage.setImageBitmap(receiverBitmap);
                        temp = getString(R.string.Prompt4);
                        correctGuess = temp.substring(temp.lastIndexOf(" ") + 1);
                        Log.d("Value of correctGuess", correctGuess);
                        //  Toast.makeText(ChessPicReceiveActivity.this,"Image for prompt 4 loaded", Toast.LENGTH_LONG).show();
                    }
                    break;
                case 5:
                    receiverBitmap = BitmapFactory.decodeFile("/storage/emulated/0/Pictures/ChessPic" + promptNum + ".png", options);
                    if (receiverBitmap == null) {
                        Log.d("debug loading", "Bitmap is null");
                        endAttempt--;
                        receiveImage();
                    } else {
                        receivedImage.setImageBitmap(receiverBitmap);
                        temp = getString(R.string.Prompt5);
                        correctGuess = temp.substring(temp.lastIndexOf(" ") + 1);
                        Log.d("Value of correctGuess", correctGuess);
                        //Toast.makeText(ChessPicReceiveActivity.this,"Image for prompt 5 loaded", Toast.LENGTH_LONG).show();
                    }
                    break;
                case 6:
                    receiverBitmap = BitmapFactory.decodeFile("/storage/emulated/0/Pictures/ChessPic" + promptNum + ".png", options);
                    if (receiverBitmap == null) {
                        Log.d("debug loading", "Bitmap is null");
                        endAttempt--;
                        receiveImage();
                    } else {
                        receivedImage.setImageBitmap(receiverBitmap);
                        temp = getString(R.string.Prompt6);
                        correctGuess = temp.substring(temp.lastIndexOf(" ") + 1);
                        Log.d("Value of correctGuess", correctGuess);
                        //Toast.makeText(ChessPicReceiveActivity.this,"Image for prompt 5 loaded", Toast.LENGTH_LONG).show();
                    }
                    break;
                case 7:
                    receiverBitmap = BitmapFactory.decodeFile("/storage/emulated/0/Pictures/ChessPic" + promptNum + ".png", options);
                    if (receiverBitmap == null) {
                        Log.d("debug loading", "Bitmap is null");
                        endAttempt--;
                        receiveImage();
                    } else {
                        receivedImage.setImageBitmap(receiverBitmap);
                        temp = getString(R.string.Prompt7);
                        correctGuess = temp.substring(temp.lastIndexOf(" ") + 1);
                        Log.d("Value of correctGuess", correctGuess);
                        //Toast.makeText(ChessPicReceiveActivity.this,"Image for prompt 5 loaded", Toast.LENGTH_LONG).show();
                    }
                    break;
                case 8:
                    receiverBitmap = BitmapFactory.decodeFile("/storage/emulated/0/Pictures/ChessPic" + promptNum + ".png", options);
                    if (receiverBitmap == null) {
                        Log.d("debug loading", "Bitmap is null");
                        endAttempt--;
                        receiveImage();
                    } else {
                        receivedImage.setImageBitmap(receiverBitmap);
                        temp = getString(R.string.Prompt8);
                        correctGuess = temp.substring(temp.lastIndexOf(" ") + 1);
                        Log.d("Value of correctGuess", correctGuess);
                        //Toast.makeText(ChessPicReceiveActivity.this,"Image for prompt 5 loaded", Toast.LENGTH_LONG).show();
                    }
                    break;
                case 9:
                    receiverBitmap = BitmapFactory.decodeFile("/storage/emulated/0/Pictures/ChessPic" + promptNum + ".png", options);
                    if (receiverBitmap == null) {
                        Log.d("debug loading", "Bitmap is null");
                        endAttempt--;
                        receiveImage();
                    } else {
                        receivedImage.setImageBitmap(receiverBitmap);
                        temp = getString(R.string.Prompt9);
                        correctGuess = temp.substring(temp.lastIndexOf(" ") + 1);
                        Log.d("Value of correctGuess", correctGuess);
                        //Toast.makeText(ChessPicReceiveActivity.this,"Image for prompt 5 loaded", Toast.LENGTH_LONG).show();
                    }
                    break;
                case 10:
                    receiverBitmap = BitmapFactory.decodeFile("/storage/emulated/0/Pictures/ChessPic" + promptNum + ".png", options);
                    if (receiverBitmap == null) {
                        Log.d("debug loading", "Bitmap is null");
                        endAttempt--;
                        receiveImage();
                    } else {
                        receivedImage.setImageBitmap(receiverBitmap);
                        temp = getString(R.string.Prompt10);
                        correctGuess = temp.substring(temp.lastIndexOf(" ") + 1);
                        Log.d("Value of correctGuess", correctGuess);
                        //Toast.makeText(ChessPicReceiveActivity.this,"Image for prompt 5 loaded", Toast.LENGTH_LONG).show();
                    }
                    break;
                case 11:
                    receiverBitmap = BitmapFactory.decodeFile("/storage/emulated/0/Pictures/ChessPic" + promptNum + ".png", options);
                    if (receiverBitmap == null) {
                        Log.d("debug loading", "Bitmap is null");
                        endAttempt--;
                        receiveImage();
                    } else {
                        receivedImage.setImageBitmap(receiverBitmap);
                        temp = getString(R.string.Prompt11);
                        correctGuess = temp.substring(temp.lastIndexOf(" ") + 1);
                        Log.d("Value of correctGuess", correctGuess);
                        //Toast.makeText(ChessPicReceiveActivity.this,"Image for prompt 5 loaded", Toast.LENGTH_LONG).show();
                    }
                    break;
                case 12:
                    receiverBitmap = BitmapFactory.decodeFile("/storage/emulated/0/Pictures/ChessPic" + promptNum + ".png", options);
                    if (receiverBitmap == null) {
                        Log.d("debug loading", "Bitmap is null");
                        endAttempt--;
                        receiveImage();
                    } else {
                        receivedImage.setImageBitmap(receiverBitmap);
                        temp = getString(R.string.Prompt12);
                        correctGuess = temp.substring(temp.lastIndexOf(" ") + 1);
                        Log.d("Value of correctGuess", correctGuess);
                        //Toast.makeText(ChessPicReceiveActivity.this,"Image for prompt 5 loaded", Toast.LENGTH_LONG).show();
                    }
                    break;
                case 13:
                    receiverBitmap = BitmapFactory.decodeFile("/storage/emulated/0/Pictures/ChessPic" + promptNum + ".png", options);
                    if (receiverBitmap == null) {
                        Log.d("debug loading", "Bitmap is null");
                        endAttempt--;
                        receiveImage();
                    } else {
                        receivedImage.setImageBitmap(receiverBitmap);
                        temp = getString(R.string.Prompt13);
                        correctGuess = temp.substring(temp.lastIndexOf(" ") + 1);
                        Log.d("Value of correctGuess", correctGuess);
                        //Toast.makeText(ChessPicReceiveActivity.this,"Image for prompt 5 loaded", Toast.LENGTH_LONG).show();
                    }
                    break;
                case 14:
                    receiverBitmap = BitmapFactory.decodeFile("/storage/emulated/0/Pictures/ChessPic" + promptNum + ".png", options);
                    if (receiverBitmap == null) {
                        Log.d("debug loading", "Bitmap is null");
                        endAttempt--;
                        receiveImage();
                    } else {
                        receivedImage.setImageBitmap(receiverBitmap);
                        temp = getString(R.string.Prompt14);
                        correctGuess = temp.substring(temp.lastIndexOf(" ") + 1);
                        Log.d("Value of correctGuess", correctGuess);
                        //Toast.makeText(ChessPicReceiveActivity.this,"Image for prompt 5 loaded", Toast.LENGTH_LONG).show();
                    }
                    break;
                case 15:
                    receiverBitmap = BitmapFactory.decodeFile("/storage/emulated/0/Pictures/ChessPic" + promptNum + ".png", options);
                    if (receiverBitmap == null) {
                        Log.d("debug loading", "Bitmap is null");
                        endAttempt--;
                        receiveImage();
                    } else {
                        receivedImage.setImageBitmap(receiverBitmap);
                        temp = getString(R.string.Prompt15);
                        correctGuess = temp.substring(temp.lastIndexOf(" ") + 1);
                        Log.d("Value of correctGuess", correctGuess);
                        //Toast.makeText(ChessPicReceiveActivity.this,"Image for prompt 5 loaded", Toast.LENGTH_LONG).show();
                    }
                    break;
                case 16:
                    receiverBitmap = BitmapFactory.decodeFile("/storage/emulated/0/Pictures/ChessPic" + promptNum + ".png", options);
                    if (receiverBitmap == null) {
                        Log.d("debug loading", "Bitmap is null");
                        endAttempt--;
                        receiveImage();
                    } else {
                        receivedImage.setImageBitmap(receiverBitmap);
                        temp = getString(R.string.Prompt16);
                        correctGuess = temp.substring(temp.lastIndexOf(" ") + 1);
                        Log.d("Value of correctGuess", correctGuess);
                        //Toast.makeText(ChessPicReceiveActivity.this,"Image for prompt 5 loaded", Toast.LENGTH_LONG).show();
                    }
                    break;
                case 17:
                    receiverBitmap = BitmapFactory.decodeFile("/storage/emulated/0/Pictures/ChessPic" + promptNum + ".png", options);
                    if (receiverBitmap == null) {
                        Log.d("debug loading", "Bitmap is null");
                        endAttempt--;
                        receiveImage();
                    } else {
                        receivedImage.setImageBitmap(receiverBitmap);
                        temp = getString(R.string.Prompt17);
                        correctGuess = temp.substring(temp.lastIndexOf(" ") + 1);
                        Log.d("Value of correctGuess", correctGuess);
                        //Toast.makeText(ChessPicReceiveActivity.this,"Image for prompt 5 loaded", Toast.LENGTH_LONG).show();
                    }
                    break;
                case 18:
                    receiverBitmap = BitmapFactory.decodeFile("/storage/emulated/0/Pictures/ChessPic" + promptNum + ".png", options);
                    if (receiverBitmap == null) {
                        Log.d("debug loading", "Bitmap is null");
                        endAttempt--;
                        receiveImage();
                    } else {
                        receivedImage.setImageBitmap(receiverBitmap);
                        temp = getString(R.string.Prompt18);
                        correctGuess = temp.substring(temp.lastIndexOf(" ") + 1);
                        Log.d("Value of correctGuess", correctGuess);
                        //Toast.makeText(ChessPicReceiveActivity.this,"Image for prompt 5 loaded", Toast.LENGTH_LONG).show();
                    }
                    break;
                case 19:
                    receiverBitmap = BitmapFactory.decodeFile("/storage/emulated/0/Pictures/ChessPic" + promptNum + ".png", options);
                    if (receiverBitmap == null) {
                        Log.d("debug loading", "Bitmap is null");
                        endAttempt--;
                        receiveImage();
                    } else {
                        receivedImage.setImageBitmap(receiverBitmap);
                        temp = getString(R.string.Prompt19);
                        correctGuess = temp.substring(temp.lastIndexOf(" ") + 1);
                        Log.d("Value of correctGuess", correctGuess);
                        //Toast.makeText(ChessPicReceiveActivity.this,"Image for prompt 5 loaded", Toast.LENGTH_LONG).show();
                    }
                    break;
                case 20:
                    receiverBitmap = BitmapFactory.decodeFile("/storage/emulated/0/Pictures/ChessPic" + promptNum + ".png", options);
                    if (receiverBitmap == null) {
                        Log.d("debug loading", "Bitmap is null");
                        endAttempt--;
                        receiveImage();
                    } else {
                        receivedImage.setImageBitmap(receiverBitmap);
                        temp = getString(R.string.Prompt20);
                        correctGuess = temp.substring(temp.lastIndexOf(" ") + 1);
                        Log.d("Value of correctGuess", correctGuess);
                        //Toast.makeText(ChessPicReceiveActivity.this,"Image for prompt 5 loaded", Toast.LENGTH_LONG).show();
                    }
                    break;
                case 21:
                    receiverBitmap = BitmapFactory.decodeFile("/storage/emulated/0/Pictures/ChessPic" + promptNum + ".png", options);
                    if (receiverBitmap == null) {
                        Log.d("debug loading", "Bitmap is null");
                        endAttempt--;
                        receiveImage();
                    } else {
                        receivedImage.setImageBitmap(receiverBitmap);
                        temp = getString(R.string.Prompt21);
                        correctGuess = temp.substring(temp.lastIndexOf(" ") + 1);
                        Log.d("Value of correctGuess", correctGuess);
                        //Toast.makeText(ChessPicReceiveActivity.this,"Image for prompt 5 loaded", Toast.LENGTH_LONG).show();
                    }
                    break;
                case 22:
                    receiverBitmap = BitmapFactory.decodeFile("/storage/emulated/0/Pictures/ChessPic" + promptNum + ".png", options);
                    if (receiverBitmap == null) {
                        Log.d("debug loading", "Bitmap is null");
                        endAttempt--;
                        receiveImage();
                    } else {
                        receivedImage.setImageBitmap(receiverBitmap);
                        temp = getString(R.string.Prompt22);
                        correctGuess = temp.substring(temp.lastIndexOf(" ") + 1);
                        Log.d("Value of correctGuess", correctGuess);
                        //Toast.makeText(ChessPicReceiveActivity.this,"Image for prompt 5 loaded", Toast.LENGTH_LONG).show();
                    }
                    break;
                case 23:
                    receiverBitmap = BitmapFactory.decodeFile("/storage/emulated/0/Pictures/ChessPic" + promptNum + ".png", options);
                    if (receiverBitmap == null) {
                        Log.d("debug loading", "Bitmap is null");
                        endAttempt--;
                        receiveImage();
                    } else {
                        receivedImage.setImageBitmap(receiverBitmap);
                        temp = getString(R.string.Prompt23);
                        correctGuess = temp.substring(temp.lastIndexOf(" ") + 1);
                        Log.d("Value of correctGuess", correctGuess);
                        //Toast.makeText(ChessPicReceiveActivity.this,"Image for prompt 5 loaded", Toast.LENGTH_LONG).show();
                    }
                    break;
            }
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