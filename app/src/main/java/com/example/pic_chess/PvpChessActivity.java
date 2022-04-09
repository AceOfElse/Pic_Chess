package com.example.pic_chess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;

public class PvpChessActivity extends AppCompatActivity {
    private ImageButton backButton, newGameButton, endButton, resignButton;
    private TextView timerText;
    private ConstraintLayout boardLayout, pieceLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pvp_chess);

        backButton = findViewById(R.id.backButton);
        newGameButton = findViewById(R.id.newGameButton);
        endButton = findViewById(R.id.endButton);
        resignButton = findViewById(R.id.resignButton);
        timerText = findViewById(R.id.timerText);
        boardLayout = findViewById(R.id.boardLayout);
        pieceLayout = findViewById(R.id.pieceLayout);

        //Set listeners
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnHome();
            }
        });
    }

    public void returnHome() {
        Intent homeIntent = new Intent(PvpChessActivity.this, HomeActivity.class);
        startActivity(homeIntent);
    }

    private void promptPanel() {

    }
}