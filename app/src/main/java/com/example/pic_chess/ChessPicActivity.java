package com.example.pic_chess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.DragStartHelper;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ChessPicActivity extends AppCompatActivity implements NewCanvasPromptFragment.OnInputSelected {
    private ImageButton backButton, newCanvasButton, loadFileButton, saveFileButton, submitFileButton;
    private ToggleButton toolbarButton;
    private AlertDialog.Builder alertDialogue;

    private ImageView bishopTool, knightTool, pawnTool, rookTool, kingTool, queenTool, currentTool;

    private TextView bishopPieceNum, knightPieceNum, pawnPieceNum, rookPieceNum, kingPieceNum, queenPieceNum;

    private int bishopsLeft, knightsLeft, pawnsLeft, rooksLeft, kingsLeft, queensLeft;

    private float dX, dY;

    private View canvas;
    private Bitmap bitmap;

    private ConstraintLayout constraintLayout;

    private NewCanvasPromptFragment fragmentNCF;
    private String nameFile;

    //Tags for fragment
    private static final String TAG = "NewCanvasPromptFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess_pic);
        //Set up alert dialogue
        alertDialogue = new AlertDialog.Builder(ChessPicActivity.this);

        // Find views
        backButton = findViewById(R.id.backButtonCP);
        newCanvasButton = findViewById(R.id.newCanvasButtonCP);
        loadFileButton = findViewById(R.id.loadFileButtonCP);
        saveFileButton = findViewById(R.id.saveFileCP);
        submitFileButton = findViewById(R.id.submitFileCP);
        toolbarButton = findViewById(R.id.toolbarButtonCP);

        bishopTool = findViewById(R.id.pieceBishopCP);
        knightTool = findViewById(R.id.pieceKnightCP);
        pawnTool = findViewById(R.id.piecePawnCP);
        rookTool = findViewById(R.id.pieceRookCP);
        kingTool = findViewById(R.id.pieceKingCP);
        queenTool = findViewById(R.id.pieceQueenCP);

        bishopPieceNum = findViewById(R.id.bishopPieceNumber);
        knightPieceNum = findViewById(R.id.knightPieceNumber);
        pawnPieceNum = findViewById(R.id.pawnPieceNumber);
        rookPieceNum = findViewById(R.id.rookPieceNumber);
        kingPieceNum = findViewById(R.id.kingPieceNumber);
        queenPieceNum = findViewById(R.id.queenPieceNumber);

        canvas = findViewById(R.id.canvasLayoutCP);

        //Set fragments
        fragmentNCF = new NewCanvasPromptFragment();

        //Set button listeners
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickShowAlert(view);
            }
        });

        newCanvasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentNCF.show(getSupportFragmentManager(), "Create New File");
            }
        });

        createDraggableImage();

        createCanvas();
    }

    //Implement methods from interface
    public void sendInput(String inputName) {
        nameFile = inputName;
    }

    public void sendNewCanvasState(boolean state) {
        if (state) {
            //Test fragment data passing
            Toast.makeText(this, "Created new file '" + nameFile + "'.", Toast.LENGTH_SHORT).show();
        }
    }

    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    // capture the difference between view's top left corner and touch point
                    dX = v.getX() - event.getRawX();
                    dY = v.getY() - event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    //  a different approach would be to change the view's LayoutParams.
                    v.animate()
                            .x(event.getRawX() + dX)
                            .y(event.getRawY() + dY)
                            .setDuration(0)
                            .start();
                    break;
                case MotionEvent.ACTION_UP:
                    break;
            }
            currentTool = (ImageView) v;
            return true;
        }
    };

    //user touches desired piece, allows them to click and drag this imageview to canvas
    private void createDraggableImage(){
        bishopTool.setOnTouchListener(touchListener);
        knightTool.setOnTouchListener(touchListener);
        pawnTool.setOnTouchListener(touchListener);
        rookTool.setOnTouchListener(touchListener);
        kingTool.setOnTouchListener(touchListener);
        queenTool.setOnTouchListener(touchListener);

        ///TODO: Get currently selected tool's number of pieces left to see if not zero
        ///switch statement

    }

    //creates a canvas for drawing
    private void createCanvas(){
        canvas.draw(new Canvas());
        Paint paint = new Paint();
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
                finish();
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

    //Dealing with Android's back button
    public void onBackPressed() {
        alertDialogue.setMessage(R.string.prompt_back_text);
        alertDialogue.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                finish();
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
}