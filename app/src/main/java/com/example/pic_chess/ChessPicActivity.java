package com.example.pic_chess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.DragStartHelper;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ChessPicActivity extends AppCompatActivity implements NewCanvasPromptFragment.OnInputSelected, ToolBarFragmentTest.OnClickSelected {
    private ImageButton backButton, newCanvasButton, loadFileButton, saveFileButton, submitFileButton;
    private ToggleButton toolbarButton;
    private AlertDialog.Builder alertDialogue;
    private DrawingView drawingView;

    private ImageView bishopTool, knightTool, pawnTool, rookTool, kingTool, queenTool, currentTool;

    private TextView bishopPieceNum, knightPieceNum, pawnPieceNum, rookPieceNum, kingPieceNum, queenPieceNum;

    private int bishopsLeft, knightsLeft, pawnsLeft, rooksLeft, kingsLeft, queensLeft;

    private float dX, dY;

    private View canvas;
    private Bitmap bitmap;

    private ConstraintLayout constraintLayout;

    private NewCanvasPromptFragment fragmentNCF;
    private ToolBarFragmentTest toolbarFragment;
    private FragmentTransaction transaction;
    private String nameFile;

    //Tags for fragment
    private static final String TAG = "NewCanvasPromptFragment";
    private static final String TAG2 = "ToolbarFragment";

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
        drawingView = findViewById(R.id.drawingViewCP);

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
        fragmentNCF = NewCanvasPromptFragment.newInstance();
        toolbarFragment = ToolBarFragmentTest.newInstance();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.toolbarFragmentContainer, toolbarFragment);
        transaction.addToBackStack(null);
        transaction.commit();
        transaction.hide(toolbarFragment);


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
        toolbarButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton toggleButton, boolean isChecked) {
                transaction = getSupportFragmentManager().beginTransaction();
                if (isChecked) {
                    transaction.show(toolbarFragment);
                } else {
                    transaction.hide(toolbarFragment);
                }
                transaction.commit();
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

    public void sendModeToChessPicActivity(int mode) {
        switch(mode) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                drawingView.setColor(Color.WHITE);
                break;
            case 3:
                drawingView.resetCanvas();
                break;
            default:
                break;
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

    //Open and close toolBar fragment


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

    ///Start Creating Canvas Properties\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    //Making custom drawing view
    public static class DrawingView extends View {
        private static Paint paint = new Paint();
        private static Path path = new Path();
        //private static Canvas canvas = new Canvas();

        public DrawingView(Context context, AttributeSet attributes) {
            super(context, attributes);

            //Set line attributes
            paint.setAntiAlias(true);
            paint.setStrokeWidth(5f);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
        }

        protected void onDraw(Canvas canvas) {
            canvas.drawPath(path, paint);
        }

        public boolean onTouchEvent(MotionEvent event) {
            float touchX = event.getX();
            float touchY = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //Point to the touch coordinate
                    path.moveTo(touchX, touchY);
                    return true;
                case MotionEvent.ACTION_MOVE:
                    //Connect the point every frame
                    path.lineTo(touchX, touchY);
                    break;
                default:
                    return false;
            }

            //Repaint by calling onDraw()
            invalidate();
            return true;
        }

        public void resetCanvas() {
            path.reset();
        }

        public void setColor(int color) {
            paint.setColor(color);
        }

    }
///End Creating Canvas Properties\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
}