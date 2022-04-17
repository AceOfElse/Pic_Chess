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

public class HomeActivity extends AppCompatActivity implements SecondMenuFragmentOne.OnClickSelection{
    private TextView titleText;
    private ImageButton settingButton, chessPreviewButton, drawPreviewButton;
    private AlertDialog.Builder alertDialogue;
    private SecondMenuFragmentOne secondMenuFragmentOne;
    private FragmentTransaction transaction;

    //Tags for fragment
    private static final String TAG1 = "SecondMenuFragmentOne";
    private static final String TAG2 = "SecondMenuFragmentTwo";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        alertDialogue = new AlertDialog.Builder(HomeActivity.this);

        //Find views
        titleText = findViewById(R.id.titleText);
        chessPreviewButton = findViewById(R.id.chessPreviewButton);
        drawPreviewButton = findViewById(R.id.drawPreviewButton);
        settingButton = findViewById(R.id.settingButton);

        //Set fragments
        secondMenuFragmentOne = SecondMenuFragmentOne.newInstance();

        //Set button listeners
        chessPreviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //transaction = getSupportFragmentManager().beginTransaction();
                //transaction.replace(R.id.secondMenuFragmentContainer, secondMenuFragmentOne);
                //transaction.commit();
                secondMenuFragmentOne.show(getSupportFragmentManager(), "Open second menu one");
            }
        });
        drawPreviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChessPic();
            }
        });
    }

    //Implements method from interface
    public void sendMode(int mode) {
        //executions on each case are just temporarily for testing, might invoke third layer fragment later
        switch(mode) {
            case 0:
                openPvp();
                break;
            case 1:
                openChessPic();
                break;
            case 2:
            case 3:
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

    private void openPvp() {
        Intent pvpIntent = new Intent(HomeActivity.this, PvpChessActivity.class);
        startActivity(pvpIntent);
    }

    private void openChessPic() {
        Intent chessPicIntent = new Intent(HomeActivity.this, ChessPicActivity.class);
        startActivity(chessPicIntent);
        onStop();
        onRestart();
    }
}