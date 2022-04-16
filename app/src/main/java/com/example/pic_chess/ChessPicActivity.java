package com.example.pic_chess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ToggleButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ChessPicActivity extends AppCompatActivity {
    private ImageButton backButton, newCanvasButton, loadFileButton, saveFileButton, submitFileButton;
    private ToggleButton toolbarButton;
    private AlertDialog.Builder alertDialogue;

    private ImageView bishopTool, knightTool, pawnTool, rookTool, kingTool, queenTool;

    private TextView bishopPieceNum, knightPieceNum, pawnPieceNum, rookPieceNum, kingPieceNum, queenPieceNum;

    private int bishopsLeft, knightsLeft, pawnsLeft, rooksLeft, kingsLeft, queensLeft;

    private View canvas;
    private Bitmap bitmap;

    private ConstraintLayout constraintLayout;

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


        //Set button listeners
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickShowAlert(view);
            }
        });

        bishopTool.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                return true;
            }
        });

//        createDraggableImage();

        createCanvas();
    }



    //user touches desired piece, allows them to click and drag to canvas
//    private void createDraggableImage(){
//        /// set currentSelectedPiece to whatever the user clicks on
//
//        //Get ID of the currently selected chess piece to use for drawing
//        pieceID = currentSelectedPiece.getId();
//
//        ///Find what the piece is based on the ID
//        findViewById(pieceID);
//
//        ///Get text from currently selected piece's number of pieces left to see if not zero
//        ///switch statement
//
//    }

    //creates a canvas for drawing
    private void createCanvas(){
        canvas.draw(new Canvas());
//        Paint paint = new Paint();

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
}