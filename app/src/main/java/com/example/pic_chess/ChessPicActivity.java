package com.example.pic_chess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import java.util.Locale;

public class ChessPicActivity extends AppCompatActivity implements NewCanvasPromptFragment.OnInputSelected, ToolBarFragmentTest.OnClickSelected, ColorFragment.OnClickSelected {
    private ImageButton backButton, newCanvasButton, loadFileButton, saveFileButton, submitFileButton;
    private ToggleButton toolbarButton;
    private AlertDialog.Builder alertDialogue;

    private DrawingView drawingView;
    private static Path path = new Path();
    private static Paint paint = new Paint();

    private CountDownTimer countDownTimer;
    private static long startTime;
    private boolean isTimerRunning;
    private long timeLeftInMilliSecond;

    private ImageView bishopTool, knightTool, pawnTool, rookTool, kingTool, queenTool, currentTool;
    private TextView bishopPieceNum, knightPieceNum, pawnPieceNum, rookPieceNum, kingPieceNum, queenPieceNum, timerText;
    private int bishopsLeft = 4, knightsLeft = 4, pawnsLeft = 4, rooksLeft = 4, kingsLeft = 4, queensLeft = 4;

    private float dX, dY;

    private ViewGroup canvasView, mainLayout;

    private NewCanvasPromptFragment newCanvasPromptFragment;
    private ToolBarFragmentTest toolbarFragment;
    private ColorFragment colorFragment;
    private FragmentTransaction transaction;
    private String nameFile;

    //Tags for fragment
    private static final String TAG = "NewCanvasPromptFragment";
    private static final String TAG2 = "ToolbarFragment";

//////Start Creation of Activity and Relevant Connections\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess_pic);

