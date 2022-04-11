package com.example.pic_chess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PvpChessActivity extends AppCompatActivity {
    private ImageButton backButton, newGameButton, endButton, resignButton;
    private TextView timerText;
    private ConstraintLayout boardLayout, pieceLayout;
    private ArrayList<ImageView> boardImages = new ArrayList<ImageView>();
    private ArrayList<ImageView> pieces = new ArrayList<ImageView>();
    private ArrayList<Move> selectedMoves = new ArrayList<Move>();
    private ArrayList<Boolean> moved = new ArrayList<Boolean>();
    private ArrayList<ImageView> captured = new ArrayList<ImageView>();
    private ImageView selectedView;
    private int whiteMaterial = 0;
    private int blackMaterial = 0;
    private int numMoves = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pvp_chess);


        backButton = findViewById(R.id.backButton);
        newGameButton = findViewById(R.id.newGameButton);
        endButton = findViewById(R.id.endButton);
        resignButton = findViewById(R.id.resignButton);
        timerText = findViewById(R.id.timerText);
        boardLayout = findViewById(R.id.boardLayout);
        pieceLayout = findViewById(R.id.pieceLayout);

        //Add board image views to array list (From boardA1 -> board H8, bottom to top, left to right)
        {
            boardImages.add(findViewById(R.id.boardA1));
            boardImages.add(findViewById(R.id.boardB1));
            boardImages.add(findViewById(R.id.boardC1));
            boardImages.add(findViewById(R.id.boardD1));
            boardImages.add(findViewById(R.id.boardE1));
            boardImages.add(findViewById(R.id.boardF1));
            boardImages.add(findViewById(R.id.boardG1));
            boardImages.add(findViewById(R.id.boardH1));
            boardImages.add(findViewById(R.id.boardA2));
            boardImages.add(findViewById(R.id.boardB2));
            boardImages.add(findViewById(R.id.boardC2));
            boardImages.add(findViewById(R.id.boardD2));
            boardImages.add(findViewById(R.id.boardE2));
            boardImages.add(findViewById(R.id.boardF2));
            boardImages.add(findViewById(R.id.boardG2));
            boardImages.add(findViewById(R.id.boardH2));
            boardImages.add(findViewById(R.id.boardA3));
            boardImages.add(findViewById(R.id.boardB3));
            boardImages.add(findViewById(R.id.boardC3));
            boardImages.add(findViewById(R.id.boardD3));
            boardImages.add(findViewById(R.id.boardE3));
            boardImages.add(findViewById(R.id.boardF3));
            boardImages.add(findViewById(R.id.boardG3));
            boardImages.add(findViewById(R.id.boardH3));
            boardImages.add(findViewById(R.id.boardA4));
            boardImages.add(findViewById(R.id.boardB4));
            boardImages.add(findViewById(R.id.boardC4));
            boardImages.add(findViewById(R.id.boardD4));
            boardImages.add(findViewById(R.id.boardE4));
            boardImages.add(findViewById(R.id.boardF4));
            boardImages.add(findViewById(R.id.boardG4));
            boardImages.add(findViewById(R.id.boardH4));
            boardImages.add(findViewById(R.id.boardA5));
            boardImages.add(findViewById(R.id.boardB5));
            boardImages.add(findViewById(R.id.boardC5));
            boardImages.add(findViewById(R.id.boardD5));
            boardImages.add(findViewById(R.id.boardE5));
            boardImages.add(findViewById(R.id.boardF5));
            boardImages.add(findViewById(R.id.boardG5));
            boardImages.add(findViewById(R.id.boardH5));
            boardImages.add(findViewById(R.id.boardA6));
            boardImages.add(findViewById(R.id.boardB6));
            boardImages.add(findViewById(R.id.boardC6));
            boardImages.add(findViewById(R.id.boardD6));
            boardImages.add(findViewById(R.id.boardE6));
            boardImages.add(findViewById(R.id.boardF6));
            boardImages.add(findViewById(R.id.boardG6));
            boardImages.add(findViewById(R.id.boardH6));
            boardImages.add(findViewById(R.id.boardA7));
            boardImages.add(findViewById(R.id.boardB7));
            boardImages.add(findViewById(R.id.boardC7));
            boardImages.add(findViewById(R.id.boardD7));
            boardImages.add(findViewById(R.id.boardE7));
            boardImages.add(findViewById(R.id.boardF7));
            boardImages.add(findViewById(R.id.boardG7));
            boardImages.add(findViewById(R.id.boardH7));
            boardImages.add(findViewById(R.id.boardA8));
            boardImages.add(findViewById(R.id.boardB8));
            boardImages.add(findViewById(R.id.boardC8));
            boardImages.add(findViewById(R.id.boardD8));
            boardImages.add(findViewById(R.id.boardE8));
            boardImages.add(findViewById(R.id.boardF8));
            boardImages.add(findViewById(R.id.boardG8));
            boardImages.add(findViewById(R.id.boardH8));
        }

        //Set button listeners
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnHome();
            }
        });
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        resignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void returnHome() {
        Intent homeIntent = new Intent(PvpChessActivity.this, HomeActivity.class);
        startActivity(homeIntent);
    }

    private void promptPanel() {

    }
    public int evaluate(){
        int whiteEval = whiteMaterial * 100;
        int blackEval = blackMaterial * 100;
        int evaluation = whiteEval - blackEval;
        int perspective;
        if (getTurn() == "white")
            perspective = 1;
        else
            perspective = -1;
        return evaluation * perspective;
    }
    public int search (int depth, int alpha, int beta){
        if (depth == 0)
            return searchAllCaptures(alpha,beta);
        ArrayList<Move> moves = new ArrayList<Move>();
        for (View v: pieces) {
            if (getPieceColor(v) == getTurn()){
                moves = getLegalMoves(v,false);
            }
            if (moves.size() == 0) {
                if (playerInCheck())
                    return Integer.MIN_VALUE;
                return 0;
            }

            for (Move m: moves){
                makeMove(m,v);
                int eval = -search(depth-1, -beta, -alpha);
                unmakeMove(m,v);
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
        for(View v: pieces) {
            captureMoves = getLegalMoves(v,true);
            orderMoves(captureMoves,v);
            for(Move m: captureMoves){
                makeMove(m,v);
                eval = -searchAllCaptures(-beta,-alpha);
                unmakeMove(m,v);
                if (eval >= beta)
                    return beta;
                alpha = Math.max(alpha,eval);
            }
        }
        return alpha;
    }
    public void orderMoves (ArrayList<Move> moves,View v){
        for (Move m: moves){
            int moveScoreGuess = 0;
            int movePieceType = getMaterialValue(v);
            int capturePieceType = getMaterialValue(v);
            if (capturePieceType != 1) {
                moveScoreGuess += 10 * capturePieceType - movePieceType;
            }
            if (!notAttacked(m.getTargetSquare())){
                moveScoreGuess -= movePieceType;
            }
        }
    }
    public void makeMove(Move move,View v){
        setSquare(v,move.getTargetSquare());
    }
    public void unmakeMove(Move move,View v){
        setSquare(v,move.getCurrentSquare());
    }
    public void pieceOnClick(View v){
        if(getTurn() == getPieceColor(v)) {
            selectedMoves = getLegalMoves(v,false);
            for (Move m: selectedMoves) {
                getSquarebyInt(m.getTargetSquare()).setImageResource(R.drawable.redsquare);
            }
            selectedView = (ImageView) v;
            getSquarebyInt(getSquare(v)).setImageResource(R.drawable.goldsquare);
        } else {
            for (Move m: selectedMoves) {
                if(m.getTargetSquare() == getSquare(v)){
                    if (getTurn() == "white")
                        blackMaterial -= getMaterialValue(v);
                    else
                        whiteMaterial -= getMaterialValue(v);
                    moved.set(getSquare(selectedView)-1,true);
                    setSquare(selectedView,m.getTargetSquare());
                    captured.add((ImageView) v);
                    numMoves++;
                }
            }
        }
    }
    public void squareOnClick(View v){
        if (selectedView != null){
            for (Move m: selectedMoves) {
                if (m.getTargetSquare() == getSquare(v)) {
                    setSquare(selectedView, m.getTargetSquare());
                    if (getSquare(selectedView) == 3 && getSquare(v) == 5 && checkFirstMove(v))
                        setSquare(pieces.get(1), 4);
                    if (getSquare(selectedView) == 7 && getSquare(v) == 5 && checkFirstMove(v))
                        setSquare(pieces.get(8), 6);
                    if (getSquare(selectedView) == 59 && getSquare(v) == 61 && checkFirstMove(v))
                        setSquare(pieces.get(25), 60);
                    if (getSquare(selectedView) == 63 && getSquare(v) == 61 && checkFirstMove(v))
                        setSquare(pieces.get(32), 62);
                    if (getSquare(selectedView) > 56 && getSquare(selectedView) < 65 && getPieceType(selectedView) == "white pawn") {
                        whiteMaterial += 8;
                        ImageView iv = new ImageView(this);
                        iv.setImageResource(R.drawable.whitequeen);
                        pieces.set(pieces.indexOf(selectedView),iv);
                        setSquare(iv, m.getTargetSquare());
                    }
                    if (getSquare(selectedView) > 0 && getSquare(selectedView) < 9 && getPieceType(selectedView) == "black pawn") {
                        blackMaterial += 8;
                        ImageView iv = new ImageView(this);
                        iv.setImageResource(R.drawable.blackqueen);
                        pieces.set(pieces.indexOf(selectedView),iv);
                        setSquare(iv, m.getTargetSquare());
                    }
                    moved.set(getSquare(v) - 1, true);
                    numMoves++;
                }
            }
        }
    }

    private void setSquare (View v, Integer i){
        ImageView view = (ImageView) v;
        ImageView square = getSquarebyInt(i);
        int fromBottom = 15;
        int fromLeft = 0;
        float x = i % 8 * 45 + fromLeft;
        int y1 = i / 8;
        float y2 = y1 * 45 + fromBottom;
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.connect(view.getId(),ConstraintSet.START,square.getId(),ConstraintSet.START);
        constraintSet.connect(view.getId(),ConstraintSet.END,square.getId(),ConstraintSet.END);
        constraintSet.connect(view.getId(),ConstraintSet.TOP,square.getId(),ConstraintSet.TOP);
        constraintSet.connect(view.getId(),ConstraintSet.BOTTOM,square.getId(),ConstraintSet.BOTTOM);
        //constraintSet.applyTo(v);
        v.setTranslationX(x);
        v.setTranslationY(y2);
        v.setTranslationZ(1);
        Log.d("chess1","Moved "+ getPieceColor(v) + " " + getPieceType(v) + " to " + getSquare(v));
    }
    private int getSquare(View v){
        int fromBottom = 15;
        int fromLeft = 0;
        float x = v.getTranslationX();
        float y = v.getTranslationY();
        int i = 0;
        x = (x - fromLeft) / 45 * 8;
        y = (y - fromBottom) / 45 * 8;
        i = (int) (x + 1 + (y*8));
        return i;
    }
    public View getPiecebySquare(int square){
        for(View v: pieces){
            if (getSquare(v) == square) {
                return v;
            }
        }
        return null;
    }
    public ArrayList<Move> getLegalMoves(View v, boolean capturesOnly){
        ArrayList <Move> moves = new ArrayList <Move> ();
        String piece = getPieceType(v);
        boolean placeholder = false;
        int currentSquare = getSquare(v);
        if (piece == "bishop"){
            for (int upRight = currentSquare; upRight < 64; upRight += 9)
                if (!openSquare(upRight) && getPieceColor(getPiecebySquare(upRight)) != getTurn()) {
                    moves.add(new Move(currentSquare,upRight));
                    break;
                } else if (!openSquare(upRight)){
                    break;
                } else {
                    moves.add(new Move(currentSquare, upRight));
                }
            for (int upLeft = currentSquare; upLeft < 58; upLeft += 7)
                if (!openSquare(upLeft) && getPieceColor(getPiecebySquare(upLeft)) != getTurn()) {
                    moves.add(new Move(currentSquare,upLeft));
                    break;
                } else if (!openSquare(upLeft)){
                    break;
                } else {
                    moves.add(new Move(currentSquare, upLeft));
                }
            for (int downRight = currentSquare; downRight > 7; downRight -= 7)
                if (!openSquare(downRight)) {
                    moves.add(new Move(currentSquare,downRight));
                    break;
                } else
                    moves.add(new Move(currentSquare, downRight));
            for (int downLeft = currentSquare; downLeft > 0; downLeft -= 9)
                if (!openSquare(downLeft)) {
                    moves.add(new Move (currentSquare,downLeft));
                    break;
                } else
                    moves.add(new Move (currentSquare,downLeft));
        } else if (piece == "king"){
            if(checkFirstMove(v) && openSquare(2) && openSquare(3) && openSquare(4) && checkFirstMove(pieces.get(1)) && notAttacked(5) && notAttacked(4) && notAttacked(3) && notAttacked(2) && getPieceColor(v) == "white"){
                moves.add(new Move(currentSquare,2));
            } // long castle
            if (checkFirstMove(v) && openSquare(6) && openSquare(7) && checkFirstMove(pieces.get(8)) && notAttacked(6) && notAttacked(7) && notAttacked(5) && getPieceColor(v) == "white"){
                moves.add(new Move(currentSquare,7));
            }// short castle
            if(checkFirstMove(v) && openSquare(58) && openSquare(59) && openSquare(60) && checkFirstMove(pieces.get(25)) && notAttacked(61) && notAttacked(60) && notAttacked(59) &&
                    notAttacked(58) && getPieceColor(v) == "black"){
                moves.add(new Move(currentSquare,59));
            } // long castle
            if (checkFirstMove(v) && openSquare(62) && openSquare(63) && checkFirstMove(pieces.get(32)) && notAttacked(62) && notAttacked(63) && notAttacked(61) && getPieceColor(v) == "black"){
                moves.add(new Move(currentSquare,63));
            }// short castle
            if (notAttacked(currentSquare-1) && currentSquare % 8 != 1){
                moves.add(new Move(currentSquare,currentSquare-1));
            }
            if (notAttacked(currentSquare+1) && currentSquare % 8 != 0){
                moves.add(new Move(currentSquare,currentSquare+1));
            }
            if (notAttacked(currentSquare+8) && currentSquare < 57){
                moves.add(new Move(currentSquare,currentSquare+8));
            }
            if (notAttacked(currentSquare-8) && currentSquare > 8){
                moves.add(new Move(currentSquare,currentSquare-8));
            }
            if (notAttacked(currentSquare-9) && currentSquare > 9 && currentSquare % 8 != 1){
                moves.add(new Move (currentSquare,currentSquare-9));
            }
            if (notAttacked(currentSquare+9) && currentSquare < 56 && currentSquare % 8 != 0){
                moves.add(new Move(currentSquare,currentSquare+9));
            }
            if (notAttacked(currentSquare-7) && currentSquare > 9 && currentSquare % 8 != 0){
                moves.add(new Move(currentSquare,currentSquare-9));
            }
            if (notAttacked(currentSquare+7) && currentSquare < 56 && currentSquare % 8 != 1){
                moves.add(new Move(currentSquare,currentSquare+9));
            }
        } else if (piece == "knight") {
            if (currentSquare % 8 != 0){
                if (currentSquare-15>=1)
                    moves.add(new Move(currentSquare,currentSquare - 15));
                if (currentSquare+17<=64)
                    moves.add(new Move(currentSquare,currentSquare+17));
            } else if (currentSquare % 8 != 1) {
                if (currentSquare-17>=1)
                    moves.add(new Move(currentSquare,currentSquare - 17));
                if (currentSquare+15 <= 64)
                    moves.add(new Move(currentSquare,currentSquare+15));
            } else if (currentSquare % 8 != 7 && currentSquare % 8 != 0) {
                if (currentSquare - 6 >= 1)
                    moves.add(new Move (currentSquare,currentSquare - 6));
                if (currentSquare + 10 <= 64)
                    moves.add(new Move (currentSquare,currentSquare+10));
            } else if (currentSquare % 8 != 1 && currentSquare % 8 != 2) {
                if (currentSquare - 10 >= 1)
                    moves.add(new Move(currentSquare,currentSquare - 10));
                if (currentSquare + 6 <= 64)
                    moves.add(new Move(currentSquare,currentSquare + 6));
            }
        } else if (piece == "white pawn") {
            if (openSquare(currentSquare+8))
                moves.add(new Move(currentSquare,currentSquare + 8));
            if (checkFirstMove(v) && openSquare(currentSquare+16))
                moves.add(new Move(currentSquare,currentSquare + 16));
            if (!openSquare(currentSquare-1) && openSquare(currentSquare+7) && currentSquare < 41 && currentSquare > 33 && placeholder)
                moves.add(new Move(currentSquare,currentSquare+7));
            //en passant left
            if (placeholder)
                moves.add(new Move(currentSquare,currentSquare+9));
            //en passant right
            if (!openSquare(currentSquare+7) && currentSquare % 8 != 1)
                moves.add(new Move (currentSquare,currentSquare+7));
            if (!openSquare(currentSquare+9) && currentSquare % 8 != 0)
                moves.add(new Move(currentSquare,currentSquare+9));
        } else if (piece == "black pawn"){
            if (openSquare(currentSquare-8))
                moves.add(new Move(currentSquare,currentSquare - 8));
            if (checkFirstMove(v) && openSquare(currentSquare-16))
                moves.add(new Move (currentSquare,currentSquare - 16));
            if (placeholder)
                moves.add(new Move(currentSquare,currentSquare-7));
            //en passant right
            if (placeholder)
                moves.add(new Move(currentSquare,currentSquare-9));
            //en passant left
            if (!openSquare(currentSquare-7) && currentSquare % 8 != 0)
                moves.add(new Move (currentSquare,currentSquare-7));
            if (!openSquare(currentSquare-9) && currentSquare % 8 != 1)
                moves.add(new Move (currentSquare,currentSquare-9));
        } else if (piece == "queen"){
            for (int y = currentSquare; y < 64; y += 8)
                if (!openSquare(y)) {
                    moves.add(new Move (currentSquare,y));
                    break;
                } else
                    moves.add(new Move (currentSquare,y));
            for (int y = currentSquare; y > 1; y -= 8)
                if (!openSquare(y)) {
                    moves.add(new Move(currentSquare,y));
                    break;
                } else
                    moves.add(new Move (currentSquare,y));
            for (int x = currentSquare%8; x < 9; x++)
                if (!openSquare(currentSquare+x)) {
                    moves.add(new Move (currentSquare,currentSquare+x));
                    break;
                } else
                    moves.add(new Move (currentSquare,currentSquare+x));
            for (int x = currentSquare%8; x > 0; x--)
                if (!openSquare(currentSquare-x)) {
                    moves.add(new Move (currentSquare,currentSquare-x));
                    break;
                } else
                    moves.add(new Move (currentSquare,currentSquare-x));
            for (int upRight = currentSquare; upRight < 64; upRight += 9)
                if (!openSquare(upRight)) {
                    moves.add(new Move(currentSquare,upRight));
                    break;
                } else
                    moves.add(new Move(currentSquare,upRight));
            for (int upLeft = currentSquare; upLeft < 58; upLeft += 7)
                if (!openSquare(upLeft)) {
                    moves.add(new Move(currentSquare,upLeft));
                    break;
                } else
                    moves.add(new Move(currentSquare,upLeft));
            for (int downRight = currentSquare; downRight > 7; downRight -= 7)
                if (!openSquare(downRight)) {
                    moves.add(new Move(currentSquare,downRight));
                    break;
                } else
                    moves.add(new Move(currentSquare,downRight));
            for (int downLeft = currentSquare; downLeft > 0; downLeft -= 9)
                if (!openSquare(downLeft)) {
                    moves.add(new Move(currentSquare,downLeft));
                    break;
                } else
                    moves.add(new Move(currentSquare,downLeft));
        } else if (piece == "rook"){
            for (int y = currentSquare; y < 64; y += 8)
                if (!openSquare(y)) {
                    moves.add(new Move(currentSquare,y));
                    break;
                } else
                    moves.add(new Move(currentSquare,y));
            for (int y = currentSquare; y > 1; y -= 8)
                if (!openSquare(y)) {
                    moves.add(new Move(currentSquare,y));
                    break;
                } else
                    moves.add(new Move(currentSquare,y));
            for (int x = currentSquare%8; x < 9; x++)
                if (!openSquare(currentSquare+x)) {
                    moves.add(new Move(currentSquare,currentSquare+x));
                    break;
                } else
                    moves.add(new Move(currentSquare,currentSquare+x));
            for (int x = currentSquare%8; x > 0; x--)
                if (!openSquare(currentSquare-x)) {
                    moves.add(new Move(currentSquare,currentSquare-x));
                    break;
                } else
                    moves.add(new Move(currentSquare,currentSquare-x));
        }
        return moves;
    }
    private boolean checkFirstMove(View v){
        int x = 0;
        for (x = 0; x < pieces.size()-1; x++) {
            if (pieces.get(x) == v)
                break;
        }
        return moved.get(x);
    }
    private boolean openSquare(int square){
        for(View v:pieces) {
            if (getSquare(v) == square) {
                return false;
            }
        }
        return true;
    }
    private boolean notAttacked(int square) {
        ArrayList <Move> pieceMoves = new ArrayList <Move>();
        for (View v: pieces){
            if (getPieceColor(v) != getTurn()) {
                pieceMoves = getLegalMoves(v,true);
                for (Move m : pieceMoves) {
                    if (m.getTargetSquare() == square)
                        return false;
                }
            }
        }
        return true;
    }
    private boolean playerInCheck(){
        for (View v: pieces)
            if (getTurn() == "white")
                return !notAttacked(getSquare(pieces.get(4)));
        return !notAttacked(getSquare(pieces.get(28)));
    }
    private boolean pinnedPiece(View v){
        ArrayList <Move> moves = getLegalMoves(v,false);
        boolean result = false;
        for (Move m: moves){
            makeMove(m,v);
            if (!notAttacked(getSquare(pieces.get(4)))) {
                result = true;
            }
            unmakeMove(m,v);
        }
        return result;
    }
    private String getTurn(){
        if(numMoves%2==0)
            return "white";
        else
            return "black";
    }
    private int getMaterialValue(View v){
        String piece = getPieceType(v);
        if (piece == "bishop" || piece == "knight" || piece == "king")
            return 3;
        else if (piece == "rook")
            return 5;
        else if (piece == "queen")
            return 9;
        else
            return 1;
    }
    private String getPieceColor(View v){
        if (v == null) {
            String result = getTurn();
            if (result == "white"){
                return "black";
            }
            return "white";
        }
        if (v == findViewById(R.id.blackBishop))
            return "black";
        else if (v == findViewById(R.id.blackKing))
            return "black";
        else if (v == findViewById(R.id.blackKnight))
            return "black";
        else if (v == findViewById(R.id.blackPawn))
            return "black";
        else if (v == findViewById(R.id.blackQueen))
            return "black";
        else if (v == findViewById(R.id.blackRook))
            return "black";
        else if (v == findViewById(R.id.whiteBishop))
            return "white";
        else if (v == findViewById(R.id.whiteKing))
            return "white";
        else if (v == findViewById(R.id.whiteKnight))
            return "white";
        else if (v == findViewById(R.id.whitePawn))
            return "white";
        else if (v == findViewById(R.id.whiteQueen))
            return "white";
        else
            return "white";
    }
    private String getPieceType(View v){
        if (v == findViewById(R.id.blackBishop) || v == findViewById(R.id.whiteBishop))
            return "bishop";
        else if (v == findViewById(R.id.blackKing) || v == findViewById(R.id.whiteKing))
            return "king";
        else if (v == findViewById(R.id.blackKnight) || v == findViewById(R.id.whiteKnight))
            return "knight";
        else if (v == findViewById(R.id.blackPawn))
            return "black pawn";
        else if (v == findViewById(R.id.blackQueen) || v == findViewById(R.id.whiteQueen))
            return "queen";
        else if (v == findViewById(R.id.whitePawn))
            return "white pawn";
        else
            return "rook";

    }
    private ImageView getSquarebyInt(int i){
        ImageView s = new ImageView(this);
        switch(i) {
            case 1:
                s = findViewById(R.id.boardA1);
            case 2:
                s = findViewById(R.id.boardB1);
            case 3:
                s = findViewById(R.id.boardC1);
            case 4:
                s = findViewById(R.id.boardD1);
            case 5:
                s = findViewById(R.id.boardE1);
            case 6:
                s = findViewById(R.id.boardF1);
            case 7:
                s = findViewById(R.id.boardG1);
            case 8:
                s = findViewById(R.id.boardH1);
            case 9:
                s = findViewById(R.id.boardA2);
            case 10:
                s = findViewById(R.id.boardB2);
            case 11:
                s = findViewById(R.id.boardC2);
            case 12:
                s = findViewById(R.id.boardD2);
            case 13:
                s = findViewById(R.id.boardE2);
            case 14:
                s = findViewById(R.id.boardF2);
            case 15:
                s = findViewById(R.id.boardG2);
            case 16:
                s = findViewById(R.id.boardH2);
            case 17:
                s = findViewById(R.id.boardA3);
            case 18:
                s = findViewById(R.id.boardB3);
            case 19:
                s = findViewById(R.id.boardC3);
            case 20:
                s = findViewById(R.id.boardD3);
            case 21:
                s = findViewById(R.id.boardE3);
            case 22:
                s = findViewById(R.id.boardF3);
            case 23:
                s = findViewById(R.id.boardG3);
            case 24:
                s = findViewById(R.id.boardH3);
            case 25:
                s = findViewById(R.id.boardA4);
            case 26:
                s = findViewById(R.id.boardB4);
            case 27:
                s = findViewById(R.id.boardC4);
            case 28:
                s = findViewById(R.id.boardD4);
            case 29:
                s = findViewById(R.id.boardE4);
            case 30:
                s = findViewById(R.id.boardF4);
            case 31:
                s = findViewById(R.id.boardG4);
            case 32:
                s = findViewById(R.id.boardH4);
            case 33:
                s = findViewById(R.id.boardA5);
            case 34:
                s = findViewById(R.id.boardB5);
            case 35:
                s = findViewById(R.id.boardC5);
            case 36:
                s = findViewById(R.id.boardD5);
            case 37:
                s = findViewById(R.id.boardE5);
            case 38:
                s = findViewById(R.id.boardF5);
            case 39:
                s = findViewById(R.id.boardG5);
            case 40:
                s = findViewById(R.id.boardH5);
            case 41:
                s = findViewById(R.id.boardA6);
            case 42:
                s = findViewById(R.id.boardB6);
            case 43:
                s = findViewById(R.id.boardC6);
            case 44:
                s = findViewById(R.id.boardD6);
            case 45:
                s = findViewById(R.id.boardE6);
            case 46:
                s = findViewById(R.id.boardF6);
            case 47:
                s = findViewById(R.id.boardG6);
            case 48:
                s = findViewById(R.id.boardH6);
            case 49:
                s = findViewById(R.id.boardA7);
            case 50:
                s = findViewById(R.id.boardB7);
            case 51:
                s = findViewById(R.id.boardC7);
            case 52:
                s = findViewById(R.id.boardD7);
            case 53:
                s = findViewById(R.id.boardE7);
            case 54:
                s = findViewById(R.id.boardF7);
            case 55:
                s = findViewById(R.id.boardG7);
            case 56:
                s = findViewById(R.id.boardH7);
            case 57:
                s = findViewById(R.id.boardA8);
            case 58:
                s = findViewById(R.id.boardB8);
            case 59:
                s = findViewById(R.id.boardC8);
            case 60:
                s = findViewById(R.id.boardD8);
            case 61:
                s = findViewById(R.id.boardE8);
            case 62:
                s = findViewById(R.id.boardF8);
            case 63:
                s = findViewById(R.id.boardG8);
            case 64:
                s = findViewById(R.id.boardH8);
        }
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
}