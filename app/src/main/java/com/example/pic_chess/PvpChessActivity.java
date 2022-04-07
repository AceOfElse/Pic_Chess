package com.example.pic_chess;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;

public class PvpChessActivity extends AppCompatActivity {
    private ImageButton backButton, newGameButton, endButton, resignButton;
    private TextView timerText;
    private TableLayout boardLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pvp_chess);

        backButton = findViewById(R.id.backButton);
        newGameButton = findViewById(R.id.newGameButton);
        endButton = findViewById(R.id.endButton);
        resignButton = findViewById(R.id.resignButton);
        timerText = findViewById(R.id.timerText);
    }
}