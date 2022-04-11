package com.example.pic_chess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    private TextView titleText;
    private ImageButton settingButton, chessPreviewButton, drawPreviewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        titleText = findViewById(R.id.titleText);
        chessPreviewButton = findViewById(R.id.chessPreviewButton);
        drawPreviewButton = findViewById(R.id.drawPreviewButton);
        settingButton = findViewById(R.id.settingButton);

        //Set button listeners
        chessPreviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPvp();
            }
        });
        drawPreviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChessPic();
            }
        });
    }

    private void openPvp() {
        Intent pvpIntent = new Intent(HomeActivity.this, PvpChessActivity.class);
        startActivity(pvpIntent);
    }

    private void openChessPic() {
        Intent chessPicIntent = new Intent(HomeActivity.this, ChessPicActivity.class);
        startActivity(chessPicIntent);
    }
}