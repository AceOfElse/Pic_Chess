package com.example.pic_chess;

import static com.example.pic_chess.R.drawable;
import static com.example.pic_chess.R.id;
import static com.example.pic_chess.R.layout;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import java.util.ArrayList;
import java.util.Collections;

public class PvpChessActivity extends AppCompatActivity {
    private ImageButton backButton, newGameButton, endButton, resignButton;
    private LinearLayout popupLayout, gameLayout;
    private Button yesButton, noButton, closeButton, min15button;
    private TextView timerText1, timerText2, resignText;
    private ConstraintLayout mainLayout;
    private ArrayList<ConstraintLayout> boardLayout = new ArrayList<ConstraintLayout>();
    private PopupWindow resignMenu, gameMenu;
    private ArrayList<ImageView> boardImages = new ArrayList<ImageView>();
    private ArrayList<Square> boardSquares = new ArrayList<Square>();
    private ArrayList<Piece> pieces = new ArrayList<Piece>();
    private ArrayList<Move> selectedMoves = new ArrayList<Move>();
    private Piece selectedPiece;
    private ArrayList<ImageView> pieceImages = new ArrayList<ImageView>();
    private ArrayList<ImageView> captured = new ArrayList<ImageView>();
    private ArrayList<String> positions = new ArrayList<String>();
    private ImageView selectedView;
    private int whiteMaterial = 0;
    private int blackMaterial = 0;
    private int movesSinceLastPawnMove = 0;
    private int whiteTime = 0;
    private int blackTime = 0;
    private int increment = 0;
    private int numMoves = 0;
    private int time = 900;
    private boolean gameInProgress = false;
    private boolean prompted = false;
    private CountDownTimer whiteTimer, blackTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_pvp_chess);
        mainLayout = findViewById(id.pvpChessLayout);
        gameLayout = new LinearLayout(this);
        popupLayout = new LinearLayout(this);
        resignMenu = new PopupWindow(this);
        gameMenu = new PopupWindow(this);
        resignText = new TextView(this);
        yesButton = new Button(this);
        yesButton.setText("YES");
        noButton = new Button(this);
        noButton.setText("NO");
        min15button = new Button(this);
        min15button.setText("15min");
        closeButton = new Button(this);
        closeButton.setText("CLOSE");
        backButton = findViewById(id.backButton);
        newGameButton = findViewById(id.newGameButton);
        endButton = findViewById(id.endButton);
        resignButton = findViewById(id.resignButton);
        timerText1 = findViewById(id.timerText1);
        timerText2 = findViewById(id.timerText2);
        boardLayout.add(findViewById(id.layoutA1));
        boardLayout.add(findViewById(id.layoutB1));
        boardLayout.add(findViewById(id.layoutC1));
        boardLayout.add(findViewById(id.layoutD1));
        boardLayout.add(findViewById(id.layoutE1));
        boardLayout.add(findViewById(id.layoutF1));
        boardLayout.add(findViewById(id.layoutG1));
        boardLayout.add(findViewById(id.layoutH1));
        boardLayout.add(findViewById(id.layoutA2));
        boardLayout.add(findViewById(id.layoutB2));
        boardLayout.add(findViewById(id.layoutC2));
        boardLayout.add(findViewById(id.layoutD2));
        boardLayout.add(findViewById(id.layoutE2));
        boardLayout.add(findViewById(id.layoutF2));
        boardLayout.add(findViewById(id.layoutG2));
        boardLayout.add(findViewById(id.layoutH2));
        boardLayout.add(findViewById(id.layoutA3));
        boardLayout.add(findViewById(id.layoutB3));
        boardLayout.add(findViewById(id.layoutC3));
        boardLayout.add(findViewById(id.layoutD3));
        boardLayout.add(findViewById(id.layoutE3));
        boardLayout.add(findViewById(id.layoutF3));
        boardLayout.add(findViewById(id.layoutG3));
        boardLayout.add(findViewById(id.layoutH3));
        boardLayout.add(findViewById(id.layoutA4));
        boardLayout.add(findViewById(id.layoutB4));
        boardLayout.add(findViewById(id.layoutC4));
        boardLayout.add(findViewById(id.layoutD4));
        boardLayout.add(findViewById(id.layoutE4));
        boardLayout.add(findViewById(id.layoutF4));
        boardLayout.add(findViewById(id.layoutG4));
        boardLayout.add(findViewById(id.layoutH4));
        boardLayout.add(findViewById(id.layoutA5));
        boardLayout.add(findViewById(id.layoutB5));
        boardLayout.add(findViewById(id.layoutC5));
        boardLayout.add(findViewById(id.layoutD5));
        boardLayout.add(findViewById(id.layoutE5));
        boardLayout.add(findViewById(id.layoutF5));
        boardLayout.add(findViewById(id.layoutG5));
        boardLayout.add(findViewById(id.layoutH5));
        boardLayout.add(findViewById(id.layoutA6));
        boardLayout.add(findViewById(id.layoutB6));
        boardLayout.add(findViewById(id.layoutC6));
        boardLayout.add(findViewById(id.layoutD6));
        boardLayout.add(findViewById(id.layoutE6));
        boardLayout.add(findViewById(id.layoutF6));
        boardLayout.add(findViewById(id.layoutG6));
        boardLayout.add(findViewById(id.layoutH6));
        boardLayout.add(findViewById(id.layoutA7));
        boardLayout.add(findViewById(id.layoutB7));
        boardLayout.add(findViewById(id.layoutC7));
        boardLayout.add(findViewById(id.layoutD7));
        boardLayout.add(findViewById(id.layoutE7));
        boardLayout.add(findViewById(id.layoutF7));
        boardLayout.add(findViewById(id.layoutG7));
        boardLayout.add(findViewById(id.layoutH7));
        boardLayout.add(findViewById(id.layoutA8));
        boardLayout.add(findViewById(id.layoutB8));
        boardLayout.add(findViewById(id.layoutC8));
        boardLayout.add(findViewById(id.layoutD8));
        boardLayout.add(findViewById(id.layoutE8));
        boardLayout.add(findViewById(id.layoutF8));
        boardLayout.add(findViewById(id.layoutG8));
        boardLayout.add(findViewById(id.layoutH8));
        //Add board image views to array list (From boardA1 -> board H8, bottom to top, left to right)
        {
            boardImages.add(findViewById(id.boardA1));
            boardImages.add(findViewById(id.boardB1));
            boardImages.add(findViewById(id.boardC1));
            boardImages.add(findViewById(id.boardD1));
            boardImages.add(findViewById(id.boardE1));
            boardImages.add(findViewById(id.boardF1));
            boardImages.add(findViewById(id.boardG1));
            boardImages.add(findViewById(id.boardH1));
            boardImages.add(findViewById(id.boardA2));
            boardImages.add(findViewById(id.boardB2));
            boardImages.add(findViewById(id.boardC2));
            boardImages.add(findViewById(id.boardD2));
            boardImages.add(findViewById(id.boardE2));
            boardImages.add(findViewById(id.boardF2));
            boardImages.add(findViewById(id.boardG2));
            boardImages.add(findViewById(id.boardH2));
            boardImages.add(findViewById(id.boardA3));
            boardImages.add(findViewById(id.boardB3));
            boardImages.add(findViewById(id.boardC3));
            boardImages.add(findViewById(id.boardD3));
            boardImages.add(findViewById(id.boardE3));
            boardImages.add(findViewById(id.boardF3));
            boardImages.add(findViewById(id.boardG3));
            boardImages.add(findViewById(id.boardH3));
            boardImages.add(findViewById(id.boardA4));
            boardImages.add(findViewById(id.boardB4));
            boardImages.add(findViewById(id.boardC4));
            boardImages.add(findViewById(id.boardD4));
            boardImages.add(findViewById(id.boardE4));
            boardImages.add(findViewById(id.boardF4));
            boardImages.add(findViewById(id.boardG4));
            boardImages.add(findViewById(id.boardH4));
            boardImages.add(findViewById(id.boardA5));
            boardImages.add(findViewById(id.boardB5));
            boardImages.add(findViewById(id.boardC5));
            boardImages.add(findViewById(id.boardD5));
            boardImages.add(findViewById(id.boardE5));
            boardImages.add(findViewById(id.boardF5));
            boardImages.add(findViewById(id.boardG5));
            boardImages.add(findViewById(id.boardH5));
            boardImages.add(findViewById(id.boardA6));
            boardImages.add(findViewById(id.boardB6));
            boardImages.add(findViewById(id.boardC6));
            boardImages.add(findViewById(id.boardD6));
            boardImages.add(findViewById(id.boardE6));
            boardImages.add(findViewById(id.boardF6));
            boardImages.add(findViewById(id.boardG6));
            boardImages.add(findViewById(id.boardH6));
            boardImages.add(findViewById(id.boardA7));
            boardImages.add(findViewById(id.boardB7));
            boardImages.add(findViewById(id.boardC7));
            boardImages.add(findViewById(id.boardD7));
            boardImages.add(findViewById(id.boardE7));
            boardImages.add(findViewById(id.boardF7));
            boardImages.add(findViewById(id.boardG7));
            boardImages.add(findViewById(id.boardH7));
            boardImages.add(findViewById(id.boardA8));
            boardImages.add(findViewById(id.boardB8));
            boardImages.add(findViewById(id.boardC8));
            boardImages.add(findViewById(id.boardD8));
            boardImages.add(findViewById(id.boardE8));
            boardImages.add(findViewById(id.boardF8));
            boardImages.add(findViewById(id.boardG8));
            boardImages.add(findViewById(id.boardH8));
        }
        int f = 0;
        int r = 1;
        int index = 0;
        for (ImageView v: boardImages){
            f++;
            if (f>8){
                f = 1;
                r++;
            }
            boardSquares.add(new Square(f,r, v,boardLayout.get(index)));
            index++;
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        popupLayout.setOrientation(LinearLayout.VERTICAL);
        gameLayout.setOrientation(LinearLayout.VERTICAL);
        resignText.setText("Are you sure you want to resign?");
        popupLayout.addView(resignText, params);
        resignMenu.setContentView(popupLayout);
        gameMenu.setContentView(gameLayout);
        popupLayout.addView(yesButton, params);
        popupLayout.addView(noButton, params);
        gameLayout.addView(closeButton,params);
        gameLayout.addView(min15button,params);
        setContentView(mainLayout);
        //Set button listeners
        yesButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                prompted = false;
                gameInProgress = false;
                resignMenu.dismiss();
            }
        });
        noButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                prompted = false;
                resignMenu.dismiss();
            }
        });
        closeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                prompted = false;
                gameMenu.dismiss();
            }
        });
        min15button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                gameInProgress = true;
                time = 900;
                timerSetup();
                prompted = false;
                gameMenu.dismiss();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!gameInProgress)
                    returnHome();
                else {
                    resignMenu.showAtLocation(gameLayout, Gravity.CENTER,300,80);
                    resignMenu.update(50,50,300,300);
                    prompted = !prompted;
                    //Are you sure
                }
            }
        });
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!gameInProgress){
                    gameMenu.showAtLocation(gameLayout, Gravity.CENTER,300,80);
                    gameMenu.update(50,50,300,300);
                    prompted = !prompted;
                } else {
                    resignMenu.showAtLocation(gameLayout, Gravity.CENTER,300,80);
                    resignMenu.update(50,50,300,300);
                    prompted = !prompted;
                }

            }
        });
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //prompt the other player for a draw
            }
        });
        resignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gameInProgress){
                    resignMenu.showAtLocation(gameLayout, Gravity.CENTER,300,300);
                    resignMenu.update(50,50,300,300);
                    prompted = !prompted;
                }
            }
        });
        generatePositionfromFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
    }
    public void timerSetup(){
        setTimerText(time/60,time%60);
        whiteTime = time;
        blackTime = time;
        blackTimer = new CountDownTimer(time*1000,1000) {
            @Override
            public void onTick(long l) {
                tickDown();
            }

            @Override
            public void onFinish() {
                checkEnd();
            }
        };
        whiteTimer = new CountDownTimer(900000,1000) {
            @Override
            public void onTick(long l) {
                tickDown();
            }

            @Override
            public void onFinish() {
                checkEnd();
            }
        };
    }
    public void returnHome() {
        Intent homeIntent = new Intent(PvpChessActivity.this, HomeActivity.class);
        startActivity(homeIntent);
    }

    private void generatePositionfromFEN(String s) {
        int square = 57;
        int index = 0;
        int blackPawn = 0;
        int whitePawn = 0;
        int blackRook = 0;
        int whiteRook = 0;
        int blackKnight = 0;
        int whiteKnight = 0;
        int blackBishop = 0;
        int whiteBishop = 0;
        ImageView iv;
        for(int x = 0; x < s.length(); x++){
            if(s.charAt(x)=='p') {
                if (blackPawn == 0)
                    iv = findViewById(R.id.blackPawn);
                else if (blackPawn == 1)
                    iv = findViewById(R.id.blackPawn2);
                else if (blackPawn == 2)
                    iv = findViewById(R.id.blackPawn3);
                else if (blackPawn == 3)
                    iv = findViewById(R.id.blackPawn4);
                else if (blackPawn == 4)
                    iv = findViewById(R.id.blackPawn5);
                else if (blackPawn == 5)
                    iv = findViewById(R.id.blackPawn6);
                else if (blackPawn == 6)
                    iv = findViewById(R.id.blackPawn7);
                else
                    iv = findViewById(R.id.blackPawn8);
                pieces.add(new Piece((square - 1) % 8 + 1, (square - 1) / 8 + 1, "black", "black pawn", iv));
                setSquare(pieces.get(index), square);
                Log.d("chess2", pieces.get(index).getPieceType() + " moved to " + square);
                index++;
                square++;
                blackPawn++;
            }else if (s.charAt(x)=='P') {
                if (whitePawn == 0)
                    iv = findViewById(R.id.whitePawn);
                else if (whitePawn == 1)
                    iv = findViewById(R.id.whitePawn2);
                else if (whitePawn == 2)
                    iv = findViewById(R.id.whitePawn3);
                else if (whitePawn == 3)
                    iv = findViewById(R.id.whitePawn4);
                else if (whitePawn == 4)
                    iv = findViewById(R.id.whitePawn5);
                else if (whitePawn == 5)
                    iv = findViewById(R.id.whitePawn6);
                else if (whitePawn == 6)
                    iv = findViewById(R.id.whitePawn7);
                else
                    iv = findViewById(R.id.whitePawn8);
                pieces.add(new Piece((square - 1) % 8 + 1, (square - 1) / 8 + 1, "white", "white pawn", iv));
                setSquare(pieces.get(index), square);
                Log.d("chess2", pieces.get(index).getPieceType() + " moved to " + square);
                index++;
                square++;
                whitePawn++;
            } else if (s.charAt(x) == '8') {
                square += 8;
            } else if (s.charAt(x) == '7') {
                square += 7;
            } else if (s.charAt(x) == '6') {
                square += 6;
            } else if (s.charAt(x) == '5') {
                square += 5;
            } else if (s.charAt(x) == '4') {
                square += 4;
            } else if (s.charAt(x) == '3') {
                square += 3;
            } else if (s.charAt(x) == '2') {
                square += 2;
            } else if (s.charAt(x) == '1') {
                square++;
            } else if (s.charAt(x) == 'r') {
                if (blackRook == 0)
                    iv = findViewById(id.blackRook);
                else
                    iv = findViewById(id.blackRook2);
                pieces.add(new Piece((square - 1) % 8 + 1, (square - 1) / 8 + 1, "black", "rook", iv));
                setSquare(pieces.get(index), square);
                Log.d("chess2", pieces.get(index).getPieceType() + " moved to " + square);
                index++;
                square++;
                blackRook++;
            } else if (s.charAt(x) == 'R') {
                if (whiteRook == 0)
                    iv = findViewById(id.whiteRook);
                else
                    iv = findViewById(id.whiteRook2);
                pieces.add(new Piece((square - 1) % 8 + 1, (square - 1) / 8 + 1, "white", "rook", iv));
                setSquare(pieces.get(index), square);
                Log.d("chess2", pieces.get(index).getPieceType() + " moved to " + square);
                index++;
                square++;
                whiteRook++;
            } else if (s.charAt(x) == 'n') {
                if (blackKnight == 0)
                    iv = findViewById(id.blackKnight);
                else
                    iv = findViewById(id.blackKnight2);
                pieces.add(new Piece((square - 1) % 8 + 1, (square - 1) / 8 + 1, "black", "knight", iv));
                setSquare(pieces.get(index), square);
                Log.d("chess2", pieces.get(index).getPieceType() + " moved to " + square);
                index++;
                square++;
                blackKnight++;
            } else if (s.charAt(x) == 'N') {
                if (whiteKnight == 0)
                    iv = findViewById(id.whiteKnight);
                else
                    iv = findViewById(id.whiteKnight2);
                pieces.add(new Piece((square - 1) % 8 + 1, (square - 1) / 8 + 1, "white", "knight", iv));
                setSquare(pieces.get(index), square);
                Log.d("chess2", pieces.get(index).getPieceType() + " moved to " + square);
                index++;
                square++;
                whiteKnight++;
            } else if (s.charAt(x) == 'b') {
                if (blackBishop == 0)
                    iv = findViewById(id.blackBishop);
                else
                    iv = findViewById(id.blackBishop2);
                pieces.add(new Piece((square - 1) % 8 + 1, (square - 1) / 8 + 1, "black", "bishop", iv));
                setSquare(pieces.get(index), square);
                Log.d("chess2", pieces.get(index).getPieceType() + " moved to " + square);
                index++;
                square++;
                blackBishop++;
            } else if (s.charAt(x) == 'B') {
                if (whiteBishop == 0)
                    iv = findViewById(id.whiteBishop);
                else
                    iv = findViewById(id.whiteBishop2);
                pieces.add(new Piece((square - 1) % 8 + 1, (square - 1) / 8 + 1, "white", "bishop", iv));
                setSquare(pieces.get(index), square);
                Log.d("chess2", pieces.get(index).getPieceType() + " moved to " + square);
                index++;
                square++;
                whiteBishop++;
            } else if (s.charAt(x) == 'k') {
                iv = findViewById(id.blackKing);
                pieces.add(new Piece((square - 1) % 8 + 1, (square - 1) / 8 + 1, "black", "king", iv));
                setSquare(pieces.get(index), square);
                Log.d("chess2", pieces.get(index).getPieceType() + " moved to " + square);
                index++;
                square++;
            } else if (s.charAt(x) == 'K') {
                iv = findViewById(id.whiteKing);
                pieces.add(new Piece((square - 1) % 8 + 1, (square - 1) / 8 + 1, "white", "king", iv));
                setSquare(pieces.get(index), square);
                Log.d("chess2", pieces.get(index).getPieceType() + " moved to " + square);
                index++;
                square++;
            } else if (s.charAt(x) == 'q') {
                iv = findViewById(id.blackQueen);
                pieces.add(new Piece((square - 1) % 8 + 1, (square - 1) / 8 + 1, "black", "queen", iv));
                setSquare(pieces.get(index), square);
                Log.d("chess2", pieces.get(index).getPieceType() + " moved to " + square);
                index++;
                square++;
            } else if (s.charAt(x) == 'Q') {
                iv = findViewById(id.whiteQueen);
                pieces.add(new Piece((square - 1) % 8 + 1, (square - 1) / 8 + 1, "white", "queen", iv));
                setSquare(pieces.get(index), square);
                Log.d("chess2", pieces.get(index).getPieceType() + " moved to " + square);
                index++;
                square++;
            } else if (s.charAt(x) == '/'){
                square -= 16;
            }
        }
        calculateMaterial();
    }
    public String getFENfromPosition(){
        String FEN = "";
        int square = 57;
        int openSpace = 0;
        for(int r = 8; r > 0; r--) {
            for (int f = 0; f < 8; f++) {
                if (getPiecebySquare(square) == null) {
                    openSpace++;
                } else if (getPiecebySquare(square).getPieceType().equals("rook")) {
                    if (getPiecebySquare(square).getPieceColor().equals("white")) {
                        if (openSpace != 0)
                            FEN += openSpace;
                        FEN += "R";
                    } else {
                        if (openSpace != 0)
                            FEN += openSpace;
                        FEN += "r";
                    }
                    openSpace = 0;
                } else if (getPiecebySquare(square).getPieceType().equals("bishop")) {
                    if (getPiecebySquare(square).getPieceColor().equals("white")) {
                        if (openSpace != 0)
                            FEN += openSpace;
                        FEN += "B";
                    } else {
                        if (openSpace != 0)
                            FEN += openSpace;
                        FEN += "b";
                    }
                    openSpace = 0;
                } else if (getPiecebySquare(square).getPieceType().equals("knight")) {
                    if (getPiecebySquare(square).getPieceColor().equals("white")) {
                        if (openSpace != 0)
                            FEN += openSpace;
                        FEN += "N";
                    } else {
                        if (openSpace != 0)
                            FEN += openSpace;
                        FEN += "n";
                    }
                    openSpace = 0;
                } else if (getPiecebySquare(square).getPieceType().equals("king")) {
                    if (getPiecebySquare(square).getPieceColor().equals("white")) {
                        if (openSpace != 0)
                            FEN += openSpace;
                        FEN += "K";
                    } else {
                        if (openSpace != 0)
                            FEN += openSpace;
                        FEN += "k";
                    }
                    openSpace = 0;
                } else if (getPiecebySquare(square).getPieceType().equals("queen")) {
                    if (getPiecebySquare(square).getPieceColor().equals("white")) {
                        if (openSpace != 0)
                            FEN += openSpace;
                        FEN += "Q";
                    } else {
                        if (openSpace != 0)
                            FEN += openSpace;
                        FEN += "q";
                    }
                    openSpace = 0;
                } else if (getPiecebySquare(square).getPieceType().equals("white pawn")) {
                    if (openSpace != 0)
                        FEN += openSpace;
                    FEN += "P";
                    openSpace = 0;
                } else if (getPiecebySquare(square).getPieceType().equals("black pawn")) {
                    if (openSpace != 0)
                        FEN += openSpace;
                    FEN += "p";
                    openSpace = 0;
                }
                square++;
            }
            if (openSpace != 0)
                FEN += openSpace;
            FEN += "/";
            openSpace = 0;
            square -= 16;
        }
        Log.d("FEN",FEN);
        return FEN;
    }
    public void setTimerText(int min, int sec){
        String s = min + ":0" + sec;
        timerText1.setText(s);
        timerText2.setText(s);
    }
    public void tickDown() {
        String s = "";
        String min;
        String sec;
        int minutes;
        int seconds;
        if (getTurn().equals("white")) {
            whiteTime--;
            s = timerText2.getText().toString();
            min = s.substring(0, s.length() - 3);
            sec = s.substring(s.length() - 2);
            seconds = Integer.parseInt(sec);
            minutes = Integer.parseInt(min);
            if (seconds <= 10 && seconds > 0) {
                seconds--;
                s = min + ":0" + seconds;
            } else if (seconds > 10) {
                seconds--;
                s = min + ":" + seconds;
            } else if (seconds == 0) {
                minutes--;
                s = minutes + ":59";
            }
            timerText2.setText(s);
        } else {
            blackTime--;
            s = timerText1.getText().toString();
            min = s.substring(0, s.length() - 3);
            sec = s.substring(s.length() - 2);
            seconds = Integer.parseInt(sec);
            minutes = Integer.parseInt(min);
            if (seconds <= 10 && seconds > 0) {
                seconds--;
                s = min + ":0" + seconds;
            } else if (seconds > 10) {
                seconds--;
                s = min + ":" + seconds;
            } else {
                minutes--;
                s = minutes + ":59";
            }
            timerText1.setText(s);
        }
    }
    //The following evaluate, search, searchAllCaptures, orderMoves, forceKingtoCornerEndgameEval methods
    //These methods were written differently but heavily inspired by Sebastian Lague's code
    //So credit where credit is due
    public int evaluate(){
        int whiteEval = whiteMaterial * 100;
        int blackEval = blackMaterial * 100;
        int evaluation = whiteEval - blackEval;
        int perspective;
        if (getTurn().equals("white"))
            perspective = 1;
        else
            perspective = -1;
        return evaluation * perspective;
    }
    public int search (int depth, int alpha, int beta){
        if (depth == 0)
            return searchAllCaptures(alpha,beta);
        ArrayList<Move> moves = new ArrayList<Move>();
        for (Piece p: pieces) {
            if (p.getPieceColor().equals(getTurn())){
                moves = getLegalMoves(p,false);
            }
            if (moves.size() == 0) {
                if (playerInCheck(getTurn()))
                    return Integer.MIN_VALUE;
                return 0;
            }

            for (Move m: moves){
                makeMove(m,p);
                int eval = -search(depth-1, -beta, -alpha);
                unmakeMove(m,p);
                if (eval >= beta){
                    //Move was too good, opponent will avoid this pos
                    return beta;
                }
                alpha = Math.max(alpha, eval);

            }
        }
        return alpha;
    }
    public int searchAllCaptures(int alpha, int beta){
        int eval = evaluate();
        if (eval >= beta)
            return beta;
        alpha = Math.max(alpha,eval);

        ArrayList<Move> captureMoves = new ArrayList<Move>();
        for(Piece p: pieces) {
            captureMoves = getLegalMoves(p,true);
            orderMoves(captureMoves,p);
            for(Move m: captureMoves){
                makeMove(m,p);
                eval = -searchAllCaptures(-beta,-alpha);
                unmakeMove(m,p);
                if (eval >= beta)
                    return beta;
                alpha = Math.max(alpha,eval);
            }
        }
        return alpha;
    }
    public void orderMoves (ArrayList<Move> moves,Piece p){
        for (Move m: moves){
            int moveScoreGuess = 0;
            int movePieceType = getMaterialValue(p);
            int capturePieceType = getMaterialValue(p);
            if (capturePieceType != 1) {
                moveScoreGuess += 10 * capturePieceType - movePieceType;
            }
            if (!notAttacked(m.getTargetSquare())){
                moveScoreGuess -= movePieceType;
            }
            if (p.getPieceType().equals("white") && p.getRank() == 7){
                moveScoreGuess += 80;
            }
            moveScoreGuess += forceKingtoCornerEndgameEval((float)numMoves/50);
        }
    }
    public int forceKingtoCornerEndgameEval (float endgameWeight){
        int opponentKingRank = pieces.get(4).getRank();
        int opponentKingFile = pieces.get(4).getFile();
        int opponentKingDstToCenterFile = Math.max(3-opponentKingFile,opponentKingFile-4);
        int opponentKingDstToCenterRank = Math.max(3-opponentKingRank,opponentKingRank-4);
        int opponentKingDstFromCenter = opponentKingDstToCenterFile + opponentKingDstToCenterRank;
        int eval = opponentKingDstFromCenter;
        int friendlyKingRank = pieces.get(4).getRank();
        int friendlyKingFile = pieces.get(4).getFile();
        int fileDst = Math.abs(friendlyKingFile-opponentKingFile);
        int rankDst = Math.abs(friendlyKingRank-opponentKingRank);
        int dstBetweenKings = fileDst + rankDst;
        eval += 14-dstBetweenKings;
        return (int)(eval * 10 * endgameWeight);
    }
    public void makeMove(Move move,Piece p){
        int i = move.getTargetSquare();
        p.setRank((i-1)/8+1);
        p.setFile((i-1)%8+1);
    }

    public void unmakeMove(Move move,Piece p){
        int i = move.getCurrentSquare();
        p.setRank((i-1)/8+1);
        p.setFile((i-1)%8+1);
    }
    public void pieceOnClick(View v){
        if (gameInProgress) {
            Piece p = getPiecebyView(v);
            if (getTurn() == p.getPieceColor()) {
                selectedMoves = getLegalMoves(p, false);
                if (selectedPiece != p && selectedPiece != null) {
                    resetBoardSquares();
                }
                for (Move m : selectedMoves) {
                    getSquarebyInt(m.getTargetSquare()).setImageResource(drawable.redsquare);
                }
                selectedView = (ImageView) v;
                selectedPiece = p;
                getSquarebyInt(getSquare(p)).setImageResource(drawable.goldsquare);
            } else if (selectedPiece != null) {
                for (Move m : selectedMoves) {
                    if (m.getTargetSquare() == getSquare(p)) {
                        if (getTurn().equals("white"))
                            blackMaterial -= getMaterialValue(p);
                        else
                            whiteMaterial -= getMaterialValue(p);
                        setSquare(selectedPiece, m.getTargetSquare());
                        p.setMoved(true);
                        getSquarebyView(getSquarebyInt(m.getTargetSquare())).getLayout().removeView(v);
                        captured.add((ImageView) v);
                        selectedPiece = null;
                        selectedView = null;
                        resetBoardSquares();
                        if (numMoves > 1) {
                            if (numMoves % 2 == 0)
                                whiteTimer.cancel();
                            else
                                blackTimer.cancel();
                        }
                        numMoves++;
                        if (numMoves > 1) {
                            if (numMoves % 2 == 0)
                                whiteTimer.start();
                            else
                                blackTimer.start();
                        }
                        positions.add(getFENfromPosition());
                        checkEnd();
                        break;
                    }
                }
            }
        }
    }
    private void checkEnd() {
        ArrayList<ArrayList<Move>> allLegalMoves = new ArrayList<>();
        for (Piece p:pieces){
            if (getTurn().equals(p.getPieceColor()))
                allLegalMoves.add(getLegalMoves(p,false));
        }
        if (getTurn().equals("white")) {
            if (allLegalMoves.get(0) == null && !notAttacked(getSquare(pieces.get(4)))) {
                gameInProgress = false; //Checkmate
            } else if (allLegalMoves.get(0) == null) {
                gameInProgress = false; //Stalemate
            }
        } else {
            if (allLegalMoves.get(0) == null && !notAttacked(getSquare(pieces.get(29)))) {
                gameInProgress = false; //Checkmate
            } else if (allLegalMoves.get(0) == null) {
                gameInProgress = false; //Stalemate
            }
        }
        if (movesSinceLastPawnMove == 100){
            gameInProgress = false; //Draw by 50 Move Rule
        }
        if (positions.size() > 5){
            for (int c1 = 0; c1 < positions.size()-1; c1++) {
                for (int c2 = c1++; c2 < positions.size()-1; c2++) {
                    for (int c3 = c2++; c3 < positions.size()-1; c3++) {
                        if (positions.get(c3).equals(positions.get(c2)) && positions.get(c2).equals(positions.get(c1)) && c2 != c3 && c1 != c2) {
                            gameInProgress = false; //Draw by 3 fold repetition
                            Log.d("end", "Draw by 3 fold repetition");
                        }
                    }
                }
            }
        }
        if (whiteTime == 0 && blackMaterial > 3){
            gameInProgress = false; //Black wins by timeout
            Log.d("end","Black wins by timeout");
        } else if (whiteTime == 0 && blackMaterial == 3)
            gameInProgress = false; //Draw by Timeout vs Insufficient Material
        if (blackTime == 0 && whiteMaterial > 3){
            gameInProgress = false; //White wins by timeout
            Log.d("end","White wins by timeout");
        } else if (blackTime == 0 && whiteMaterial == 3)
            gameInProgress = false; // Draw by Timeout vs Insufficient Material
        if (whiteMaterial <= 3 && blackMaterial <= 3) {
            gameInProgress = false; //Draw by Insufficient Material

        }
        if (!gameInProgress){
            whiteTimer.cancel();
            blackTimer.cancel();
        }
        Log.d("game",""+gameInProgress);
    }

    public void squareOnClick(View v){
        if (selectedView != null && selectedPiece != null && gameInProgress){
            Square s = getSquarebyView(v);
            for (Move m: selectedMoves) {
                if (m.getTargetSquare() == getSquare(s)) {
                    resetBoardSquares();
                    setSquare(selectedPiece, m.getTargetSquare());
                    if (getSquare(selectedPiece) == 3 && getSquare(s) == 5 && selectedPiece.checkFirstMove())
                        setSquare(pieces.get(1), 4);
                    if (getSquare(selectedPiece) == 7 && getSquare(s) == 5 && selectedPiece.checkFirstMove())
                        setSquare(pieces.get(8), 6);
                    if (getSquare(selectedPiece) == 59 && getSquare(s) == 61 && selectedPiece.checkFirstMove())
                        setSquare(pieces.get(25), 60);
                    if (getSquare(selectedPiece) == 63 && getSquare(s) == 61 && selectedPiece.checkFirstMove())
                        setSquare(pieces.get(32), 62);
                    if (getSquare(selectedPiece) > 56 && getSquare(selectedPiece) < 65 && selectedPiece.getPieceType().equals("white pawn")) {
                        whiteMaterial += 8;
                        selectedPiece.promote(true);
                        selectedPiece.setPieceType("queen");
                        pieces.set(pieces.indexOf(selectedPiece), new Piece(selectedPiece.getFile(), selectedPiece.getRank(), selectedPiece.getPieceColor(), "queen"));
                    }
                    if (getSquare(selectedPiece) > 0 && getSquare(selectedPiece) < 9 && selectedPiece.getPieceType().equals("black pawn")) {
                        blackMaterial += 8;
                        selectedPiece.promote(false);
                        selectedPiece.setPieceType("queen");
                        pieces.set(pieces.indexOf(selectedPiece), new Piece(selectedPiece.getFile(), selectedPiece.getRank(), selectedPiece.getPieceColor(), "queen"));
                    }
                    if (selectedPiece.getPieceType().equals("white pawn") || selectedPiece.getPieceType().equals("blackPawn")) {
                        movesSinceLastPawnMove = 0;
                    } else {
                        movesSinceLastPawnMove++;
                    }
                    selectedPiece.setMoved(true);
                    positions.add(getFENfromPosition());
                    if (numMoves > 1){
                        if (numMoves % 2 == 0)
                            whiteTimer.cancel();
                        else
                            blackTimer.cancel();
                    }
                    numMoves++;
                    if (numMoves > 1) {
                        if (numMoves % 2 == 0)
                            whiteTimer.start();
                        else
                            blackTimer.start();
                    }
                    selectedPiece = null;
                    selectedView = null;
                    checkEnd();
                    break;
                }
            }
        }
    }
    public void resetBoardSquares(){
        int x = 0;
        for(ImageView i: boardImages){
            if (x%2 == 0 && getSquarebyView(i).getRank() % 2 == 1)
                i.setImageResource(drawable.darksquare);
            else if (x%2 == 1 && getSquarebyView(i).getRank()%2 == 1)
                i.setImageResource(drawable.lightsquare);
            else if (x%2 == 0 && getSquarebyView(i).getRank()%2 == 0)
                i.setImageResource(drawable.lightsquare);
            else
                i.setImageResource(drawable.darksquare);
            x++;
        }
    }
    private Square getSquarebyView(View v) {
        for (Square square:boardSquares){
            if (square.getView() == v){
                return square;
            }
        }
        return null;
    }

    private void setSquare (Piece p, Integer i){
        ImageView v = p.getPic();
        ImageView square = getSquarebyInt(i);
        Square s = getSquarebyView(square);
        assert s != null;
        if ((ViewGroup)v.getParent() != null)
            ((ViewGroup)v.getParent()).removeView(v);
        s.getLayout().addView(v);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(s.getLayout());
        constraintSet.connect(v.getId(),ConstraintSet.START,square.getId(),ConstraintSet.START);
        constraintSet.connect(v.getId(),ConstraintSet.END,square.getId(),ConstraintSet.END);
        constraintSet.connect(v.getId(),ConstraintSet.TOP,square.getId(),ConstraintSet.TOP);
        constraintSet.connect(v.getId(),ConstraintSet.BOTTOM,square.getId(),ConstraintSet.BOTTOM);
        p.setRank((i-1)/8+1);
        p.setFile((i-1)%8+1);
        Log.d("chess1",s.toString());
        Log.d("chess1","Rank"+p.getRank());
        Log.d("chess1","File"+p.getFile());
        constraintSet.setVisibility(v.getId(),View.VISIBLE);
        constraintSet.setTranslationZ(v.getId(),1);
        constraintSet.applyTo(s.getLayout());
    }
    private int getSquare(Piece p){
        int rank = p.getRank();
        int file = p.getFile();
        return (rank-1)*8+file;
    }
    private int getSquare(Square s){
        int rank = s.getRank();
        int file = s.getFile();
        return (rank-1)*8+file;
    }
    public Piece getPiecebySquare(int square){
        for(Piece p: pieces){
            if (getSquare(p) == square) {
                return p;
            }
        }
        return null;
    }
    public ArrayList<Move> getMoves(Piece p, boolean capturesOnly, String turn) {
        ArrayList<Move> moves = new ArrayList<Move>();
        String piece = p.getPieceType();
        boolean placeholder = false;
        int currentSquare = getSquare(p);
        if (piece.equals("bishop")) {
            for (int upRight = currentSquare+9; upRight < 64 || upRight % 8 == 1; upRight += 9) {
                if (!openSquare(upRight) && !getPiecebySquare(upRight).getPieceColor().equals(turn) && !capturesOnly) {
                    moves.add(new Move(currentSquare, upRight));
                    break;
                } else if (!openSquare(upRight)) {
                    break;
                } else if (!capturesOnly) {
                    moves.add(new Move(currentSquare, upRight));
                }
            }
            for (int upLeft = currentSquare+7; upLeft < 58 || upLeft % 8 == 0; upLeft += 7) {
                if (!openSquare(upLeft) && !getPiecebySquare(upLeft).getPieceColor().equals(turn) && !capturesOnly) {
                    moves.add(new Move(currentSquare, upLeft));
                    break;
                } else if (!openSquare(upLeft)) {
                    break;
                } else if (!capturesOnly){
                    moves.add(new Move(currentSquare, upLeft));
                }
            }
            for (int downRight = currentSquare-7; downRight > 7 || downRight % 8 == 1; downRight -= 7){
                if (!openSquare(downRight) && !getPiecebySquare(downRight).getPieceColor().equals(turn) && !capturesOnly) {
                    moves.add(new Move(currentSquare, downRight));
                    break;
                } else if (!openSquare(downRight)) {
                    break;
                } else if (!capturesOnly)
                    moves.add(new Move(currentSquare, downRight));
            }
            for (int downLeft = currentSquare-9; downLeft > 0 || downLeft % 8 == 0; downLeft -= 9) {
                if (!openSquare(downLeft) && !getPiecebySquare(downLeft).getPieceColor().equals(turn) && !capturesOnly) {
                    moves.add(new Move(currentSquare, downLeft));
                    break;
                } else if (!openSquare(downLeft)) {
                    break;
                } else if (!capturesOnly)
                    moves.add(new Move(currentSquare, downLeft));
            }
        } else if (piece.equals("king")){
            if(p.checkFirstMove() && openSquare(2) && openSquare(3) && openSquare(4) && pieces.get(1).checkFirstMove() && notAttacked(5) && notAttacked(4) && notAttacked(3) && notAttacked(2) && p.getPieceColor().equals("white")){
                moves.add(new Move(currentSquare,2));
            } // long castle
            if (p.checkFirstMove() && openSquare(6) && openSquare(7) && pieces.get(8).checkFirstMove() && notAttacked(6) && notAttacked(7) && notAttacked(5) && p.getPieceColor().equals("white")){
                moves.add(new Move(currentSquare,7));
            }// short castle
            if(p.checkFirstMove() && openSquare(58) && openSquare(59) && openSquare(60) && pieces.get(25).checkFirstMove() && notAttacked(61) && notAttacked(60) && notAttacked(59) &&
                    notAttacked(58) && p.getPieceColor().equals("black")){
                moves.add(new Move(currentSquare,59));
            } // long castle
            if (p.checkFirstMove() && openSquare(62) && openSquare(63) && pieces.get(32).checkFirstMove() && notAttacked(62) && notAttacked(63) && notAttacked(61) && p.getPieceColor().equals("black")){
                moves.add(new Move(currentSquare,63));
            }// short castle
            if (notAttacked(currentSquare-1) && currentSquare % 8 != 1 && getPiecebySquare(currentSquare-1) != null && !getPiecebySquare(currentSquare - 1).getPieceColor().equals(turn)){
                moves.add(new Move(currentSquare,currentSquare-1));
            }
            if (notAttacked(currentSquare+1) && currentSquare % 8 != 0 && getPiecebySquare(currentSquare+1) != null && !getPiecebySquare(currentSquare + 1).getPieceColor().equals(turn)){
                moves.add(new Move(currentSquare,currentSquare+1));
            }
            if (notAttacked(currentSquare+8) && currentSquare < 57 && getPiecebySquare(currentSquare+8) != null && !getPiecebySquare(currentSquare + 8).getPieceColor().equals(turn)){
                moves.add(new Move(currentSquare,currentSquare+8));
            }
            if (notAttacked(currentSquare-8) && currentSquare > 8 && getPiecebySquare(currentSquare-8) != null && !getPiecebySquare(currentSquare - 8).getPieceColor().equals(turn)){
                moves.add(new Move(currentSquare,currentSquare-8));
            }
            if (notAttacked(currentSquare-9) && currentSquare > 9 && currentSquare % 8 != 1 && getPiecebySquare(currentSquare-9) != null && !getPiecebySquare(currentSquare - 9).getPieceColor().equals(turn)){
                moves.add(new Move (currentSquare,currentSquare-9));
            }
            if (notAttacked(currentSquare+9) && currentSquare < 56 && currentSquare % 8 != 0 && getPiecebySquare(currentSquare+9) != null && !getPiecebySquare(currentSquare + 9).getPieceColor().equals(turn)){
                moves.add(new Move(currentSquare,currentSquare+9));
            }
            if (notAttacked(currentSquare-7) && currentSquare > 9 && currentSquare % 8 != 0 && getPiecebySquare(currentSquare-7) != null && !getPiecebySquare(currentSquare - 7).getPieceColor().equals(turn)){
                moves.add(new Move(currentSquare,currentSquare-7));
            }
            if (notAttacked(currentSquare+7) && currentSquare < 56 && currentSquare % 8 != 1 && getPiecebySquare(currentSquare+7) != null && !getPiecebySquare(currentSquare + 7).getPieceColor().equals(turn)){
                moves.add(new Move(currentSquare,currentSquare+7));
            }
        } else if (piece.equals("knight")) {
            if (currentSquare % 8 != 0){
                if (currentSquare-15>=1 && (openSquare(currentSquare-15) || (!openSquare(currentSquare-15) && getPiecebySquare(currentSquare-15).getPieceColor() != turn)))
                    moves.add(new Move(currentSquare,currentSquare - 15));
                if (currentSquare+17<=64 && (openSquare(currentSquare+17) || (!openSquare(currentSquare+17) && getPiecebySquare(currentSquare+17).getPieceColor() != turn)))
                    moves.add(new Move(currentSquare,currentSquare+17));
            }
            if (currentSquare % 8 != 1) {
                if (currentSquare-17>=1 && (openSquare(currentSquare-17) || (!openSquare(currentSquare-17) && getPiecebySquare(currentSquare-17).getPieceColor() != turn)))
                    moves.add(new Move(currentSquare,currentSquare - 17));
                if (currentSquare+15 <= 64 && (openSquare(currentSquare+15) || (!openSquare(currentSquare+15) && getPiecebySquare(currentSquare+15).getPieceColor() != turn)))
                    moves.add(new Move(currentSquare,currentSquare+15));
            }
            if (currentSquare % 8 != 7 && currentSquare % 8 != 0) {
                if (currentSquare - 6 >= 1 && (openSquare(currentSquare-6) || (!openSquare(currentSquare-6) && getPiecebySquare(currentSquare-6).getPieceColor() != turn)))
                    moves.add(new Move (currentSquare,currentSquare - 6));
                if (currentSquare + 10 <= 64 && (openSquare(currentSquare+10) || (!openSquare(currentSquare+10) && getPiecebySquare(currentSquare+10).getPieceColor() != turn)))
                    moves.add(new Move (currentSquare,currentSquare+10));
            }
            if (currentSquare % 8 != 1 && currentSquare % 8 != 2) {
                if (currentSquare - 10 >= 1 && (openSquare(currentSquare-10) || (!openSquare(currentSquare-10) && getPiecebySquare(currentSquare-10).getPieceColor() != turn)))
                    moves.add(new Move(currentSquare,currentSquare - 10));
                if (currentSquare + 6 <= 64 && (openSquare(currentSquare+6) || (!openSquare(currentSquare+6) && getPiecebySquare(currentSquare+6).getPieceColor() != turn)))
                    moves.add(new Move(currentSquare,currentSquare + 6));
            }
        } else if (piece.equals("white pawn")) {
            if (openSquare(currentSquare+8) && !capturesOnly)
                moves.add(new Move(currentSquare,currentSquare + 8));
            if (p.checkFirstMove() && openSquare(currentSquare+16) && !capturesOnly && openSquare(currentSquare+8))
                moves.add(new Move(currentSquare,currentSquare + 16));
            if (!openSquare(currentSquare-1) && openSquare(currentSquare+7) && currentSquare < 41 && currentSquare > 33 && placeholder)
                moves.add(new Move(currentSquare,currentSquare+7));
            //en passant left
            if (placeholder)
                moves.add(new Move(currentSquare,currentSquare+9));
            //en passant right
            if (!openSquare(currentSquare+7) && currentSquare % 8 != 1 && !getPiecebySquare(currentSquare + 7).getPieceColor().equals(turn))
                moves.add(new Move (currentSquare,currentSquare+7));
            if (!openSquare(currentSquare+9) && currentSquare % 8 != 0 && !getPiecebySquare(currentSquare + 9).getPieceColor().equals(turn))
                moves.add(new Move(currentSquare,currentSquare+9));
        } else if (piece.equals("black pawn")){
            if (openSquare(currentSquare-8) && !capturesOnly)
                moves.add(new Move(currentSquare,currentSquare - 8));
            if (p.checkFirstMove() && openSquare(currentSquare-16) && !capturesOnly && openSquare(currentSquare-8))
                moves.add(new Move (currentSquare,currentSquare - 16));
            if (placeholder)
                moves.add(new Move(currentSquare,currentSquare-7));
            //en passant right
            if (placeholder)
                moves.add(new Move(currentSquare,currentSquare-9));
            //en passant left
            if (!openSquare(currentSquare-7) && currentSquare % 8 != 0 && !getPiecebySquare(currentSquare - 7).getPieceColor().equals(turn))
                moves.add(new Move (currentSquare,currentSquare-7));
            if (!openSquare(currentSquare-9) && currentSquare % 8 != 1 && !getPiecebySquare(currentSquare - 9).getPieceColor().equals(turn))
                moves.add(new Move (currentSquare,currentSquare-9));
        } else if (piece.equals("queen")) {
            for (int y = currentSquare+8; y < 64; y += 8)
                if (!openSquare(y) && !turn.equals(getPiecebySquare(y).getPieceColor()) && !capturesOnly) {
                    moves.add(new Move(currentSquare, y));
                    break;
                } else if (!openSquare(y)) {
                    break;
                } else if (!capturesOnly)
                    moves.add(new Move(currentSquare, y));
            for (int y = currentSquare-8; y > 1; y -= 8)
                if (!openSquare(y) && !turn.equals(getPiecebySquare(y).getPieceColor()) && !capturesOnly){
                    moves.add(new Move(currentSquare, y));
                    break;
                } else if (!openSquare(y)) {
                    break;
                } else if (!capturesOnly)
                    moves.add(new Move(currentSquare, y));
            for (int x = currentSquare % 8; x < 9; x++)
                if (!openSquare(currentSquare + x) && !turn.equals(getPiecebySquare(currentSquare + x).getPieceColor()) && !capturesOnly) {
                    moves.add(new Move(currentSquare, currentSquare + x));
                    break;
                } else if (!openSquare(currentSquare)) {
                    break;
                } else if (!capturesOnly)
                    moves.add(new Move(currentSquare, currentSquare + x));
            for (int x = currentSquare % 8; x > 0; x--)
                if (!openSquare(currentSquare - x) && !turn.equals(getPiecebySquare(currentSquare - x).getPieceColor()) && !capturesOnly){
                    moves.add(new Move (currentSquare,currentSquare-x));
                    break;
                }else if (!openSquare(currentSquare-x)) {
                    break;
                } else if (!capturesOnly)
                    moves.add(new Move (currentSquare,currentSquare-x));
            for (int upRight = currentSquare+9; upRight < 64 || upRight % 8 == 1; upRight += 9) {
                if (!openSquare(upRight) && !getPiecebySquare(upRight).getPieceColor().equals(turn) && !capturesOnly) {
                    moves.add(new Move(currentSquare, upRight));
                    break;
                } else if (!openSquare(upRight)) {
                    break;
                } else if (!capturesOnly) {
                    moves.add(new Move(currentSquare, upRight));
                }
            }
            for (int upLeft = currentSquare+7; upLeft < 58 || upLeft % 8 == 0; upLeft += 7) {
                if (!openSquare(upLeft) && !getPiecebySquare(upLeft).getPieceColor().equals(turn) && !capturesOnly) {
                    moves.add(new Move(currentSquare, upLeft));
                    break;
                } else if (!openSquare(upLeft)) {
                    break;
                } else if (!capturesOnly){
                    moves.add(new Move(currentSquare, upLeft));
                }
            }
            for (int downRight = currentSquare-7; downRight > 7 || downRight % 8 == 1; downRight -= 7){
                if (!openSquare(downRight) && !getPiecebySquare(downRight).getPieceColor().equals(turn) && !capturesOnly) {
                    moves.add(new Move(currentSquare, downRight));
                    break;
                } else if (!openSquare(downRight)) {
                    break;
                } else if (!capturesOnly)
                    moves.add(new Move(currentSquare, downRight));
            }
            for (int downLeft = currentSquare-9; downLeft > 0 || downLeft % 8 == 0; downLeft -= 9) {
                if (!openSquare(downLeft) && !getPiecebySquare(downLeft).getPieceColor().equals(turn) && !capturesOnly) {
                    moves.add(new Move(currentSquare, downLeft));
                    break;
                } else if (!openSquare(downLeft)) {
                    break;
                } else if (!capturesOnly)
                    moves.add(new Move(currentSquare, downLeft));
            }
        } else if (piece.equals("rook")){
            for (int y = currentSquare + 8; y < 64; y += 8)
                if (!openSquare(y) && !turn.equals(getPiecebySquare(y).getPieceColor()) && !capturesOnly){
                    moves.add(new Move(currentSquare, y));
                    break;
                } else if (!openSquare(y)) {
                    break;
                } else if (!capturesOnly)
                    moves.add(new Move(currentSquare,y));
            for (int y = currentSquare - 8; y > 1; y -= 8)
                if (!openSquare(y) && !turn.equals(getPiecebySquare(y).getPieceColor()) && !capturesOnly){
                    moves.add(new Move(currentSquare, y));
                    break;
                } else if (!openSquare(y)) {
                    break;
                } else if (!capturesOnly)
                    moves.add(new Move(currentSquare,y));
            for (int x = currentSquare%8; x < 9; x++)
                if (!openSquare(currentSquare+x) && !turn.equals(getPiecebySquare(currentSquare + x).getPieceColor()) && !capturesOnly){
                    moves.add(new Move(currentSquare,currentSquare+x));
                    break;
                } else if (!openSquare(currentSquare+x)) {
                    break;
                } else if (!capturesOnly)
                    moves.add(new Move(currentSquare,currentSquare+x));
            for (int x = currentSquare%8; x > 0; x--)
                if (!openSquare(currentSquare-x) && !turn.equals(getPiecebySquare(currentSquare - x).getPieceColor()) && !capturesOnly){
                    moves.add(new Move(currentSquare,currentSquare-x));
                    break;
                } else if (!openSquare(currentSquare-x)) {
                    break;
                } else if (!capturesOnly)
                    moves.add(new Move(currentSquare,currentSquare-x));
        }
        return moves;
    }
    private ArrayList<Move> getLegalMoves (Piece p, boolean capturesOnly){
        ArrayList<Move> moves = getMoves(p,capturesOnly,getTurn());
        int myKingSquare=0;
        String otherTurn = "white";
        if (getTurn().equals(otherTurn))
            otherTurn = "black";
        for (Piece piece:pieces){
            if (getTurn().equals(piece.getPieceColor()) && piece.getPieceType().equals("king")){
                myKingSquare = getSquare(piece);
            }
        }
        ArrayList <Integer> movesToDelete = new ArrayList<Integer>();
        for (Move moveToVerify:moves){
            makeMove(moveToVerify,p);
            Log.d("chess6","move made " + moveToVerify.getCurrentSquare() + " to " + moveToVerify.getTargetSquare());
            if (playerInCheck(otherTurn)){
                movesToDelete.add(moves.indexOf(moveToVerify));
            }
            unmakeMove(moveToVerify,p);
            Log.d("chess6","move unmade " + moveToVerify.getTargetSquare() + " to " + moveToVerify.getCurrentSquare());
        }
        Collections.reverse(movesToDelete);
        for (Integer i: movesToDelete){
            moves.remove(i);
        }
        return moves;
    }
    private boolean openSquare(int square){
        for(Piece p:pieces) {
            if (getSquare(p) == square) {
                return false;
            }
        }
        return true;
    }
    private boolean notAttacked(int square) {
        ArrayList <Move> pieceMoves = new ArrayList <Move>();
        for (Piece p: pieces){
            if (!p.getPieceColor().equals(getTurn()) && !p.getPieceType().equals("king")) {
                pieceMoves = getMoves(p,true, p.getPieceColor());
                for (Move m : pieceMoves) {
                    if (m.getTargetSquare() == square)
                        return false;
                }
            }
        }
        return true;
    }
    private boolean notAttacked(int square, String otherTurn) {
        ArrayList <Move> pieceMoves = new ArrayList <Move>();
        for (Piece p: pieces){
            if (!p.getPieceColor().equals(otherTurn) && !p.getPieceType().equals("king")) {
                pieceMoves = getMoves(p,true, p.getPieceColor());
                for (Move m : pieceMoves) {
                    if (m.getTargetSquare() == square)
                        return false;
                }
            }
        }
        return true;
    }
    private boolean playerInCheck(String otherTurn){
        if (getTurn().equals("white"))
            return !notAttacked(getSquare(pieces.get(4)),otherTurn);
        return !notAttacked(getSquare(pieces.get(28)),otherTurn);
    }
    private String getTurn(){
        if(numMoves%2==0)
            return "white";
        else
            return "black";
    }
    private int getMaterialValue(Piece p){
        String piece = p.getPieceType();
        if (piece.equals("bishop") || piece.equals("knight") || piece.equals("king"))
            return 3;
        else if (piece.equals("rook"))
            return 5;
        else if (piece.equals("queen"))
            return 9;
        else
            return 1;
    }
    private void calculateMaterial(){
        for(Piece p: pieces){
            if (p.getPieceColor().equals("white")){
                whiteMaterial += getMaterialValue(p);
            } else {
                blackMaterial += getMaterialValue(p);
            }
        }
    }
    public Piece getPiecebyView(View v){
        for (Piece p: pieces){
            if (p.getPic() == v){
                return p;
            }
        }
        return null;
    }
    private ImageView getSquarebyInt(int i){
        ImageView s = new ImageView(this);
        if (i == 1)
            s = findViewById(id.boardA1);
        else if (i == 2)
            s = findViewById(id.boardB1);
        else if (i == 3)
            s = findViewById(id.boardC1);
        else if (i == 4)
            s = findViewById(id.boardD1);
        else if (i == 5)
            s = findViewById(id.boardE1);
        else if (i == 6)
            s = findViewById(id.boardF1);
        else if (i == 7)
            s = findViewById(id.boardG1);
        else if (i == 8)
            s = findViewById(id.boardH1);
        else if (i == 9)
            s = findViewById(id.boardA2);
        else if (i == 10)
            s = findViewById(id.boardB2);
        else if (i == 11)
            s = findViewById(id.boardC2);
        else if (i == 12)
            s = findViewById(id.boardD2);
        else if (i == 13)
            s = findViewById(id.boardE2);
        else if (i == 14)
            s = findViewById(id.boardF2);
        else if (i == 15)
            s = findViewById(id.boardG2);
        else if (i == 16)
            s = findViewById(id.boardH2);
        else if (i == 17)
            s = findViewById(id.boardA3);
        else if (i == 18)
            s = findViewById(id.boardB3);
        else if (i == 19)
            s = findViewById(id.boardC3);
        else if (i == 20)
            s = findViewById(id.boardD3);
        else if (i == 21)
            s = findViewById(id.boardE3);
        else if (i == 22)
            s = findViewById(id.boardF3);
        else if (i == 23)
            s = findViewById(id.boardG3);
        else if (i == 24)
            s = findViewById(id.boardH3);
        else if (i == 25)
            s = findViewById(id.boardA4);
        else if (i == 26)
            s = findViewById(id.boardB4);
        else if (i == 27)
            s = findViewById(id.boardC4);
        else if (i == 28)
            s = findViewById(id.boardD4);
        else if (i == 29)
            s = findViewById(id.boardE4);
        else if (i == 30)
            s = findViewById(id.boardF4);
        else if (i == 31)
            s = findViewById(id.boardG4);
        else if (i == 32)
            s = findViewById(id.boardH4);
        else if (i == 33)
            s = findViewById(id.boardA5);
        else if (i == 34)
            s = findViewById(id.boardB5);
        else if (i == 35)
            s = findViewById(id.boardC5);
        else if (i == 36)
            s = findViewById(id.boardD5);
        else if (i == 37)
            s = findViewById(id.boardE5);
        else if (i == 38)
            s = findViewById(id.boardF5);
        else if (i == 39)
            s = findViewById(id.boardG5);
        else if (i == 40)
            s = findViewById(id.boardH5);
        else if (i == 41)
            s = findViewById(id.boardA6);
        else if (i == 42)
            s = findViewById(id.boardB6);
        else if (i == 43)
            s = findViewById(id.boardC6);
        else if (i == 44)
            s = findViewById(id.boardD6);
        else if (i == 45)
            s = findViewById(id.boardE6);
        else if (i == 46)
            s = findViewById(id.boardF6);
        else if (i == 47)
            s = findViewById(id.boardG6);
        else if (i == 48)
            s = findViewById(id.boardH6);
        else if (i == 49)
            s = findViewById(id.boardA7);
        else if (i == 50)
            s = findViewById(id.boardB7);
        else if (i == 51)
            s = findViewById(id.boardC7);
        else if (i == 52)
            s = findViewById(id.boardD7);
        else if (i == 53)
            s = findViewById(id.boardE7);
        else if (i == 54)
            s = findViewById(id.boardF7);
        else if (i == 55)
            s = findViewById(id.boardG7);
        else if (i == 56)
            s = findViewById(id.boardH7);
        else if (i == 57)
            s = findViewById(id.boardA8);
        else if (i == 58)
            s = findViewById(id.boardB8);
        else if (i == 59)
            s = findViewById(id.boardC8);
        else if (i == 60)
            s = findViewById(id.boardD8);
        else if (i == 61)
            s = findViewById(id.boardE8);
        else if (i == 62)
            s = findViewById(id.boardF8);
        else if (i == 63)
            s = findViewById(id.boardG8);
        else if (i == 64)
            s = findViewById(id.boardH8);
        return s;
    }

    public static class Move {
        private int currentSquare;
        private int targetSquare;
        public Move(int current, int target){
            currentSquare = current;
            targetSquare = target;
        }
        public int getCurrentSquare(){
            return currentSquare;
        }
        public int getTargetSquare(){
            return targetSquare;
        }

    }
    public static class Square {
        private int rank, file;
        private ImageView view;
        private ConstraintLayout layout;
        public Square(int f, int r, ImageView v, ConstraintLayout l){
            rank = r;
            file = f;
            view = v;
            layout = l;
        }
        public int getFile() {
            return file;
        }

        public int getRank() {
            return rank;
        }

        public View getView() {
            return view;
        }
        public ConstraintLayout getLayout(){
            return layout;
        }
    }
    public static class Piece {
        private int rank,file;
        private String pieceType,pieceColor;
        private boolean moved;
        private ImageView pic;
        public Piece(){
            rank = 0;
            file = 0;
            pieceColor = "";
            pieceType = "";
            moved = false;
        }
        public Piece(int f, int r, String c, String t){
            rank = r;
            file = f;
            pieceColor = c;
            pieceType = t;
            moved = true;
        }
        public Piece(int f, int r, String c, String t, ImageView i){
            rank = r;
            file = f;
            pieceColor = c;
            pieceType = t;
            moved = false;
            pic = i;
        }
        public int getRank(){
            return rank;
        }
        public int getFile(){
            return file;
        }
        public String getPieceType(){
            return pieceType;
        }
        public String getPieceColor(){
            return pieceColor;
        }
        public boolean checkFirstMove(){
            return !moved;
        }
        public void setMoved(boolean x){
            moved = x;
        }
        public void setRank(int i) {
            rank = i;
        }
        public void setFile(int i){
            file = i;
        }
        public ImageView getPic() {
            return pic;
        }
        public void setPieceType(String s){
            pieceType = s;
        }
        public void promote(boolean white){
            if (white)
                pic.setImageResource(R.drawable.whitequeen);
            else
                pic.setImageResource(R.drawable.blackqueen);
        }
    }
}