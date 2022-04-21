package com.example.pic_chess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.constraintlayout.widget.ConstraintSet;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class ChessPicActivity extends AppCompatActivity implements NewCanvasPromptFragment.OnInputSelected, ToolBarFragmentTest.OnClickSelected{
    private ImageButton backButton, newCanvasButton, loadFileButton, saveFileButton, submitFileButton;
    private ToggleButton toolbarButton;
    private AlertDialog.Builder alertDialogue;

    private DrawingView drawingView;
    private static Path path = new Path();
    private static Paint paint = new Paint();

    private ImageView bishopTool, knightTool, pawnTool, rookTool, kingTool, queenTool, currentTool;
    private TextView bishopPieceNum, knightPieceNum, pawnPieceNum, rookPieceNum, kingPieceNum, queenPieceNum;
    private int bishopsLeft, knightsLeft, pawnsLeft, rooksLeft, kingsLeft, queensLeft;
    private float dX, dY;

    private ViewGroup canvasView, mainLayout;
    private Bitmap bitmap;
    private ConstraintLayout constraintLayout;
    private ConstraintSet.Layout layout;

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

        canvasView = findViewById(R.id.canvasLayoutCP);
        mainLayout = findViewById(R.id.chessPicLayout);

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
            drawingView.resetCanvas();
            drawingView.invalidate();
            Toast.makeText(this, "Created new file '" + nameFile + "'.", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendModeOfToolBar(int mode) {
        switch (mode) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                drawingView.setEraser();
                break;
            case 3:
                drawingView.setStandardColor();
                break;
            case 4:
                drawingView.resetCanvas();
                drawingView.invalidate();
                break;
            default:
                break;
        }
    }

    private View.OnDragListener dragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            ClipData clipData = event.getClipData();
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder();
            v.startDrag(clipData, shadowBuilder, event.getLocalState(), 2);
            ViewGroup parent = (ViewGroup) v.getParent();
            parent.removeViewInLayout(v);
            mainLayout.addView(v);

            Log.d("drag", "drag start");

            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    dX = v.getX() - event.getX();
                    dY = v.getY() - event.getY();
                    v.startDrag(clipData, shadowBuilder, event.getLocalState(), 2);
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.animate()
                            .x(event.getX() + dX)
                            .y(event.getY() + dY)
                            .setDuration(0)
                            .start();
                            break;
                case DragEvent.ACTION_DROP:
                    event.getClipData();
                    dX = v.getX() - event.getX();
                    dY = v.getY() - event.getY();
                    v.animate()
                        .x(event.getX() + dX)
                        .y(event.getY() + dY)
                        .setDuration(0)
                        .start();
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    dX = v.getX() - event.getX();
                    dY = v.getY() - event.getY();
                    break;
                default:
                    break;
            }
            return true;
        }
    };

    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    ViewGroup parent = (ViewGroup) v.getParent();
                    parent.removeViewInLayout(v);
                    mainLayout.addView(v);
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
        for (ImageView imageView : Arrays.asList(bishopTool, knightTool, pawnTool, rookTool, kingTool, queenTool)) {
            imageView.setOnTouchListener(touchListener);
        }

        ///TODO: Get currently selected tool's number of pieces left to see if not zero
        ///switch statement

    }

    //creates a canvas for drawing
    private void createCanvas(){
        canvasView.draw(new Canvas());
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
        private static ArrayList<Path> pathList = new ArrayList<>();
        private static ArrayList<Integer> colorList = new ArrayList<>();
        private ViewGroup.LayoutParams parameters;
        private static int currentBrush = Color.BLACK;

        public DrawingView(Context context, AttributeSet attributes) {
            super(context, attributes);

            //Set line attributes
            paint.setAntiAlias(true);
            paint.setStrokeWidth(10f);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            pathList.add(path);
            colorList.add(currentBrush);

            parameters = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        protected void onDraw(Canvas canvas) {
            for (int i = 0; i < pathList.size(); i++) {
                paint.setColor(colorList.get(i));
                canvas.drawPath(pathList.get(i), paint);
                invalidate();
            }
        }

        public boolean onTouchEvent(MotionEvent event) {
            float touchX = event.getX();
            float touchY = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //Point to the touch coordinate
                    path.moveTo(touchX, touchY);
                    invalidate();
                    return true;
                case MotionEvent.ACTION_MOVE:
                    //Connect the point every frame
                    path.lineTo(touchX, touchY);
                    invalidate();
                    return true;
                default:
                    return false;
            }
        }

        public void resetCanvas() {
            path.reset();
        }

        public void setColorForLine(int color) {
            paint.setColor(color);
            currentColor(paint.getColor());
        }

        public void setEraser() {
            paint.setColor(Color.WHITE);
            paint.setStrokeWidth(10f);
            currentColor(paint.getColor());
            pathList.add(path);
            colorList.add(currentBrush);
        }

        public void setStandardColor() {
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(10f);
            currentColor(paint.getColor());
            pathList.add(path);
            colorList.add(currentBrush);
        }

        public void currentColor(int color) {
            currentBrush = color;
            path = new Path();
        }

    }
///End Creating Canvas Properties\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
}