        //Receive intent from home activity
        Intent chessPic  = getIntent();
        boolean isTimed = chessPic.getBooleanExtra("TIME", false);
        startTime = chessPic.getLongExtra("TIMER", 601000);
        timeLeftInMilliSecond = startTime;

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
        timerText = findViewById(R.id.timerTextCP);

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
        newCanvasPromptFragment = NewCanvasPromptFragment.newInstance();
        toolbarFragment = ToolBarFragmentTest.newInstance();
        colorFragment = ColorFragment.newInstance();

        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.toolbarFragmentContainer, toolbarFragment);
        transaction.addToBackStack(null);
        transaction.commit();
        transaction.hide(toolbarFragment);

        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.colorFragmentContainer, colorFragment);
        transaction.addToBackStack(null);
        transaction.commit();
        transaction.hide(colorFragment);


        //Set button listeners
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseTimer();
                onClickShowAlert(view);
            }
        });

        newCanvasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseTimer();
                newCanvasPromptFragment.show(getSupportFragmentManager(), "Create New File");
            }
        });
        toolbarButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton toggleButton, boolean isChecked) {
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_down_top, R.anim.slide_up_top);
                if (isChecked) {
                    transaction.show(toolbarFragment);
                } else {
                    transaction.hide(toolbarFragment);
                    transaction.hide(colorFragment);
                }
                transaction.commit();
            }
        });

        //Set time textview
        if (isTimed) {
            timerText.setVisibility(View.VISIBLE);
            startTimer();
        } else {
            timerText.setVisibility(View.INVISIBLE);
        }
        updateCountDownText();

        //Others
        createDraggableImage();
        createCanvas();
        setTextviewLeftValues();
    }

    private void setTextviewLeftValues() {
        bishopPieceNum.setText(String.valueOf(bishopsLeft));
        knightPieceNum.setText(String.valueOf(knightsLeft));
        pawnPieceNum.setText(String.valueOf(pawnsLeft));
        rookPieceNum.setText(String.valueOf(rooksLeft));
        kingPieceNum.setText(String.valueOf(kingsLeft));
        queenPieceNum.setText(String.valueOf(queensLeft));
    }

    //Implement methods from interface
    public void sendInput(String inputName) {
        nameFile = inputName;
    }

    public void sendNewCanvasState(boolean state) {
        if (state) {
            //Test fragment data passing
            drawingView.resetCanvas();
            drawingView.setStandardColor();
            toolbarFragment.setImageColor(Color.BLACK);
            drawingView.invalidate();
            resetTimer();
            Toast.makeText(this, "Created new file '" + nameFile + "'.", Toast.LENGTH_SHORT).show();
        } else {
            startTimer();
        }
    }

    public void sendModeOfToolBar(int mode) {
        switch (mode) {
            //Open grab
            case 0:
                break;
            //Close grab
            case 1:
                break;
            //Open color fragment
            case 2:
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_up_bottom, R.anim.slide_down_bottom);
                transaction.show(colorFragment);
                transaction.commit();
                break;
            //Close color fragment
            case 3:
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_up_bottom, R.anim.slide_down_bottom);
                transaction.hide(colorFragment);
                transaction.commit();
                break;
            //Open eraser
            case 4:
                drawingView.setEraser();
                break;
            //Close eraser
            case 5:
                drawingView.setPreviousColor();
                break;
            //Click clear button
            case 6:
                drawingView.resetCanvas();
                drawingView.setStandardColor();
                toolbarFragment.setImageColor(Color.BLACK);
                drawingView.invalidate();
                break;
            default:
                break;
        }
    }

    public void sendColorToActivity(int color, int r, int g, int b) {
        switch (color) {
            case 0:
                drawingView.setColorForLine(Color.BLACK);
                toolbarFragment.setImageColor(Color.BLACK);
                break;
            case 1:
                drawingView.setColorForLine(Color.RED);
                toolbarFragment.setImageColor(Color.RED);
                break;
            case 2:
                drawingView.setColorForLine(Color.BLUE);
                toolbarFragment.setImageColor(Color.BLUE);
                break;
            case 3:
                drawingView.setColorForLine(Color.GREEN);
                toolbarFragment.setImageColor(Color.GREEN);
                break;
            case 4:
                drawingView.setColorForLine(Color.YELLOW);
                toolbarFragment.setImageColor(Color.YELLOW);
                break;
            case 5:
                drawingView.setColorForLine(Color.parseColor("purple"));
                toolbarFragment.setImageColor(Color.parseColor("purple"));
                break;
            case 6:
                drawingView.setColorForLine(Color.rgb(r, g, b));
                toolbarFragment.setImageColor(Color.rgb(r, g, b));
                break;
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

    //Dealing with app's back button
    private void onClickShowAlert(View view) {
        alertDialogue.setMessage(R.string.prompt_back_text);
        alertDialogue.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                goBackViaLoadingActivity();
            }
        });
        alertDialogue.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                startTimer();
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
                goBackViaLoadingActivity();
            }
        });
        alertDialogue.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                startTimer();
            }
        });
        alertDialogue.create();
        alertDialogue.show();
    }

    //Loading animation goes up when returning back to Home Activity.
    private void goBackViaLoadingActivity() {
        Intent loadingIntent = new Intent(ChessPicActivity.this, LoadingActivity.class);
        loadingIntent.putExtra("Class Code", 0);
        startActivity(loadingIntent);
        finish();
    }
//////End Creation of Activity and Relevant Connections\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

//////Start Handling Drag Pieces Into the Canvas\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    //set currentTool to whatever view the user touches
                    currentTool = (ImageView) v;
                    // check pieces left
                    if (piecesLeftChecker()) {
                        //drag listener setup
                        ClipData clipData = ClipData.newPlainText("", "");
                        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                        v.startDrag(clipData, shadowBuilder, v, 0);
                        // capture the difference between view's top left corner and touch point
                        dX = v.getX() - event.getRawX();
                        dY = v.getY() - event.getRawY();
                    }
                    break;
            }
            return true;
        }
    };

    private View.OnDragListener dragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    ConstraintLayout container = (ConstraintLayout) v;
                    container.addView(view);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    dX = v.getX() - event.getX();
                    dY = v.getY() - event.getY();
                    Log.d("drag", String.valueOf(dX));
                    break;
            }
            return false;
        }
    };

    // user touches desired piece, allows them to click and drag this imageview to canvas
    private void createDraggableImage(){
        for (ImageView imageView : Arrays.asList(bishopTool, knightTool, pawnTool, rookTool, kingTool, queenTool)) {
            imageView.setOnTouchListener(touchListener);
        }
        for (ImageView imageView : Arrays.asList(bishopTool, knightTool, pawnTool, rookTool, kingTool, queenTool)) {
            imageView.setOnDragListener(dragListener);
        }
    }

    // Get currently selected tool's number of pieces left to see if not zero
    private boolean piecesLeftChecker() {
        switch (currentTool.getId()) {
            case R.id.pieceBishopCP:
                if (bishopsLeft > 0) {
                    bishopsLeft--;
                    bishopPieceNum.setText(String.valueOf(bishopsLeft));
                    return true;
                }
                else {
                    return false;
                }
            case R.id.piecePawnCP:
                if (pawnsLeft > 0) {
                    pawnsLeft--;
                    pawnPieceNum.setText(String.valueOf(pawnsLeft));
                    return true;
                }
                else {
                    return false;
                }
            case R.id.pieceRookCP:
                if (rooksLeft > 0) {
                    rooksLeft--;
                    rookPieceNum.setText(String.valueOf(rooksLeft));
                    return true;
                }
                else {
                    return false;
                }
            case R.id.pieceKnightCP:
                if (knightsLeft > 0) {
                    knightsLeft--;
                    knightPieceNum.setText(String.valueOf(knightsLeft));
                    return true;
                }
                else {
                    return false;
                }
            case R.id.pieceKingCP:
                if (kingsLeft > 0) {
                    kingsLeft--;
                    kingPieceNum.setText(String.valueOf(kingsLeft));
                    return true;
                }
                else {
                    return false;
                }
            case R.id.pieceQueenCP:
                if (queensLeft > 0) {
                    queensLeft--;
                    queenPieceNum.setText(String.valueOf(queensLeft));
                    return true;
                }
                else {
                    return false;
                }
        }
        return true;
    }

    //creates a canvas for drawing
    private void createCanvas(){
        canvasView.draw(new Canvas());
        Paint paint = new Paint();
    }

