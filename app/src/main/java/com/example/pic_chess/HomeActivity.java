package com.example.pic_chess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity implements SecondMenuChessFragment.OnClickSelection, SecondMenuChessPicFragment.OnClickSelection, ThirdMenuTimeFragment.OnClickSelection {
    private TextView titleText;
    private ImageButton settingButton, chessPreviewButton, drawPreviewButton;
    private AlertDialog.Builder alertDialogue;
    private SecondMenuChessFragment secondMenuChessFragment;
    private SecondMenuChessPicFragment secondMenuChessPicFragment;
    private ThirdMenuTimeFragment thirdMenuTimeFragment;
    private FragmentTransaction transaction;
    private int firstMode, secondMode, thirdMode;

    //Tags for fragment
    private static final String TAG1 = "SecondMenuFragmentOne";
    private static final String TAG2 = "SecondMenuFragmentTwo";
    private static final String TAG3 = "ThirdMenuTimeFragment";

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
        secondMenuChessFragment = SecondMenuChessFragment.newInstance();
        secondMenuChessPicFragment = SecondMenuChessPicFragment.newInstance();
        thirdMenuTimeFragment = ThirdMenuTimeFragment.newInstance();

        //Set button listeners
        chessPreviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //transaction = getSupportFragmentManager().beginTransaction();
                //transaction.replace(R.id.secondMenuFragmentContainer, secondMenuFragmentOne);
                //transaction.commit();
                secondMenuChessFragment.show(getSupportFragmentManager(), "Open second menu Chess");
            }
        });
        drawPreviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                secondMenuChessPicFragment.show(getSupportFragmentManager(), "Open second menu ChessPic");
            }
        });
    }

    //Implements method from interfaces
    public void sendModeToThirdFromSecondChess(int mode) {
        switch(mode) {
            case 0:
                firstMode = 0;
                thirdMenuTimeFragment.show(getSupportFragmentManager(), "Open third menu Time");
                break;
            case 1:
                firstMode = 1;
                thirdMenuTimeFragment.show(getSupportFragmentManager(), "Open third menu Time");
                break;
            case 2:
                firstMode = 2;
                thirdMenuTimeFragment.show(getSupportFragmentManager(), "Open third menu Time");
                break;
            case 3:
                firstMode = 3;
                thirdMenuTimeFragment.show(getSupportFragmentManager(), "Open third menu Time");
                break;
            default:
                break;
        }
    }

    public void sendModeToThirdFromSecondChessPic(int mode) {
        switch(mode) {
            case 0:
                firstMode = 4;
                thirdMenuTimeFragment.show(getSupportFragmentManager(), "Open third menu Time");
                break;
            case 1:
                firstMode = 5;
                thirdMenuTimeFragment.show(getSupportFragmentManager(), "Open third menu Time");
                break;
            default:
                break;
        }
    }

    public void sendModeToActivityFromThirdTime(int mode) {
        switch(mode) {
            case 0:
                switch (firstMode) {
                    case 0:
                        secondMenuChessFragment.getDialog().dismiss();
                        thirdMenuTimeFragment.getDialog().dismiss();
                        openPvp(true);
                        break;
                    case 1:
                        secondMenuChessFragment.getDialog().dismiss();
                        thirdMenuTimeFragment.getDialog().dismiss();
                        break;
                    case 2:
                        secondMenuChessFragment.getDialog().dismiss();
                        thirdMenuTimeFragment.getDialog().dismiss();
                        break;
                    case 3:
                        secondMenuChessFragment.getDialog().dismiss();
                        thirdMenuTimeFragment.getDialog().dismiss();
                        break;
                    case 4:
                        secondMenuChessPicFragment.getDialog().dismiss();
                        thirdMenuTimeFragment.getDialog().dismiss();
                        openChessPic(true);
                        break;
                    case 5:
                        secondMenuChessPicFragment.getDialog().dismiss();
                        thirdMenuTimeFragment.getDialog().dismiss();
                        openChessPicReceive(true);
                        break;
                    default:
                        secondMenuChessFragment.getDialog().dismiss();
                        secondMenuChessPicFragment.getDialog().dismiss();
                        thirdMenuTimeFragment.getDialog().dismiss();
                        break;
                }
                break;
            case 1:
                switch (firstMode) {
                    case 0:
                        secondMenuChessFragment.getDialog().dismiss();
                        thirdMenuTimeFragment.getDialog().dismiss();
                        openPvp(false);
                        break;
                    case 1:
                        secondMenuChessFragment.getDialog().dismiss();
                        thirdMenuTimeFragment.getDialog().dismiss();
                        break;
                    case 2:
                        secondMenuChessFragment.getDialog().dismiss();
                        thirdMenuTimeFragment.getDialog().dismiss();
                        break;
                    case 3:
                        secondMenuChessFragment.getDialog().dismiss();
                        thirdMenuTimeFragment.getDialog().dismiss();
                        break;
                    case 4:
                        secondMenuChessPicFragment.getDialog().dismiss();
                        thirdMenuTimeFragment.getDialog().dismiss();
                        openChessPic(false);
                        break;
                    case 5:
                        secondMenuChessPicFragment.getDialog().dismiss();
                        thirdMenuTimeFragment.getDialog().dismiss();
                        openChessPicReceive(false);
                        break;
                    default:
                        secondMenuChessFragment.getDialog().dismiss();
                        secondMenuChessPicFragment.getDialog().dismiss();
                        thirdMenuTimeFragment.getDialog().dismiss();
                        break;
                }
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

    public void onBackPressed() {
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
    }

    private void openPvp(boolean isTimed) {
        Intent pvpIntent = new Intent(HomeActivity.this, PvpChessActivity.class);
        pvpIntent.putExtra("TIME", isTimed);
        startActivity(pvpIntent);
        onStop();
        onRestart();
    }

    private void openChessPic(boolean isTimed) {
        Intent chessPicIntent = new Intent(HomeActivity.this, ChessPicActivity.class);
        chessPicIntent.putExtra("TIME", isTimed);
        startActivity(chessPicIntent);
        onStop();
        onRestart();
    }

    private void openChessPicReceive(boolean isTimed) {
        Intent chessPicReceiveIntent = new Intent(HomeActivity.this, ChessPicReceiveActivity.class);
        chessPicReceiveIntent.putExtra("TIME", isTimed);
        startActivity(chessPicReceiveIntent);
        onStop();
        onRestart();
    }
}