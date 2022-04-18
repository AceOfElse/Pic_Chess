package com.example.pic_chess;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
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

public class PvpChessActivity extends AppCompatActivity {
    private ImageButton backButton, newGameButton, endButton, resignButton;
    private LinearLayout popupLayout, gameLayout;
    private Button yesButton, noButton, closeButton;
    private TextView timerText1, timerText2, resignText;
    private ConstraintLayout boardLayout, pieceLayout, mainLayout;
    private PopupWindow resignMenu, gameMenu;
    private ArrayList<ImageView> boardImages = new ArrayList<ImageView>();
    private ArrayList<Piece> pieces = new ArrayList<Piece>();
    private ArrayList<Move> selectedMoves = new ArrayList<Move>();
    private Piece selectedPiece;
    private ArrayList<ImageView> pieceImages = new ArrayList<ImageView>();
    private ArrayList<ImageView> captured = new ArrayList<ImageView>();
    private ImageView selectedView;
    private int whiteMaterial = 0;
    private int blackMaterial = 0;
    private int numMoves = 0;
    private boolean gameInProgress = false;
    private boolean prompted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pvp_chess);
        mainLayout = (ConstraintLayout) findViewById(R.id.pvpChessLayout);
        pieceLayout = (ConstraintLayout) findViewById(R.id.pieceLayout);
        gameLayout = new LinearLayout(this);
        popupLayout = new LinearLayout(this);
        resignMenu = new PopupWindow(this);
        gameMenu = new PopupWindow(this);
        resignText = new TextView(this);
        yesButton = new Button(this);
        yesButton.setText("YES");
        noButton = new Button(this);
        noButton.setText("NO");
        closeButton = new Button(this);
        closeButton.setText("CLOSE");
        backButton = findViewById(R.id.backButton);
        newGameButton = findViewById(R.id.newGameButton);
        endButton = findViewById(R.id.endButton);
        resignButton = findViewById(R.id.resignButton);
        timerText1 = findViewById(R.id.timerText1);
        timerText2 = findViewById(R.id.timerText2);
        boardLayout = (ConstraintLayout) findViewById(R.id.boardLayout);

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
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        popupLayout.setOrientation(LinearLayout.HORIZONTAL);
        gameLayout.setOrientation(LinearLayout.HORIZONTAL);
        resignText.setText("Are you sure you want to resign?");
        popupLayout.addView(resignText, params);
        resignMenu.setContentView(popupLayout);
        gameMenu.setContentView(gameLayout);
        popupLayout.addView(yesButton, params);
        popupLayout.addView(noButton, params);
        gameLayout.addView(closeButton,params);
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
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!gameInProgress)
                    returnHome();
                else {
                    resignMenu.showAtLocation(boardLayout, Gravity.CENTER,300,80);
                    resignMenu.update(50,50,300,80);
                    prompted = !prompted;
                    //Are you sure
                }
            }
        });
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!gameInProgress){
                    gameMenu.showAtLocation(boardLayout, Gravity.CENTER,300,80);
                    gameMenu.update(50,50,300,80);
                    prompted = !prompted;
                } else {
                    resignMenu.showAtLocation(boardLayout, Gravity.CENTER,300,80);
                    resignMenu.update(50,50,300,80);
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
                    resignMenu.showAtLocation(boardLayout, Gravity.CENTER,300,80);
                    resignMenu.update(50,50,300,80);
                    prompted = !prompted;
                }
            }
        });
        generatePositionfromFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
    }

    public void returnHome() {
        Intent homeIntent = new Intent(PvpChessActivity.this, HomeActivity.class);
        startActivity(homeIntent);
    }

    private void generatePositionfromFEN(String s) {
        int square = 57;
        ImageView iv;
        for(int x = 0; x < s.length(); x++){
            iv = new ImageView(this);
            switch(s.charAt(x)){
                case 'p':
                    iv.setImageResource(R.drawable.blackpawn);
                    pieceImages.add(iv);
                    pieces.add(new Piece(square%8+1,square/8+1,"black","pawn"));
                    setSquare(pieces.get(x),pieceImages.get(x),square);
                    Log.d("chess2",pieces.get(x).getPieceType());
                    square++;
                case 'P':
                    iv.setImageResource(R.drawable.whitepawn);
                    pieceImages.add(iv);
                    pieces.add(new Piece(square%8+1,square/8+1,"white","pawn"));
                    setSquare(pieces.get(x),pieceImages.get(x),square);
                    Log.d("chess2",pieces.get(x).getPieceType());
                    square++;
                case '8':
                    square += 8;
                case '7':
                    square += 7;
                case '6':
                    square += 6;
                case '5':
                    square += 5;
                case '4':
                    square += 4;
                case '3':
                    square += 3;
                case '2':
                    square += 2;
                case '1':
                    square++;
                case 'r':
                    iv.setImageResource(R.drawable.blackrook);
                    pieceImages.add(iv);
                    pieces.add(new Piece(square%8+1,square/8+1,"black","rook"));
                    setSquare(pieces.get(x),pieceImages.get(x),square);
                    Log.d("chess2",pieces.get(x).getPieceType());
                    square++;
                case 'R':
                    iv.setImageResource(R.drawable.whiterook);
                    pieceImages.add(iv);
                    pieces.add(new Piece(square%8+1,square/8+1,"white","rook"));
                    setSquare(pieces.get(x),pieceImages.get(x),square);
                    Log.d("chess2",pieces.get(x).getPieceType());
                    square++;
                case 'n':
                    iv.setImageResource(R.drawable.blackknight);
                    pieceImages.add(iv);
                    pieces.add(new Piece(square%8+1,square/8+1,"black","knight"));
                    setSquare(pieces.get(x),pieceImages.get(x),square);
                    Log.d("chess2",pieces.get(x).getPieceType());
                    square++;
                case 'N':
                    iv.setImageResource(R.drawable.whiteknight);
                    pieceImages.add(iv);
                    pieces.add(new Piece(square%8+1,square/8+1,"white","knight"));
                    setSquare(pieces.get(x),pieceImages.get(x),square);
                    Log.d("chess2",pieces.get(x).getPieceType());
                    square++;
                case 'b':
                    iv.setImageResource(R.drawable.blackbishop);
                    pieceImages.add(iv);
                    pieces.add(new Piece(square%8+1,square/8+1,"black","bishop"));
                    setSquare(pieces.get(x),pieceImages.get(x),square);
                    Log.d("chess2",pieces.get(x).getPieceType());
                    square++;
                case 'B':
                    iv.setImageResource(R.drawable.whitebishop);
                    pieceImages.add(iv);
                    pieces.add(new Piece(square%8+1,square/8+1,"white","bishop"));
                    setSquare(pieces.get(x),pieceImages.get(x),square);
                    Log.d("chess2",pieces.get(x).getPieceType());
                    square++;
                case 'k':
                    iv.setImageResource(R.drawable.blackking);
                    pieceImages.add(iv);
                    pieces.add(new Piece(square%8+1,square/8+1,"black","king"));
                    setSquare(pieces.get(x),pieceImages.get(x),square);
                    Log.d("chess2",pieces.get(x).getPieceType());
                    square++;
                case 'K':
                    iv.setImageResource(R.drawable.whiteking);
                    pieceImages.add(iv);
                    pieces.add(new Piece(square%8+1,square/8+1,"white","king"));
                    setSquare(pieces.get(x),pieceImages.get(x),square);
                    Log.d("chess2",pieces.get(x).getPieceType());
                    square++;
                case 'q':
                    iv.setImageResource(R.drawable.blackqueen);
                    pieceImages.add(iv);
                    pieces.add(new Piece(square%8+1,square/8+1,"black","queen"));
                    setSquare(pieces.get(x),pieceImages.get(x),square);
                    Log.d("chess2",pieces.get(x).getPieceType());
                    square++;
                case 'Q':
                    iv.setImageResource(R.drawable.whitequeen);
                    pieceImages.add(iv);
                    pieces.add(new Piece(square%8+1,square/8+1,"white","queen"));
                    setSquare(pieces.get(x),pieceImages.get(x),square);
                    Log.d("chess2",pieces.get(x).getPieceType());
                    square++;
                case '/':
                    square -= 15;
            }
            Log.d("chess1","Moved "+ pieces.get(x).getPieceColor() + " " + pieces.get(x).getPieceType() + " to " + square);
        }
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
        for (Piece p: pieces) {
            if (p.getPieceColor() == getTurn()){
                moves = getLegalMoves(p,false);
            }
            if (moves.size() == 0) {
                if (playerInCheck())
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
        }
    }
    public void makeMove(Move move,Piece p){
        setSquare(p,getViewbyPiece(p),move.getTargetSquare());
    }

    private View getViewbyPiece(Piece p) {
        return v;
    }

    public void unmakeMove(Move move,Piece p){
        setSquare(p,getViewbyPiece(p),move.getCurrentSquare());
    }
    public void pieceOnClick(View v){
        p = getPiecebyView(v);
        if(getTurn() == p.getPieceColor()) {
            selectedMoves = getLegalMoves(p,false);
            for (Move m: selectedMoves) {
                getSquarebyInt(m.getTargetSquare()).setImageResource(R.drawable.redsquare);
            }
            selectedView = (ImageView) v;
            selectedPiece = p;
            getSquarebyInt(getSquare(p)).setImageResource(R.drawable.goldsquare);
        } else {
            for (Move m: selectedMoves) {
                if(m.getTargetSquare() == getSquare(p)){
                    if (getTurn() == "white")
                        blackMaterial -= getMaterialValue(p);
                    else
                        whiteMaterial -= getMaterialValue(p);
                    p.setMoved(false);
                    setSquare(selectedView,m.getTargetSquare());
                    captured.add((ImageView) v);
                    numMoves++;
                }
            }
        }
    }

    private Piece getPiecebyView(View v) {
        Piece piece = new Piece();
        for(Piece p:pieces){
            if (p == v){
                piece = p;
                break;
            }
        }
        if (piece != null)
            return piece;
        return null;
    }

    public void squareOnClick(View v){
        if (selectedView != null && selectedPiece != null){
            Square s = getSquarebyView(v);
            for (Move m: selectedMoves) {
                if (m.getTargetSquare() == getSquare(s)) {
                    int x = 0;
                    for(ImageView i: boardImages){
                        if (x%2 == 0)
                            i.setImageResource(R.drawable.darksquare);
                        else
                            i.setImageResource(R.drawable.lightsquare);
                        x++;
                    }
                    setSquare(selectedPiece,selectedView, m.getTargetSquare());
                    if (getSquare(selectedPiece) == 3 && getSquare(s) == 5 && selectedPiece.checkFirstMove())
                        setSquare(pieces.get(1), 4);
                    if (getSquare(selectedPiece) == 7 && getSquare(s) == 5 && selectedPiece.checkFirstMove())
                        setSquare(pieces.get(8), 6);
                    if (getSquare(selectedPiece) == 59 && getSquare(s) == 61 && selectedPiece.checkFirstMove())
                        setSquare(pieces.get(25), 60);
                    if (getSquare(selectedPiece) == 63 && getSquare(s) == 61 && selectedPiece.checkFirstMove())
                        setSquare(pieces.get(32), 62);
                    if (getSquare(selectedPiece) > 56 && getSquare(selectedPiece) < 65 && selectedPiece.getPieceType() == "white pawn") {
                        whiteMaterial += 8;
                        ImageView iv = new ImageView(this);
                        iv.setImageResource(R.drawable.whitequeen);
                        pieces.set(pieces.indexOf(selectedPiece),new Piece(selectedPiece.getFile(),selectedPiece.getRank(),selectedPiece.getPieceColor(),"queen"));
                        setSquare(iv, m.getTargetSquare());
                    }
                    if (getSquare(selectedPiece) > 0 && getSquare(selectedPiece) < 9 && selectedPiece.getPieceType() == "black pawn") {
                        blackMaterial += 8;
                        ImageView iv = new ImageView(this);
                        iv.setImageResource(R.drawable.blackqueen);
                        pieces.set(pieces.indexOf(selectedPiece),new Piece(selectedPiece.getFile(),selectedPiece.getRank(),selectedPiece.getPieceColor(),"queen"));
                        setSquare(iv, m.getTargetSquare());
                    }
                    selectedPiece.setMoved(true);
                    numMoves++;
                }
            }
        }
    }

    private Square getSquarebyView(View v) {
        Square s = new Square(0,0);
        for (Square square:boardSquares){

        }
        return s;
    }

    private void setSquare (Piece p, View v, Integer i){
        ImageView square = getSquarebyInt(i);
        v.setId(View.generateViewId());
        pieceLayout.removeAllViewsInLayout();
        pieceLayout.addView(v);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(pieceLayout);
        constraintSet.connect(v.getId(),ConstraintSet.START,square.getId(),ConstraintSet.START);
        constraintSet.connect(v.getId(),ConstraintSet.END,square.getId(),ConstraintSet.END);
        constraintSet.connect(v.getId(),ConstraintSet.TOP,square.getId(),ConstraintSet.TOP);
        constraintSet.connect(v.getId(),ConstraintSet.BOTTOM,square.getId(),ConstraintSet.BOTTOM);
        constraintSet.setScaleX(v.getId(), (float) 0.15);
        constraintSet.setScaleY(v.getId(), (float) 0.15);
        p.setRank(i/8+1);
        p.setFile(i%8+1);
        constraintSet.setVisibility(v.getId(),View.VISIBLE);
        constraintSet.setTranslationZ(v.getId(),1);
        constraintSet.applyTo(pieceLayout);
    }
    private int getSquare(Piece p){
        int rank = p.getRank();
        int file = p.getFile();
        return rank*8+file;
    }
    private int getSquare(Square s){
        int rank = s.getRank();
        int file = s.getFile();
        return rank*8+file;
    }
    public Piece getPiecebySquare(int square){
        for(Piece p: pieces){
            if (getSquare(p) == square) {
                return p;
            }
        }
        return null;
    }
    public ArrayList<Move> getLegalMoves(Piece p, boolean capturesOnly){
        ArrayList <Move> moves = new ArrayList <Move> ();
        String piece = p.getPieceType();
        boolean placeholder = false;
        int currentSquare = getSquare(p);
        if (piece == "bishop"){
            for (int upRight = currentSquare; upRight < 64; upRight += 9)
                if (!openSquare(upRight) && getPiecebySquare(upRight).getPieceColor() != getTurn()) {
                    moves.add(new Move(currentSquare,upRight));
                    break;
                } else if (!openSquare(upRight)){
                    break;
                } else {
                    moves.add(new Move(currentSquare, upRight));
                }
            for (int upLeft = currentSquare; upLeft < 58; upLeft += 7)
                if (!openSquare(upLeft) && getPiecebySquare(upLeft).getPieceColor() != getTurn()) {
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
            if(p.checkFirstMove() && openSquare(2) && openSquare(3) && openSquare(4) && pieces.get(1).checkFirstMove() && notAttacked(5) && notAttacked(4) && notAttacked(3) && notAttacked(2) && p.getPieceColor() == "white"){
                moves.add(new Move(currentSquare,2));
            } // long castle
            if (p.checkFirstMove() && openSquare(6) && openSquare(7) && pieces.get(8).checkFirstMove() && notAttacked(6) && notAttacked(7) && notAttacked(5) && p.getPieceColor() == "white"){
                moves.add(new Move(currentSquare,7));
            }// short castle
            if(p.checkFirstMove() && openSquare(58) && openSquare(59) && openSquare(60) && pieces.get(25).checkFirstMove() && notAttacked(61) && notAttacked(60) && notAttacked(59) &&
                    notAttacked(58) && p.getPieceColor() == "black"){
                moves.add(new Move(currentSquare,59));
            } // long castle
            if (p.checkFirstMove() && openSquare(62) && openSquare(63) && pieces.get(32).checkFirstMove() && notAttacked(62) && notAttacked(63) && notAttacked(61) && p.getPieceColor() == "black"){
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
            if (p.checkFirstMove() && openSquare(currentSquare+16))
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
            if (p.checkFirstMove() && openSquare(currentSquare-16))
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
            if (p.getPieceColor() != getTurn()) {
                pieceMoves = getLegalMoves(p,true);
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
    private int getMaterialValue(Piece p){
        String piece = p.getPieceType();
        if (piece == "bishop" || piece == "knight" || piece == "king")
            return 3;
        else if (piece == "rook")
            return 5;
        else if (piece == "queen")
            return 9;
        else
            return 1;
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
    public static class Square {
        private int rank;
        private int file;
        public Square(int f, int r){
            rank = r;
            file = f;
        }
        public int getFile() {
            return file;
        }

        public int getRank() {
            return rank;
        }
    }
    public static class Piece {
        private int rank;
        private int file;
        private String pieceType;
        private String pieceColor;
        private boolean moved;
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
            moved = false;
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
            return moved;
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
        public void setPieceType(String s){
            pieceType = s;
        }
    }
}