//////End Handling Drag Pieces into Canvas\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

//////Start Creating Canvas Properties\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    //Making custom drawing view
    public static class DrawingView extends View {
        private static ArrayList<Path> pathList = new ArrayList<>();
        private static ArrayList<Integer> colorList = new ArrayList<>();
        private static ArrayList<Float> strokeWidthList = new ArrayList<>();
        private ViewGroup.LayoutParams parameters;
        private static int currentBrush = Color.BLACK;
        private static float currentStrokeWidth = 10f;

        public DrawingView(Context context, AttributeSet attributes) {
            super(context, attributes);

            //Set line attributes
            paint.setAntiAlias(true);
            paint.setStrokeWidth(currentStrokeWidth);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);

            parameters = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        protected void onDraw(Canvas canvas) {
            for (int i = 0; i < pathList.size(); i++) {
                paint.setColor(colorList.get(i));
                paint.setStrokeWidth(strokeWidthList.get(i));
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
                    pathList.add(path);
                    colorList.add(currentBrush);
                    strokeWidthList.add(currentStrokeWidth);
                    invalidate();
                    return true;
                default:
                    return false;
            }
        }

        public void resetCanvas() {
            for (int i = 0; i < pathList.size(); i++) {
                pathList.get(i).reset();
            }
            pathList.clear();
            colorList.clear();
            strokeWidthList.clear();
        }

        public void setStandardColor() {
            paint.setColor(Color.BLACK);
            currentColor(paint.getColor(), 10f);
        }

        public void setColorForLine(int color) {
            paint.setColor(color);
            currentColor(paint.getColor(), 10f);
        }

        public void setEraser() {
            paint.setColor(Color.WHITE);
            currentColor(paint.getColor(), 40f);
        }

        public void setPreviousColor() {
            for (int i = colorList.size() - 1; i >= 0; i--) {
                if (!colorList.get(i).equals(Color.WHITE)) {
                    currentBrush = colorList.get(i);
                    break;
                }
            }
            paint.setColor(currentBrush);
            currentColor(paint.getColor(), 10f);
        }

        public void currentColor(int color, float strokeWidth) {
            currentBrush = color;
            currentStrokeWidth = strokeWidth;
            path = new Path();
        }
    }
//////End Creating Canvas Properties\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

//////Start Handling Timer\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMilliSecond, 1000) {
            @Override
            public void onTick(long milliSecondsUntilFinished) {
                timeLeftInMilliSecond = milliSecondsUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                isTimerRunning = false;
            }
        }.start();
        isTimerRunning = true;
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        isTimerRunning = false;
    }

    private void resetTimer() {
        timeLeftInMilliSecond = startTime;
        updateCountDownText();
        isTimerRunning = false;
        startTimer();
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMilliSecond / 1000) / 60;
        int seconds = (int) (timeLeftInMilliSecond / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timerText.setText("TIME\n" + timeLeftFormatted);
    }
//////End Handling Timer\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
}