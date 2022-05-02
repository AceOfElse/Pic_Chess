package com.example.pic_chess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class ChessPicActivity extends AppCompatActivity implements NewCanvasPromptFragment.OnInputSelected, ToolBarFragmentTest.OnClickSelected, ColorFragment.OnClickSelected {
    private ImageButton backButton, newCanvasButton, loadFileButton, submitFileButton;
    private static TextView pixelCountText;
    private static ImageButton saveFileButton;
    private ToggleButton toolbarButton;
    private AlertDialog.Builder alertDialogueForBackButton, alertDialogueForNewCanvas;
    private boolean returnState;

    private DrawingView drawingView;
    private static Path path = new Path();
    private static Paint paint = new Paint();

    private CountDownTimer countDownTimer;
    private static long startTime;
    private boolean isTimerRunning;
    private long timeLeftInMilliSecond;

    private ImageView bishopTool, knightTool, pawnTool, rookTool, kingTool, queenTool, currentTool;
    protected static ImageView brushView;
    private ConstraintLayout canvasLayout;
    private TextView bishopPieceNum, knightPieceNum, pawnPieceNum, rookPieceNum, kingPieceNum, queenPieceNum, timerText;
    private int bishopsLeft = 4, knightsLeft = 4, pawnsLeft = 4, rooksLeft = 4, kingsLeft = 4, queensLeft = 4, pieceCode;

    private ToolBarFragmentTest toolbarFragment;
    private ColorFragment colorFragment;
    private FragmentTransaction transaction;
    private String nameFile;

    private Bitmap savingBitmap = Bitmap.createBitmap(300, 200, Bitmap.Config.ARGB_8888);
    private static final int REQUEST_CODE = 100;
    private static boolean isSaved;

    //Tags for fragment
    private static final String TAG2 = "ToolbarFragment";

    //////Start Creation of Activity and Relevant Connections\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess_pic);

        //Receive intent from home activity
        Intent chessPic = getIntent();
        boolean isTimed = chessPic.getBooleanExtra("TIME", false);
        startTime = chessPic.getLongExtra("TIMER", 601000);
        timeLeftInMilliSecond = startTime;

        //Set up alert dialogue
        alertDialogueForBackButton = new AlertDialog.Builder(ChessPicActivity.this);
        alertDialogueForNewCanvas = new AlertDialog.Builder(ChessPicActivity.this);

        // Find views
        backButton = findViewById(R.id.backButtonCP);
        newCanvasButton = findViewById(R.id.newCanvasButtonCP);
        loadFileButton = findViewById(R.id.loadFileButtonCP);
        saveFileButton = findViewById(R.id.saveFileCP);
        submitFileButton = findViewById(R.id.submitFileCP);
        toolbarButton = findViewById(R.id.toolbarButtonCP);
        drawingView = findViewById(R.id.drawingViewCP);
        timerText = findViewById(R.id.timerTextCP);
        brushView = findViewById(R.id.brushView);
        pixelCountText = findViewById(R.id.pixelCountText);
        canvasLayout = findViewById(R.id.canvasLayoutCP);

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

        //Set initial state of save button and brush view
        brushView.setActivated(false);
        isSaved = true;
        saveFileButton.setActivated(false);
        returnState = false;

        //Set fragments
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
                onClickShowAlertReturn();
            }
        });

        newCanvasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseTimer();
                returnState = false;
                onClickShowAlertOnSave();
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
        saveFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnState = false;
                askPermission();
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

    //Return prompt
    private void onClickShowAlertReturn() {
        alertDialogueForBackButton.setMessage(R.string.prompt_back_text);
        alertDialogueForBackButton.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if (isSaved) {
                    drawingView.resetCanvas();
                    resetPieceCount();
                    returnState = true;
                    goBackViaLoadingActivity();
                } else {
                    drawingView.resetCanvas();
                    resetPieceCount();
                    returnState = true;
                    onClickShowAlertOnSave();
                }
            }
        });
        alertDialogueForBackButton.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                startTimer();
            }
        });
        alertDialogueForBackButton.create();
        alertDialogueForBackButton.show();
    }

    //Saving prompt
    private void onClickShowAlertOnSave() {
        alertDialogueForNewCanvas.setMessage(R.string.prompt_saving_text);
        alertDialogueForNewCanvas.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                setState(true);
            }
        });
        alertDialogueForNewCanvas.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                setState(false);
            }
        });
        alertDialogueForNewCanvas.create();
        alertDialogueForNewCanvas.show();

    }

    //Set state after alert
    private void setState(boolean state) {
        if (state) {
            askPermission();
            drawingView.resetCanvas();
            resetPieceCount();
        } else {
            if (returnState) {
                drawingView.resetCanvas();
                resetPieceCount();
                goBackViaLoadingActivity();
            }
        }
    }

    private boolean getReturn() {
        return returnState;
    }



    //Dealing with Android's back button
    public void onBackPressed() {
        alertDialogueForBackButton.setMessage(R.string.prompt_back_text);
        alertDialogueForBackButton.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if (isSaved) {
                    drawingView.resetCanvas();
                    resetPieceCount();
                    returnState = true;
                    goBackViaLoadingActivity();
                } else {
                    drawingView.resetCanvas();
                    resetPieceCount();
                    returnState = true;
                    onClickShowAlertOnSave();
                }
            }
        });
        alertDialogueForBackButton.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                startTimer();
            }
        });
        alertDialogueForBackButton.create();
        alertDialogueForBackButton.show();
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
    //Create shadow while dragging image
    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            //set currentTool to whatever view the user touches
            switch(event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    currentTool = (ImageView) view;
                    // check pieces left
                    if (piecesLeftChecker()) {
                        //drag listener setup
                        ClipData clipData = ClipData.newPlainText("", "");
                        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(currentTool);
                        view.startDrag(clipData, shadowBuilder, currentTool, 0);
                        return true;
                    } else {
                        return false;
                    }
            }
            return false;
        }
    };

    //Drag listener for canvas
    private View.OnDragListener dragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View view, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
                    return true;
                //Only this case is important now, other cases may be implemented if needed.
                case DragEvent.ACTION_DROP:
                    brushView.setVisibility(View.VISIBLE);
                    brushView.setActivated(true);
                    getPieceFromCode(pieceCode);
                    isSaved = false;
                    saveFileButton.setActivated(true);
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    return true;
                case DragEvent.ACTION_DRAG_LOCATION:
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    return true;
                default:
                    return false;
            }
        }
    };

    //Each piece after touch event is assigned with a code after checking availability.
    //Using this method to retrieve image from drawable to match the code, assign appropriated stroke width,
    //reduce the piece after successfully dropped the piece on canvas, and update the text view on piece count.
    private void getPieceFromCode(int code) {
        switch (code) {
            case 0:
                brushView.setImageResource(R.drawable.whitebishop);
                drawingView.setStrokeWidth(10f);
                drawingView.setPixelCount(100);
                bishopsLeft--;
                bishopPieceNum.setText(String.valueOf(bishopsLeft));
                break;
            case 1:
                brushView.setImageResource(R.drawable.whitepawn);
                drawingView.setStrokeWidth(50f);
                drawingView.setPixelCount(50);
                pawnsLeft--;
                pawnPieceNum.setText(String.valueOf(pawnsLeft));
                break;
            case 2:
                brushView.setImageResource(R.drawable.whiterook);
                drawingView.setStrokeWidth(10f);
                drawingView.setPixelCount(100);
                rooksLeft--;
                rookPieceNum.setText(String.valueOf(rooksLeft));
                break;
            case 3:
                brushView.setImageResource(R.drawable.whiteknight);
                drawingView.setStrokeWidth(30f);
                drawingView.setPixelCount(80);
                knightsLeft--;
                knightPieceNum.setText(String.valueOf(knightsLeft));
                break;
            case 4:
                brushView.setImageResource(R.drawable.whiteking);
                drawingView.setStrokeWidth(40f);
                drawingView.setPixelCount(70);
                kingsLeft--;
                kingPieceNum.setText(String.valueOf(kingsLeft));
                break;
            case 5:
                brushView.setImageResource(R.drawable.whitequeen);
                drawingView.setStrokeWidth(5f);
                drawingView.setPixelCount(120);
                queensLeft--;
                queenPieceNum.setText(String.valueOf(queensLeft));
                break;
            default:
                break;
        }
    }


    // user touches desired piece, allows them to click and drag this imageview to canvas
    private void createDraggableImage() {
        for (ImageView imageView : Arrays.asList(bishopTool, knightTool, pawnTool, rookTool, kingTool, queenTool)) {
            imageView.setOnTouchListener(touchListener);
        }
        canvasLayout.setOnDragListener(dragListener);
    }

    //Check the piece if it is still available (not zero). If it is, assigns its piece code.
    private boolean piecesLeftChecker() {
        switch (currentTool.getId()) {
            case R.id.pieceBishopCP:
                if (bishopsLeft > 0) {
                    pieceCode = 0;
                    return true;
                } else {
                    return false;
                }
            case R.id.piecePawnCP:
                if (pawnsLeft > 0) {
                    pieceCode = 1;
                    return true;
                } else {
                    return false;
                }
            case R.id.pieceRookCP:
                if (rooksLeft > 0) {
                    pieceCode = 2;
                    return true;
                } else {
                    return false;
                }
            case R.id.pieceKnightCP:
                if (knightsLeft > 0) {
                    pieceCode = 3;
                    return true;
                } else {
                    return false;
                }
            case R.id.pieceKingCP:
                if (kingsLeft > 0) {
                    pieceCode = 4;
                    return true;
                } else {
                    return false;
                }
            case R.id.pieceQueenCP:
                if (queensLeft > 0) {
                    pieceCode = 5;
                    return true;
                } else {
                    return false;
                }
        }
        return true;
    }

    private void resetPieceCount() {
        bishopsLeft = 4;
        pawnsLeft = 4;
        rooksLeft = 4;
        knightsLeft = 4;
        kingsLeft = 4;
        queensLeft = 4;
        bishopPieceNum.setText(String.valueOf(bishopsLeft));
        pawnPieceNum.setText(String.valueOf(pawnsLeft));
        rookPieceNum.setText(String.valueOf(rooksLeft));
        knightPieceNum.setText(String.valueOf(knightsLeft));
        kingPieceNum.setText(String.valueOf(kingsLeft));
        queenPieceNum.setText(String.valueOf(queensLeft));
    }
