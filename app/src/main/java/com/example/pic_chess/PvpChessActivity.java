package com.example.pic_chess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PvpChessActivity extends AppCompatActivity {
    private ImageButton backButton, newGameButton, endButton, resignButton;
    private TextView timerText;
    private ConstraintLayout boardLayout, pieceLayout;
    private ArrayList<ImageView> boardImages = new ArrayList<ImageView>();
    private ArrayList<ImageView> pieceImages = new ArrayList<ImageView>();


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

        //Add board image views to array list (From boardA1 -> board H8, bottom to top, left to right)
        {
            boardImages.add(findViewById(R.id.boardA1));
            boardImages.add(findViewById(R.id.boardB1));
            boardImages.add(findViewById(R.id.boardC1));
            boardImages.add(findViewById(R.id.boardD1));
            boardImages.add(findViewById(R.id.boardE1));
            boardImages.add(findViewById(R.id.boardF1));
            boardImages.add(findViewById(R.id.boardG1));
            boardImages.add(findViewById(R.id.boardH1));
            boardImages.add(findViewById(R.id.boardA2));
            boardImages.add(findViewById(R.id.boardB2));
            boardImages.add(findViewById(R.id.boardC2));
            boardImages.add(findViewById(R.id.boardD2));
            boardImages.add(findViewById(R.id.boardE2));
            boardImages.add(findViewById(R.id.boardF2));
            boardImages.add(findViewById(R.id.boardG2));
            boardImages.add(findViewById(R.id.boardH2));
            boardImages.add(findViewById(R.id.boardA3));
            boardImages.add(findViewById(R.id.boardB3));
            boardImages.add(findViewById(R.id.boardC3));
            boardImages.add(findViewById(R.id.boardD3));
            boardImages.add(findViewById(R.id.boardE3));
            boardImages.add(findViewById(R.id.boardF3));
            boardImages.add(findViewById(R.id.boardG3));
            boardImages.add(findViewById(R.id.boardH3));
            boardImages.add(findViewById(R.id.boardA4));
            boardImages.add(findViewById(R.id.boardB4));
            boardImages.add(findViewById(R.id.boardC4));
            boardImages.add(findViewById(R.id.boardD4));
            boardImages.add(findViewById(R.id.boardE4));
            boardImages.add(findViewById(R.id.boardF4));
            boardImages.add(findViewById(R.id.boardG4));
            boardImages.add(findViewById(R.id.boardH4));
            boardImages.add(findViewById(R.id.boardA5));
            boardImages.add(findViewById(R.id.boardB5));
            boardImages.add(findViewById(R.id.boardC5));
            boardImages.add(findViewById(R.id.boardD5));
            boardImages.add(findViewById(R.id.boardE5));
            boardImages.add(findViewById(R.id.boardF5));
            boardImages.add(findViewById(R.id.boardG5));
            boardImages.add(findViewById(R.id.boardH5));
            boardImages.add(findViewById(R.id.boardA6));
            boardImages.add(findViewById(R.id.boardB6));
            boardImages.add(findViewById(R.id.boardC6));
            boardImages.add(findViewById(R.id.boardD6));
            boardImages.add(findViewById(R.id.boardE6));
            boardImages.add(findViewById(R.id.boardF6));
            boardImages.add(findViewById(R.id.boardG6));
            boardImages.add(findViewById(R.id.boardH6));
            boardImages.add(findViewById(R.id.boardA7));
            boardImages.add(findViewById(R.id.boardB7));
            boardImages.add(findViewById(R.id.boardC7));
            boardImages.add(findViewById(R.id.boardD7));
            boardImages.add(findViewById(R.id.boardE7));
            boardImages.add(findViewById(R.id.boardF7));
            boardImages.add(findViewById(R.id.boardG7));
            boardImages.add(findViewById(R.id.boardH7));
            boardImages.add(findViewById(R.id.boardA8));
            boardImages.add(findViewById(R.id.boardB8));
            boardImages.add(findViewById(R.id.boardC8));
            boardImages.add(findViewById(R.id.boardD8));
            boardImages.add(findViewById(R.id.boardE8));
            boardImages.add(findViewById(R.id.boardF8));
            boardImages.add(findViewById(R.id.boardG8));
            boardImages.add(findViewById(R.id.boardH8));
        }

        //Add piece image views to array list (From rookBlack1 -> rookWhite2, bottom to top, left to right)
        {
            pieceImages.add(findViewById(R.id.rookBlack));
            pieceImages.add(findViewById(R.id.knightBlack));
            pieceImages.add(findViewById(R.id.bishopBlack));
            pieceImages.add(findViewById(R.id.queenBlack));
            pieceImages.add(findViewById(R.id.kingBlack));
            pieceImages.add(findViewById(R.id.pawnBlack));
            pieceImages.add(findViewById(R.id.pawnWhite));
            pieceImages.add(findViewById(R.id.rookWhite));
            pieceImages.add(findViewById(R.id.knightWhite));
            pieceImages.add(findViewById(R.id.bishopWhite));
            pieceImages.add(findViewById(R.id.queenWhite));
            pieceImages.add(findViewById(R.id.kingWhite));
        }

        //Set button listeners
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnHome();
            }
        });
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        resignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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