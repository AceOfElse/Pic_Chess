package com.example.pic_chess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    private TextView titleText;
    private ImageView chessPreviewImage, drawPreviewImage;
    //private Button chessPopUpButton, drawPopUpButton;
    private ImageButton settingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        titleText = findViewById(R.id.titleText);
        chessPreviewImage = findViewById(R.id.chessPreviewImage);
        drawPreviewImage = findViewById(R.id.drawPreviewImage);
        //chessPopUpButton = findViewById(R.id.chessPopUpButton);
        //drawPopUpButton = findViewById(R.id.drawPopUpButton);
        settingButton = findViewById(R.id.settingButton);

        chessPreviewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPvp();
            }
        });
    }

    public void openPvp() {
        Intent pvpIntent = new Intent(HomeActivity.this, PvpChessActivity.class);
        startActivity(pvpIntent);
    }
}