//////End Handling Drag Pieces into Canvas\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    //////Start Creating Canvas Properties\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    //Making custom drawing view
    public static class DrawingView extends View {
        private static ArrayList<Path> pathList = new ArrayList<>();
        private static ArrayList<Integer> colorList = new ArrayList<>();
        private static ArrayList<Float> strokeWidthList = new ArrayList<>();
        private ViewGroup.LayoutParams parameters;
        private static int pixelCount = 0, moveCount = 0;
        private static int currentBrush = Color.BLACK;
        private static float currentStrokeWidth = 10f;
        private Bitmap savingBitmap = Bitmap.createBitmap(300, 200, Bitmap.Config.ARGB_8888);

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
                canvas.drawBitmap(savingBitmap, 0, 0, paint);
                canvas.drawPath(pathList.get(i), paint);
                isSaved = false;
                saveFileButton.setActivated(true);
                invalidate();
            }
        }

        public boolean onTouchEvent(MotionEvent event) {
            float touchX = event.getX();
            float touchY = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //Check if the touch is within the bound of brush image view
                    if (touchX > brushView.getX() && touchX < brushView.getX() + 120 && touchY > brushView.getY() && touchY < brushView.getY() + 220) {
                        //Point to the touch coordinate
                        path.moveTo(touchX, touchY);
                        invalidate();
                        return true;
                    } else {
                        return false;
                    }
                case MotionEvent.ACTION_MOVE:
                    if (moveCount < pixelCount) {
                        //Set translation for brush image view
                        brushView.setX(touchX - 50);
                        brushView.setY(touchY - 50);
                        pixelCountText.setX(touchX + 100);
                        pixelCountText.setY(touchY);
                        //Connect the point every frame
                        path.lineTo(touchX, touchY);
                        pathList.add(path);
                        colorList.add(currentBrush);
                        strokeWidthList.add(currentStrokeWidth);
                        moveCount++;
                        pixelCountText.setText(String.valueOf(pixelCount - moveCount));
                        invalidate();
                        return true;
                    } else {
                        return false;
                    }
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
            brushView.setImageDrawable(null);
            pixelCountText.setText(null);
            pixelCount = 0;
            moveCount = 0;
        }

        public void setStrokeWidth(float width) {
            currentColor(currentBrush, width);
        }

        public void setPixelCount(int numPixel) {
            pixelCount = numPixel;
            moveCount = 0;
            pixelCountText.setText(String.valueOf(pixelCount));
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

//////Start Handling Game Logic\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

//////End Handling Game Logic\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

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

    //////Start Handling Saving Image\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    private void askPermission() {
        ActivityCompat.requestPermissions(ChessPicActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveImage();
            } else {
                Toast.makeText(ChessPicActivity.this, "Please provide required permissions", Toast.LENGTH_SHORT).show();
            }
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void saveImage() {
        drawingView.setDrawingCacheEnabled(true);
        Bitmap bitmap = drawingView.getDrawingCache();
        FileOutputStream outStream = null;
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + "/Pictures");
        dir.mkdirs();
        String fileName = "drawin.jpg";
        File outFile = new File(dir, fileName);
        isSaved = true;
        saveFileButton.setActivated(false);
        try {
            outStream = new FileOutputStream(outFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (outStream == null) {
            Toast.makeText(ChessPicActivity.this, "Ourstream is null", Toast.LENGTH_LONG).show();
        } else {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
        }
        try {
            outStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Check if this is save and stay in activity or return back to home
        if (getReturn()) {
            drawingView.resetCanvas();
            resetPieceCount();
            goBackViaLoadingActivity();
        }
    }
//////End Handling Saving Image\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
}