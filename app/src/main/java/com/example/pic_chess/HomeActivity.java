package com.example.pic_chess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class HomeActivity extends AppCompatActivity implements SecondMenuChessFragment.OnClickSelection, SecondMenuChessPicFragment.OnClickSelection, ThirdMenuTimeFragment.OnClickSelection {
    private TextView titleText;
    private ImageButton settingButton, chessPreviewButton, drawPreviewButton;
    private AlertDialog.Builder alertDialogue;
    private SecondMenuChessFragment secondMenuChessFragment;
    private SecondMenuChessPicFragment secondMenuChessPicFragment;
    private ThirdMenuTimeFragment thirdMenuTimeFragment;
    private FragmentTransaction transaction;
    private Bundle bundleForThirdMenu;
    private ByteArrayOutputStream byteStreamFirst, byteStreamSecond;
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
        secondMenuChessPicFragment = SecondMenuChessPicFragment.newInstance();
        secondMenuChessFragment = SecondMenuChessFragment.newInstance();
        thirdMenuTimeFragment = ThirdMenuTimeFragment.newInstance();

        //Set tools to transfer data from activity to fragment
        bundleForThirdMenu = new Bundle();
        byteStreamFirst = new ByteArrayOutputStream();
        byteStreamSecond = new ByteArrayOutputStream();

        //Set button listeners
        chessPreviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.secondMenuFragmentContainer, secondMenuChessFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        drawPreviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.secondMenuFragmentContainer, secondMenuChessPicFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    //Implements method from interfaces
    public void sendModeToThirdFromSecondChess(int mode) {
        switch(mode) {
            case 0:
                firstMode = 0;
                transferDataToThirdFragment(BitmapFactory.decodeResource(getResources(), R.drawable.chess_preview_image),
                        BitmapFactory.decodeResource(getResources(), R.drawable.pvp_preview_image), "CHE", "PVP");
                break;
            case 1:
                firstMode = 1;
                transferDataToThirdFragment(BitmapFactory.decodeResource(getResources(), R.drawable.chess_preview_image),
                        BitmapFactory.decodeResource(getResources(), R.drawable.pvb_preview_image),"CHE", "PVR");
                break;
            case 2:
                firstMode = 2;
                transferDataToThirdFragment(BitmapFactory.decodeResource(getResources(), R.drawable.chess_preview_image),
                        BitmapFactory.decodeResource(getResources(), R.drawable.online_player_image), "CHE", "ONL");
                break;
            case 3:
                firstMode = 3;
                transferDataToThirdFragment(BitmapFactory.decodeResource(getResources(), R.drawable.chess_preview_image),
                        BitmapFactory.decodeResource(getResources(), R.drawable.custom_game_image), "CHE", "CUS");
                break;
            default:
                break;
        }
    }

    public void sendModeToThirdFromSecondChessPic(int mode) {
        switch(mode) {
            case 0:
                firstMode = 4;
                transferDataToThirdFragment(BitmapFactory.decodeResource(getResources(), R.drawable.chesspic_preview_image),
                        BitmapFactory.decodeResource(getResources(), R.drawable.drawing_image), "PIC", "DRW");
                break;
            case 1:
                firstMode = 5;
                transferDataToThirdFragment(BitmapFactory.decodeResource(getResources(), R.drawable.chesspic_preview_image),
                        BitmapFactory.decodeResource(getResources(), R.drawable.guessing_image),"PIC", "GUE");
                break;
            default:
                break;
        }
    }

    public void sendModeToActivityFromThirdTime(int mode) {
        //Take all fragments away from stack
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().popBackStack();

        switch (firstMode) {
            case 0:
                openPvp(mode == 0);
                break;
            case 1:
                break;
            case 2:
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

    //Helper method to establish transaction by encoding images
    private void transferDataToThirdFragment(Bitmap imageBitmapFirst, Bitmap imageBitmapSecond, String tag1, String tag2) {
        imageBitmapFirst.compress(Bitmap.CompressFormat.PNG, 100, byteStreamFirst);
        byte[] byteCodeFirst = byteStreamFirst.toByteArray();
        imageBitmapSecond.compress(Bitmap.CompressFormat.PNG, 100, byteStreamSecond);
        byte[] byteCodeSecond = byteStreamSecond.toByteArray();
        bundleForThirdMenu.putByteArray(tag1, byteCodeFirst);
        bundleForThirdMenu.putByteArray(tag2, byteCodeSecond);
        bundleForThirdMenu.putString("First Image", tag1);
        bundleForThirdMenu.putString("Second Image", tag2);
        thirdMenuTimeFragment.setArguments(bundleForThirdMenu);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.thirdMenuFragmentContainer, thirdMenuTimeFragment);
        transaction.addToBackStack(null);
        transaction.commit();
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