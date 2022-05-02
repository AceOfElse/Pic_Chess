package com.example.pic_chess;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class HomeActivity extends AppCompatActivity implements SecondMenuChessFragment.OnClickSelection, SecondMenuChessPicFragment.OnClickSelection, ThirdMenuTimeFragment.OnClickSelection {
    private TextView titleText;
    private ImageButton settingButton, chessPreviewButton, drawPreviewButton;
    private AlertDialog.Builder alertDialogue;

    private SecondMenuChessFragment secondMenuChessFragment;
    private SecondMenuChessPicFragment secondMenuChessPicFragment;
    private ThirdMenuTimeFragment thirdMenuTimeFragment;
    private FragmentTransaction transaction;

    //Mode for activity
    private int firstMode;

    //default timer is 10 minutes
    private long[] timer = {601000, 601000, 601000, 601000};

    //Set tools to transfer data from activity to activity
    private ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == 19) {
                Intent settingIntent = result.getData();
                if (settingIntent != null) {
                    timer = settingIntent.getLongArrayExtra("Timer List Back");
                }
            }
        }
    });

    //Tags for fragment
    private static final String TAG1 = "SecondMenuChessFragment";
    private static final String TAG2 = "SecondMenuChessPicFragment";
    private static final String TAG3 = "ThirdMenuTimeFragment";

//////Start Creation of Activity and Relevant Connections\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        alertDialogue = new AlertDialog.Builder(HomeActivity.this);

        //Find views
        titleText = findViewById(R.id.homeTitleText);
        chessPreviewButton = findViewById(R.id.chessPreviewButton);
        drawPreviewButton = findViewById(R.id.drawPreviewButton);
        settingButton = findViewById(R.id.settingButton);

        //Set fragments
        secondMenuChessPicFragment = SecondMenuChessPicFragment.newInstance();
        secondMenuChessFragment = SecondMenuChessFragment.newInstance();
        thirdMenuTimeFragment = ThirdMenuTimeFragment.newInstance();

        //Set button listeners
        chessPreviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out);
                transaction.replace(R.id.secondMenuFragmentContainer, secondMenuChessFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        drawPreviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out);
                transaction.replace(R.id.secondMenuFragmentContainer, secondMenuChessPicFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSetting();
            }
        });

    }

    //Implements method from interfaces
    public void sendModeToThirdFromSecondChess(int mode) {
        switch(mode) {
            case 0:
                firstMode = 0;
                openThirdFragment();
                break;
            case 1:
                firstMode = 1;
                sendModeToActivityFromThirdTime(1);
                break;
            case 2:
                firstMode = 2;
                sendModeToActivityFromThirdTime(1);
                break;
            case 3:
                firstMode = 3;
                openThirdFragment();
                break;
            default:
                break;
        }
    }

    public void sendModeToThirdFromSecondChessPic(int mode) {
        switch(mode) {
            case 0:
                firstMode = 4;
                openThirdFragment();
                break;
            case 1:
                firstMode = 5;
                openThirdFragment();
                break;
            default:
                break;
        }
    }

    public void sendModeToActivityFromThirdTime(int mode) {
        //Take all fragments away from stack
        for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
            getSupportFragmentManager().popBackStack();
        }
        switch (firstMode) {
            case 0:
                if (mode == 0)
                    openPvpTimed(true);
                else
                    openPvpUntimed(false);
                break;
            case 1:
                openPvAI(mode == 0);
                break;
            case 2:
                openAIvAI(mode == 0);
                break;
            case 3:
                break;
            case 4:
                openChessPic(mode == 0);
                break;
            case 5:
                openChessPicReceive(mode == 0);
                break;
            default:
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
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onBackPressed() {
        //Check if the fragment is currently on screen. If there is no fragment, perform alert; otherwise, pop the current stack and go back to previous one.
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            alertDialogue.setMessage(R.string.prompt_quit_text);
            alertDialogue.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    finishAffinity();
                }
            });
            alertDialogue.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            alertDialogue.create();
            alertDialogue.show();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }
//////End Creation of Activity and Relevant Connections\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

//////Start Helper Methods within Home Activity\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    //Helper method to establish transaction by encoding images
    private void openThirdFragment() {
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out);
        transaction.replace(R.id.thirdMenuFragmentContainer, thirdMenuTimeFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void openPvpTimed(boolean isTimed) {
        Intent pvpIntent = new Intent(HomeActivity.this, LoadingActivity.class);
        pvpIntent.putExtra("TIME", isTimed);
        pvpIntent.putExtra("TIMER", timer[0]);
        pvpIntent.putExtra("Class Code", 1);
        startActivity(pvpIntent);
        onStop();
        onRestart();
    }

    private void openPvpUntimed(boolean isTimed) {
        Intent pvpIntent = new Intent(HomeActivity.this, LoadingActivity.class);
        pvpIntent.putExtra("Class Code", 2);
        startActivity(pvpIntent);
        onStop();
        onRestart();
    }

    private void openPvAI(boolean isTimed) {
        Intent pvpIntent = new Intent(HomeActivity.this, LoadingActivity.class);
        pvpIntent.putExtra("TIME", isTimed);
        pvpIntent.putExtra("TIMER", timer[1]);
        pvpIntent.putExtra("Class Code", 3);
        startActivity(pvpIntent);
        onStop();
        onRestart();
    }

    private void openAIvAI(boolean isTimed) {
        Intent pvpIntent = new Intent(HomeActivity.this, LoadingActivity.class);
        pvpIntent.putExtra("Class Code", 4);
        startActivity(pvpIntent);
        onStop();
        onRestart();
    }

    private void openChessPic(boolean isTimed) {
        Intent chessPicIntent = new Intent(HomeActivity.this, LoadingActivity.class);
        chessPicIntent.putExtra("TIME", isTimed);
        chessPicIntent.putExtra("TIMER", timer[2]);
        chessPicIntent.putExtra("Class Code", 6);
        startActivity(chessPicIntent);
        onStop();
        onRestart();
    }

    private void openChessPicReceive(boolean isTimed) {
        Intent chessPicReceiveIntent = new Intent(HomeActivity.this, LoadingActivity.class);
        chessPicReceiveIntent.putExtra("TIME", isTimed);
        chessPicReceiveIntent.putExtra("TIMER", timer[3]);
        chessPicReceiveIntent.putExtra("Class Code", 7);
        startActivity(chessPicReceiveIntent);
        onStop();
        onRestart();
    }

    private void openSetting() {
        Intent settingIntent = new Intent(HomeActivity.this, SettingActivity.class);
        settingIntent.putExtra("Timer List", timer);
        resultLauncher.launch(settingIntent);
        onStop();
        onRestart();
    }
//////End Helper Methods within Home Activity\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
}