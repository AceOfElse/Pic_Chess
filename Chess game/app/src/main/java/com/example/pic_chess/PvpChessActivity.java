package com.example.pic_chess;

import static com.example.pic_chess.R.drawable;
import static com.example.pic_chess.R.id;
import static com.example.pic_chess.R.layout;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PvpChessActivity extends AppCompatActivity {
    private ImageButton backButton, newGameButton, endButton, resignButton;
    private LinearLayout popupLayout, gameLayout;
    private Button yesButton, noButton, closeButton;
    private TextView timerText1, timerText2, resignText, currentTurnText, textWhiteMoves, textBlackMoves;
    private ConstraintLayout boardLayout, pieceLayout, mainLayout;
    private PopupWindow resignMenu, gameMenu;
    private ArrayList<FrameLayout> boardImageHolders = new ArrayList<>();
    private ArrayList<Square> boardSquares = new ArrayList<Square>();
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


    String CURRENT_TURN = "white";
    Square PREVIOUSLY_CLICKED_SQUARE = null, CURRENTLY_CLICKED_SQUARE = null;

    ArrayList<Integer> highlightedSquareIndexes = null;

    ArrayList<Piece> blackTakenPieces, whiteTakenPieces;

    int white_moves = 0;
    int black_moves = 0;

    // Timer duration in seconds
    int blackTimer = 300, whiteTimer = 300;

    boolean TIMED = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_pvp_chess);

        // To use to check if game is timed
        TIMED = getIntent().getBooleanExtra("TIME", false);

        // Initialize views
        initViews();

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
        initButtons();

        // Initialize game
        initializeGame();
    }

    private void initViews(){
        mainLayout = (ConstraintLayout) findViewById(id.pvpChessLayout);
        pieceLayout = (ConstraintLayout) findViewById(id.pieceLayout);
        gameLayout = new LinearLayout(this);
        popupLayout = new LinearLayout(this);
        resignMenu = new PopupWindow(this);
        gameMenu = new PopupWindow(this);
        resignText = new TextView(this);

        currentTurnText = findViewById(R.id.textCurrentTurn);
        textWhiteMoves = findViewById(id.textWhiteMoves);
        textBlackMoves = findViewById(id.textBlackMoves);

        yesButton = new Button(this);
        yesButton.setText("YES");
        noButton = new Button(this);
        noButton.setText("NO");
        closeButton = new Button(this);
        closeButton.setText("CLOSE");
        backButton = findViewById(id.backButton);
        newGameButton = findViewById(id.newGameButton);
        endButton = findViewById(id.endButton);
        resignButton = findViewById(id.resignButton);
        timerText1 = findViewById(id.timerText1);
        timerText2 = findViewById(id.timerText2);
        boardLayout = (ConstraintLayout) findViewById(id.boardLayout);
    }

    void initializeGame(){
        CURRENT_TURN = "white";
        PREVIOUSLY_CLICKED_SQUARE = null;
        CURRENTLY_CLICKED_SQUARE = null;

        white_moves = 0;
        black_moves = 0;

        blackTakenPieces = new ArrayList<>();
        whiteTakenPieces = new ArrayList<>();
        boardImageHolders.clear();

        //Add board image views to array list (From boardA1 -> board H8, bottom to top, left to right)
        addBoardImages();

        // Current turn text
        updateTurn();

        generatePositionfromFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
    }

    private void addBoardImages(){
        for(FrameLayout l:boardImageHolders){
            boardImageHolders.get(boardImageHolders.indexOf(l)).removeAllViews();
        }

        boardImageHolders.add(findViewById(id.boardA1));
        boardImageHolders.add(findViewById(id.boardB1));
        boardImageHolders.add(findViewById(id.boardC1));
        boardImageHolders.add(findViewById(id.boardD1));
        boardImageHolders.add(findViewById(id.boardE1));
        boardImageHolders.add(findViewById(id.boardF1));
        boardImageHolders.add(findViewById(id.boardG1));
        boardImageHolders.add(findViewById(id.boardH1));
        boardImageHolders.add(findViewById(id.boardA2));
        boardImageHolders.add(findViewById(id.boardB2));
        boardImageHolders.add(findViewById(id.boardC2));
        boardImageHolders.add(findViewById(id.boardD2));
        boardImageHolders.add(findViewById(id.boardE2));
        boardImageHolders.add(findViewById(id.boardF2));
        boardImageHolders.add(findViewById(id.boardG2));
        boardImageHolders.add(findViewById(id.boardH2));
        boardImageHolders.add(findViewById(id.boardA3));
        boardImageHolders.add(findViewById(id.boardB3));
        boardImageHolders.add(findViewById(id.boardC3));
        boardImageHolders.add(findViewById(id.boardD3));
        boardImageHolders.add(findViewById(id.boardE3));
        boardImageHolders.add(findViewById(id.boardF3));
        boardImageHolders.add(findViewById(id.boardG3));
        boardImageHolders.add(findViewById(id.boardH3));
        boardImageHolders.add(findViewById(id.boardA4));
        boardImageHolders.add(findViewById(id.boardB4));
        boardImageHolders.add(findViewById(id.boardC4));
        boardImageHolders.add(findViewById(id.boardD4));
        boardImageHolders.add(findViewById(id.boardE4));
        boardImageHolders.add(findViewById(id.boardF4));
        boardImageHolders.add(findViewById(id.boardG4));
        boardImageHolders.add(findViewById(id.boardH4));
        boardImageHolders.add(findViewById(id.boardA5));
        boardImageHolders.add(findViewById(id.boardB5));
        boardImageHolders.add(findViewById(id.boardC5));
        boardImageHolders.add(findViewById(id.boardD5));
        boardImageHolders.add(findViewById(id.boardE5));
        boardImageHolders.add(findViewById(id.boardF5));
        boardImageHolders.add(findViewById(id.boardG5));
        boardImageHolders.add(findViewById(id.boardH5));
        boardImageHolders.add(findViewById(id.boardA6));
        boardImageHolders.add(findViewById(id.boardB6));
        boardImageHolders.add(findViewById(id.boardC6));
        boardImageHolders.add(findViewById(id.boardD6));
        boardImageHolders.add(findViewById(id.boardE6));
        boardImageHolders.add(findViewById(id.boardF6));
        boardImageHolders.add(findViewById(id.boardG6));
        boardImageHolders.add(findViewById(id.boardH6));
        boardImageHolders.add(findViewById(id.boardA7));
        boardImageHolders.add(findViewById(id.boardB7));
        boardImageHolders.add(findViewById(id.boardC7));
        boardImageHolders.add(findViewById(id.boardD7));
        boardImageHolders.add(findViewById(id.boardE7));
        boardImageHolders.add(findViewById(id.boardF7));
        boardImageHolders.add(findViewById(id.boardG7));
        boardImageHolders.add(findViewById(id.boardH7));
        boardImageHolders.add(findViewById(id.boardA8));
        boardImageHolders.add(findViewById(id.boardB8));
        boardImageHolders.add(findViewById(id.boardC8));
        boardImageHolders.add(findViewById(id.boardD8));
        boardImageHolders.add(findViewById(id.boardE8));
        boardImageHolders.add(findViewById(id.boardF8));
        boardImageHolders.add(findViewById(id.boardG8));
        boardImageHolders.add(findViewById(id.boardH8));

        int f = 0;
        int r = 1;
        for (View v: boardImageHolders){
            f++;
            if (f>8){
                f = 1;
                r++;
            }

            // Color is black if r and f are both odd or both even
            String color = "white";

            if((f%2 == 0 && r%2 == 0) || (f%2 != 0 && r%2 != 0)){
                color = "black";
            }

            boardSquares.add(new Square(f,r,v, color));
        }
    }

    private void initButtons(){
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
                new AlertDialog.Builder(PvpChessActivity.this)
                        .setMessage(getTurn() + " wants to end the game in a draw")
                        .setPositiveButton("Accept", (dialogInterface, i) -> {

                            new AlertDialog.Builder(PvpChessActivity.this)
                                    .setMessage("Game has been ended")
                                    .setPositiveButton("OK", (dialogInterface1, i1) -> {
                                        initializeGame();
                                    })
                                    .show();

                        })
                        .setNegativeButton("Keep Playing", (dialogInterface, i) -> {
                            dialogInterface.dismiss();
                        })
                        .show();
            }
        });

        resignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(PvpChessActivity.this)
                        .setMessage("Are you sure you want to surrender?")
                        .setPositiveButton("Yes", (dialogInterface, i) -> {

                            new AlertDialog.Builder(PvpChessActivity.this)
                                    .setMessage(getTurn() + " has surrendered")
                                    .setPositiveButton("OK", (dialogInterface1, i1) -> {
                                        initializeGame();
                                    })
                                    .show();

                        })
                        .setNegativeButton("No", (dialogInterface, i) -> {
                            dialogInterface.dismiss();
                        })
                        .show();
            }
        });
    }

    public void returnHome() {
        Intent homeIntent = new Intent(PvpChessActivity.this, HomeActivity.class);
        startActivity(homeIntent);
    }

    private void generatePositionfromFEN(String s) {
        int square = 57;
        int index = 0;
        ImageView iv;

        for(int x = 0; x < s.length(); x++){
            iv = new ImageView(PvpChessActivity.this);

            if(s.charAt(x)=='p') {
                iv.setImageResource(drawable.blackpawn);
                pieces.add(new Piece(square % 8 + 1, square / 8 + 1, "black", "pawn", iv));

                Log.d("chess2", pieces.get(index).getPieceType() + " moved to " + square);
                index++;
                square++;
            }else if (s.charAt(x)=='P') {
                iv.setImageResource(drawable.whitepawn);
                pieces.add(new Piece(square % 8 + 1, square / 8 + 1, "white", "pawn", iv));

                Log.d("chess2", pieces.get(index).getPieceType() + " moved to " + square);
                index++;
                square++;
            } else if (s.charAt(x) == '8') {
                square += 8;
                iv = null;
            } else if (s.charAt(x) == '7') {
                square += 7;
                iv = null;
            } else if (s.charAt(x) == '6') {
                square += 6;
                iv = null;
            } else if (s.charAt(x) == '5') {
                square += 5;
                iv = null;
            } else if (s.charAt(x) == '4') {
                square += 4;
                iv = null;
            } else if (s.charAt(x) == '3') {
                square += 3;
                iv = null;
            } else if (s.charAt(x) == '2') {
                square += 2;
                iv = null;
            } else if (s.charAt(x) == '1') {
                square++;
                iv = null;
            } else if (s.charAt(x) == 'r') {
                iv.setImageResource(drawable.blackrook);
                pieces.add(new Piece(square % 8 + 1, square / 8 + 1, "black", "rook", iv));

                Log.d("chess2", pieces.get(index).getPieceType() + " moved to " + square);
                index++;
                square++;
            } else if (s.charAt(x) == 'R') {
                iv.setImageResource(drawable.whiterook);
                pieces.add(new Piece(square % 8 + 1, square / 8 + 1, "white", "rook", iv));

                Log.d("chess2", pieces.get(index).getPieceType() + " moved to " + square);
                index++;
                square++;
            } else if (s.charAt(x) == 'n') {
                iv.setImageResource(drawable.blackknight);
                pieces.add(new Piece(square % 8 + 1, square / 8 + 1, "black", "knight", iv));

                Log.d("chess2", pieces.get(index).getPieceType() + " moved to " + square);
                index++;
                square++;
            } else if (s.charAt(x) == 'N') {
                iv.setImageResource(drawable.whiteknight);
                pieces.add(new Piece(square % 8 + 1, square / 8 + 1, "white", "knight", iv));

                Log.d("chess2", pieces.get(index).getPieceType() + " moved to " + square);
                index++;
                square++;
            } else if (s.charAt(x) == 'b') {
                iv.setImageResource(drawable.blackbishop);
                pieces.add(new Piece(square % 8 + 1, square / 8 + 1, "black", "bishop", iv));

                Log.d("chess2", pieces.get(index).getPieceType() + " moved to " + square);
                index++;
                square++;
            } else if (s.charAt(x) == 'B') {
                iv.setImageResource(drawable.whitebishop);
                pieces.add(new Piece(square % 8 + 1, square / 8 + 1, "white", "bishop", iv));

                Log.d("chess2", pieces.get(index).getPieceType() + " moved to " + square);
                index++;
                square++;
            } else if (s.charAt(x) == 'k') {
                iv.setImageResource(drawable.blackking);
                pieces.add(new Piece(square % 8 + 1, square / 8 + 1, "black", "king", iv));

                Log.d("chess2", pieces.get(index).getPieceType() + " moved to " + square);
                index++;
                square++;
            } else if (s.charAt(x) == 'K') {
                iv.setImageResource(drawable.whiteking);
                pieces.add(new Piece(square % 8 + 1, square / 8 + 1, "white", "king", iv));

                Log.d("chess2", pieces.get(index).getPieceType() + " moved to " + square);
                index++;
                square++;
            } else if (s.charAt(x) == 'q') {
                iv.setImageResource(drawable.blackqueen);
                pieces.add(new Piece(square % 8 + 1, square / 8 + 1, "black", "queen", iv));

                Log.d("chess2", pieces.get(index).getPieceType() + " moved to " + square);
                index++;
                square++;
            } else if (s.charAt(x) == 'Q') {
                iv.setImageResource(drawable.whitequeen);
                pieces.add(new Piece(square % 8 + 1, square / 8 + 1, "white", "queen", iv));

                Log.d("chess2", pieces.get(index).getPieceType() + " moved to " + square);
                index++;
                square++;
            } else if (s.charAt(x) == '/'){
                square -= 16;
                iv = null;
            }

            // Add the image to the board
            if(iv != null) {
                if (iv.getParent() != null) {
                    ((ViewGroup) iv.getParent()).removeView(iv);
                }

                // We use -2 coz we have already incremented square
                // And the array list begins at index 0, not 1 - square is initialized at a value parallel to index 1
                boardImageHolders.get(square - 2).addView(iv);

                // Update squares to hold the info about the added piece
                // -1 for the index since at this point index++ has been executed
                setSquare(pieces.get(index - 1), square - 2);
            }
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
            if (p.getPieceType() == "white" && p.getRank() == 7){
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
        setSquare(p,move.getTargetSquare());
    }

    public void unmakeMove(Move move,Piece p){
        setSquare(p,move.getCurrentSquare());
    }

    public void pieceOnClick(View v){
        Piece p = getPiecebyView(v);

        // Check the turn and compare against piece colour
        if(getTurn().equals(p.getPieceColor())) {
            selectedMoves = getLegalMoves(p,false);
            for (Move m: selectedMoves) {
                getSquarebyInt(m.getTargetSquare()).setBackgroundResource(drawable.redsquare);
            }
            selectedView = (ImageView) v;
            selectedPiece = p;
            getSquarebyInt(getSquare(p)).setBackgroundResource(drawable.goldsquare);
        } else {
            for (Move m: selectedMoves) {
                if(m.getTargetSquare() == getSquare(p)){
                    if (getTurn() == "white")
                        blackMaterial -= getMaterialValue(p);
                    else
                        whiteMaterial -= getMaterialValue(p);
                    p.setMoved(false);
                    setSquare(selectedPiece,m.getTargetSquare());
                    captured.add((ImageView) v);
                    numMoves++;
                }
            }
            checkEnd();
        }
    }

    private void checkEnd() {
        ArrayList<ArrayList<Move>> allLegalMoves = new ArrayList<>();
        for (Piece p:pieces){
            if (getTurn() == p.getPieceColor())
                allLegalMoves.add(getLegalMoves(p,false));
        }
        if (getTurn() == "white") {
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
    }

    public void squareOnClick(View v){
        int clicked_square_index = boardImageHolders.indexOf((FrameLayout) v);
        Log.d("Click:", "Square index " + clicked_square_index);


        // Outline the square clicked
        Square square = getSquarebyView(boardImageHolders.get(clicked_square_index));
        outlineClickedSquare(clicked_square_index, square.getColor());

        // Unhighlight any highlighted squares
        if(highlightedSquareIndexes != null){
            for(int i:highlightedSquareIndexes){
                restoreSquareBackground(i);
            }
        }

        if(moving()){
            validateAndMakeMove(clicked_square_index);
        }else{
            // Highlight moveable squares
            highlightValidMoveSquares(square);
        }

        if (selectedView != null && selectedPiece != null){
            Square s = getSquarebyView(v);
            for (Move m: selectedMoves) {
                if (m.getTargetSquare() == getSquare(s)) {
                    int x = 0;
                    for(FrameLayout i: boardImageHolders){
                        if (x%2 == 0)
                            i.setBackgroundResource(drawable.darksquare);
                        else
                            i.setBackgroundResource(drawable.lightsquare);
                        x++;
                    }
                    setSquare(selectedPiece, m.getTargetSquare());
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
                        selectedPiece.promote(true);
                        selectedPiece.setPieceType("queen");
                        pieces.set(pieces.indexOf(selectedPiece),new Piece(selectedPiece.getFile(),selectedPiece.getRank(),selectedPiece.getPieceColor(),"queen"));
                    }
                    if (getSquare(selectedPiece) > 0 && getSquare(selectedPiece) < 9 && selectedPiece.getPieceType() == "black pawn") {
                        blackMaterial += 8;
                        selectedPiece.promote(false);
                        selectedPiece.setPieceType("queen");
                        pieces.set(pieces.indexOf(selectedPiece),new Piece(selectedPiece.getFile(),selectedPiece.getRank(),selectedPiece.getPieceColor(),"queen"));
                    }
                    selectedPiece.setMoved(true);
                    numMoves++;
                }
            }
            checkEnd();
        }
    }

    private void outlineClickedSquare(int square_index, String color){
        PREVIOUSLY_CLICKED_SQUARE = CURRENTLY_CLICKED_SQUARE;

        // Remove outline from previously clicked square
        if(PREVIOUSLY_CLICKED_SQUARE != null){
            int i = boardSquares.indexOf(PREVIOUSLY_CLICKED_SQUARE);
            String c = PREVIOUSLY_CLICKED_SQUARE.getColor();

            if(c.equalsIgnoreCase("black")){
                boardImageHolders.get(i).setBackgroundResource(drawable.darksquare);
            }else{
                boardImageHolders.get(i).setBackgroundResource(drawable.lightsquare);
            }
        }

        // Set outline of currently clicked
        if(color.equalsIgnoreCase("black")){
            boardImageHolders.get(square_index).setBackgroundResource(drawable.darksquareoutline);
        }else{
            boardImageHolders.get(square_index).setBackgroundResource(drawable.lightsquareoutline);
        }

        // Set the just clicked square as the currently clicked square
        // The one at currently clicked becomes previous
        CURRENTLY_CLICKED_SQUARE = boardSquares.get(square_index);
    }

    private void restoreSquareBackground(int index){
        if(boardSquares.get(index).getColor().equalsIgnoreCase("black")){
            boardImageHolders.get(index).setBackgroundResource(drawable.darksquare);
        }else{
            boardImageHolders.get(index).setBackgroundResource(drawable.lightsquare);
        }
    }

    private void highlightValidMoveSquares(Square square){
        int direction = 1;
        if(getTurn().equalsIgnoreCase("black")) direction = -1;

        // Only highlight current player's moves
        if(square.getPiece() != null && square.getPiece().getPieceColor() == getTurn()) {
            // Direction i 1 if pieces move from lower to high index squares and -1 otherwise
            ArrayList<Integer> indexes = square.getPiece().getValidSquareMovesIndexes(boardSquares.indexOf(square), direction);
            highlightedSquareIndexes = indexes;

            for(int i:indexes){
                Piece piece_at_index = boardSquares.get(i).getPiece();

                if(piece_at_index != null){
                    // Check if board has that player's piece and leave it unhighlighted

                    // Check if it is opponent's piece, and use a different colour
                    // to indicate it can be taken
                    if(!piece_at_index.getPieceColor().equals(getTurn())){
                        boardImageHolders.get(i).setBackgroundResource(drawable.goldsquare);
                    }
                    // else we will leave the square unhighlighted, it contains player's piece
                }else{
                    // No piece, highlight the square
                    boardImageHolders.get(i).setBackgroundResource(drawable.redsquare);
                }

            }
        }
    }

    private boolean moving(){
        if(PREVIOUSLY_CLICKED_SQUARE == null){
            // Has not clicked on a second square
            return false;
        }

        if(PREVIOUSLY_CLICKED_SQUARE.equals(CURRENTLY_CLICKED_SQUARE)){
            // Clicked on same piece twice
            return false;
        }

        // The square clicked before has no piece
        if(PREVIOUSLY_CLICKED_SQUARE.getPiece() == null){
            // Previous square has no piece
            return false;
        }

        // The square last clicked another of his piece
        if(CURRENTLY_CLICKED_SQUARE.getPiece() != null && CURRENTLY_CLICKED_SQUARE.getPiece().getPieceColor().equals(getTurn())){
            // Previous square has no piece
            return false;
        }

        return true;
    }

    private void validateAndMakeMove(int clicked_index){
        // Two different squares clicked
        // With first square having a piece
        attemptMove(PREVIOUSLY_CLICKED_SQUARE, CURRENTLY_CLICKED_SQUARE);
    }

    private void attemptMove(Square from, Square to){
        // Validate the move
        // Check the color of piece the player is attempting to move
        if(!getTurn().equalsIgnoreCase(from.getPiece().getPieceColor())){
            Toast.makeText(this, "You cannot move that piece!", Toast.LENGTH_LONG).show();
            return;
        }

        // Check if to is within the valid moves of the piece in from
        if(!highlightedSquareIndexes.contains(boardSquares.indexOf(to))){
            Toast.makeText(this, "Not a valid move!", Toast.LENGTH_LONG).show();
            return;
        }

        int to_index = boardSquares.indexOf(to);
        int from_index = boardSquares.indexOf(from);

        // Make move
        // Check if player will take opponents piece
        if(to.getPiece() != null){
            takePiece(to);

            // Remove the taken piece image from its square
            boardImageHolders.get(to_index).removeAllViews();
        }

        // Remove the moving piece from its original square
        boardImageHolders.get(from_index).removeAllViews();

        // Set image in new square
        ImageView iv = from.getPiece().getPic();
        if(iv.getParent() != null){
            ((ViewGroup) iv.getParent()).removeView(iv);
        }

        boardImageHolders.get(to_index).addView(iv);

        // Update pieces data in squares data
        // Mark the from piece as moved
        from.getPiece().setMoved(true);
        to.setPiece(from.getPiece());
        from.setPiece(null);

        boardSquares.set(from_index, from);
        boardSquares.set(to_index, to);

        // Update the number of moves
        if(getTurn().equalsIgnoreCase("white")){
            white_moves++;
        }else{
            black_moves++;
        }

        // Change turn
        changeTurn();
    }

    private void changeTurn(){
        if(CURRENT_TURN.equalsIgnoreCase("white")){
            CURRENT_TURN = "black";
        }else{
            CURRENT_TURN = "white";
        }

        updateTurn();
    }

    private void updateTurn(){
        currentTurnText.setText(getTurn() + "'s Turn");
        textWhiteMoves.setText("White Moves: " + white_moves);
        textBlackMoves.setText("Black Moves: " + black_moves);
        highlightedSquareIndexes = null;
    }

    private void takePiece(Square from){
        Piece piece = from.getPiece();

        if(piece.getPieceColor().equalsIgnoreCase("black")){
            blackTakenPieces.add(piece);
        }else{
            whiteTakenPieces.add(piece);
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

    private void setSquare (Piece p, int index){
        Square square = boardSquares.get(index);
        square.setPiece(p);

        boardSquares.set(index, square);
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

    public ArrayList<Move> getMoves(Piece p, boolean capturesOnly){
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

    private ArrayList<Move> getLegalMoves (Piece p, boolean capturesOnly){
        ArrayList<Move> moves = getMoves(p,capturesOnly);
        ArrayList <Move> legalMoves = new ArrayList<Move>();
        int myKingSquare=0;
        for (Piece piece:pieces){
            if (getTurn() == piece.getPieceColor() && piece.getPieceType() == "king"){
                myKingSquare = getSquare(piece);
            }
        }
        for (Move moveToVerify:moves){
            makeMove(moveToVerify,p);
            for (Piece piece: pieces){
                if (getTurn() != piece.getPieceColor()){
                    ArrayList<Move> opponentMoves = getMoves(piece,true);
                    for (Move opponentMove:opponentMoves) {
                        if (opponentMove.getTargetSquare() == myKingSquare) {

                        } else {
                            legalMoves.add(moveToVerify);
                        }
                    }
                }
            }
            unmakeMove(moveToVerify,p);
        }
        return legalMoves;
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
        for (Piece p: pieces)
            if (getTurn() == "white")
                return !notAttacked(getSquare(pieces.get(4)));
        return !notAttacked(getSquare(pieces.get(28)));
    }

    private boolean pinnedPiece(Piece p){
        ArrayList <Move> moves = getLegalMoves(p,false);
        boolean result = false;
        for (Move m: moves){
            makeMove(m,p);
            if (!notAttacked(getSquare(pieces.get(4)))) {
                result = true;
            }
            unmakeMove(m,p);
        }
        return result;
    }

    private String getTurn(){
        return CURRENT_TURN;
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

    public Piece getPiecebyView(View v){
        for (Piece p: pieces){
            if (p.getPic() == v){
                return p;
            }
        }
        return null;
    }

    private FrameLayout getSquarebyInt(int i){
        return boardImageHolders.get(i - 1);
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
        private View view;
        private int x;
        private int y;
        Piece piece = null;

        // Colour of the square
        private String color;

        public Square(int f, int r, View v, String color){
            rank = r;
            file = f;
            view = v;
            this.color = color;
            x = (int)v.getTranslationX();
            y = (int)v.getTranslationY();
        }
        public int getFile() {
            return file;
        }

        public int getRank() {
            return rank;
        }

        public String getColor(){ return color; }

        public View getView() {
            return view;
        }
        public Point getPoint(){
            Point point = new Point();
            point.set(x,y);
            return point;
        }

        public Piece getPiece() {
            return piece;
        }

        public void setPiece(Piece piece) {
            this.piece = piece;
        }
    }
    public static class Piece {
        private int rank,file,x,y;
        private String pieceType,pieceColor;
        private boolean moved;
        private ImageView pic;
        public Piece(){
            rank = 0;
            file = 0;
            pieceColor = "";
            pieceType = "";
            moved = false;
            x = 0;
            y = 0;
        }
        public Piece(int f, int r, String c, String t){
            rank = r;
            file = f;
            pieceColor = c;
            pieceType = t;
            x = 0;
            y = 0;
        }
        public Piece(int f, int r, String c, String t, ImageView i){
            rank = r;
            file = f;
            pieceColor = c;
            pieceType = t;
            moved = false;
            pic = i;
            x = 0;
            y = 0;
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
            setPieceType("queen");
            if (white)
                pic.setImageResource(R.drawable.whitequeen);
            else
                pic.setImageResource(R.drawable.blackqueen);
        }

        public Point getPoint(){
            Point point = new Point();
            point.set(x,y);
            return point;
        }
        public void setPoint(Point p){
            int x1 = p.x;
            int y1 = p.y;
            x = x1;
            y = y1;
        }

        public ArrayList<Integer> getValidSquareMovesIndexes(int currentSquareIndex, int direction){
            ArrayList<Integer> moves = new ArrayList<>();

            if(getPieceType().equalsIgnoreCase("pawn")){
                moves.add(currentSquareIndex + (8 * direction));

                if(checkFirstMove()){
                    moves.add(currentSquareIndex + (16 * direction));
                }
            }else if(getPieceType().equalsIgnoreCase("rook")){
                for(int i=1; i<8; i++){
                    int nextIndex = currentSquareIndex + (i * 8) * direction;

                    if(nextIndex > 63 || nextIndex < 0){
                        break;
                    }

                    moves.add(nextIndex);
                }
            } else if(getPieceType().equalsIgnoreCase("knight"))
            {
                // Has 8 possible moves
                // Check if movable in that direction

                // Can it move 2 steps in left direction?
                if((currentSquareIndex % 8) >= 2){
                    // Can it move 1 step up?
                    if((currentSquareIndex + 8) <= 63){
                        moves.add(currentSquareIndex - 2 + 8);
                    }

                    // Can it move 1 step down?
                    if((currentSquareIndex - 8) > 0){
                        moves.add(currentSquareIndex - 2 - 8);
                    }
                }

                // Can it move 2 steps in right direction?
                if((currentSquareIndex % 8) <= 5){
                    // Can it move 1 step up?
                    if((currentSquareIndex + 8) <= 63){
                        moves.add(currentSquareIndex + 2 + 8);
                    }

                    // Can it move 1 step down?
                    if((currentSquareIndex - 8) > 0){
                        moves.add(currentSquareIndex + 2 - 8);
                    }
                }

                // Can it move 1 step in left direction?
                if((currentSquareIndex % 8) >= 1){
                    // Can it move 2 steps up?
                    if((currentSquareIndex + 16) <= 63){
                        moves.add(currentSquareIndex - 1 + 16);
                    }

                    // Can it move 1 step down?
                    if((currentSquareIndex - 16) > 0){
                        moves.add(currentSquareIndex - 1 - 16);
                    }
                }

                // Can it move 1 step in right direction?
                if((currentSquareIndex % 8) <= 6){
                    // Can it move 2 steps up?
                    if((currentSquareIndex + 16) <= 63){
                        moves.add(currentSquareIndex + 1 + 16);
                    }

                    // Can it move 2 steps down?
                    if((currentSquareIndex - 16) > 0){
                        moves.add(currentSquareIndex + 1 - 16);
                    }
                }


            }else if(getPieceType().equalsIgnoreCase("bishop")){
                for(int i=1; i<8; i++){
                    // Can it move i step (s) left?
                    if((currentSquareIndex - i) % 8 > 0){
                        // Can it move i steps up?
                        if((currentSquareIndex + (i * 8) <= 63) && ((currentSquareIndex - i + (i * 8)) <= 63)){
                            moves.add(currentSquareIndex - i + (i*8));
                        }

                        // Can it move i steps down?
                        if((currentSquareIndex - (i * 8) >= 0) && ((currentSquareIndex - i - (i * 8)) >= 0)){
                            moves.add(currentSquareIndex - i - (i*8));
                        }
                    }

                    // Can it move i step (s) right?
                    if((currentSquareIndex + i) % 8 > 0){
                        // Can it move i steps up?
                        if(((currentSquareIndex + (i * 8) <= 63) && ((currentSquareIndex + i + (i * 8)) <= 63))){
                            moves.add(currentSquareIndex + i + (i*8));
                        }

                        // Can it move i steps down?
                        if((currentSquareIndex - (i * 8) >= 0) && ((currentSquareIndex + i - (i * 8)) >= 0)){
                            moves.add(currentSquareIndex + i - (i*8));
                        }
                    }
                }
            }

            return moves;
        }
    }
}