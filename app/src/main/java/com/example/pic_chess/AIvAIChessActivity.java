package com.example.pic_chess;

import static com.example.pic_chess.R.id;
import static com.example.pic_chess.R.layout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import java.util.ArrayList;
import java.util.Collections;

public class AIvAIChessActivity extends AppCompatActivity {
    private ConstraintLayout deadLayout;
    private LinearLayout.LayoutParams layoutParams;
    private LinearLayout deadWhite, deadBlack;
    private final ArrayList<ConstraintLayout> boardLayout = new ArrayList<>();
    private final ArrayList<ImageView> boardImages = new ArrayList<>();
    private final ArrayList<Square> boardSquares = new ArrayList<>();
    private final ArrayList<Piece> pieces = new ArrayList<>();
    private final ArrayList<ImageView> captured = new ArrayList<>();
    private final ArrayList<String> positions = new ArrayList<>();
    private int whiteMaterial = 0;
    private int blackMaterial = 0;
    private int movesSinceLastPawnMove = 0;
    private int numMoves = 0;
    private boolean gameInProgress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_aivai_chess);
        deadLayout = findViewById(id.deadPieceLayout);
        deadWhite = new LinearLayout(this);
        deadBlack = new LinearLayout(this);
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        deadBlack.setOrientation(LinearLayout.HORIZONTAL);
        deadWhite.setOrientation(LinearLayout.HORIZONTAL);
        ImageButton backButton = findViewById(id.backButton);
        ImageButton newGameButton = findViewById(id.newGameButton);
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
        //Set button listeners
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!gameInProgress)
                    returnHome();
            }
        });
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!gameInProgress){
                    startNewGame();
                }
            }
        });
        generatePositionfromFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
    }
    private void startNewGame(){
        gameInProgress = true;
        int whiteMove;
        int blackMove;
        while (gameInProgress){
            if (getTurn().equals("white")){
                whiteMove = search(5,Integer.MIN_VALUE,Integer.MAX_VALUE);
                makeMove(getMovebyEval(whiteMove));
            } else {
                blackMove = search(5,Integer.MIN_VALUE,Integer.MAX_VALUE);
                makeMove(getMovebyEval(blackMove));
            }
            Log.d("ai","done searching for move");
            numMoves++;
            checkEnd();
        }
    }
    private void makeMove(Move m){
        if (m != null){
            Piece p = getPiecebySquare(m.getCurrentSquare());
            int targetSquare = m.getTargetSquare();
            if (getPiecebySquare(m.getTargetSquare()) == null) {
                Log.d("moveMade","Moved "+ p.getPieceType() + " to " + m.getTargetSquare() + " and did not capture (except en passant)");
                setSquare(p, targetSquare);
                Square s = getSquarebyView(p.getPic());
                if (getSquare(p) == 3 && p.checkFirstMove() && p.getPieceType().equals("king")) {
                    setSquare(pieces.get(24), 4);
                    pieces.get(24).setMoved(true);
                }
                if (getSquare(p) == 7 && p.checkFirstMove() && p.getPieceType().equals("king")) {
                    setSquare(pieces.get(31), 6);
                    pieces.get(31).setMoved(true);
                }
                if (getSquare(p) == 59 && p.checkFirstMove() && p.getPieceType().equals("king")) {
                    setSquare(pieces.get(0), 60);
                    pieces.get(0).setMoved(true);
                }
                if (getSquare(p) == 63 && p.checkFirstMove() && p.getPieceType().equals("king")) {
                    setSquare(pieces.get(7), 62);
                    pieces.get(7).setMoved(true);
                }
                if (getSquare(p) > 56 && getSquare(p) < 65 && p.getPieceType().equals("white pawn")) {
                    whiteMaterial += 8;
                    p.promote(true);
                    pieces.set(pieces.indexOf(p), new Piece(p.getFile(), p.getRank(), p.getPieceColor(), "queen"));
                }
                if (getSquare(p) > 0 && getSquare(p) < 9 && p.getPieceType().equals("black pawn")) {
                    blackMaterial += 8;
                    p.promote(false);
                    pieces.set(pieces.indexOf(p), new Piece(p.getFile(), p.getRank(), p.getPieceColor(), "queen"));
                }
                if (p.getPieceType().equals("white pawn") || p.getPieceType().equals("blackPawn")) {
                    movesSinceLastPawnMove = 0;
                } else {
                    movesSinceLastPawnMove++;
                }
                if (s != null) {
                    if (p.getPieceType().equals("white pawn") && getPiecebySquare(getSquare(s) - 8) != null && getPiecebySquare(getSquare(s) - 8).getMovedTwo()) {
                        Piece p2 = getPiecebySquare(getSquare(s) - 8);
                        capture(p2);
                    }
                    if (p.getPieceType().equals("black pawn") && getPiecebySquare(getSquare(s) + 8) != null && getPiecebySquare(getSquare(s) + 8).getMovedTwo()) {
                        Piece p2 = getPiecebySquare(getSquare(s) + 8);
                        capture(p2);
                    }
                }
                if (p.getPieceType().equals("white pawn") && p.getMoved() && getPiecebySquare(getSquare(p) - 16) == null && getPiecebySquare(getSquare(p) - 8) == null) {
                    p.setMovedTwo(true);
                }
                if (p.getPieceType().equals("black pawn") && p.getMoved() && getPiecebySquare(getSquare(p) + 16) == null && getPiecebySquare(getSquare(p) + 8) == null) {
                    p.setMovedTwo(true);
                }
            } else {
                Piece p2 = getPiecebySquare(m.getTargetSquare());
                Log.d("moveMade","Moved "+ p.getPieceType() + " to " + m.getTargetSquare() + " and captured a " + p2.getPieceType());
                if (getTurn().equals("white"))
                    blackMaterial -= getMaterialValue(p);
                else
                    whiteMaterial -= getMaterialValue(p);
                setSquare(p, m.getTargetSquare());
                capture(p2);
            }
            p.setMoved(true);
            positions.add(getFENfromPosition());
        }
    }

    private void capture(Piece p2) {
        p2.setFile(69);
        p2.setRank(69);
        ImageView v = p2.getPic();
        if ((ViewGroup)v.getParent() != null)
            ((ViewGroup)v.getParent()).removeView(v);
        if (p2.getPieceColor() == "white")
            deadWhite.addView(v,layoutParams);
        else
            deadBlack.addView(v,layoutParams);
        captured.add(v);
    }

    public Move getMovebyEval(int eval){
        ArrayList<Move> moves;
        for (Piece p: pieces) {
            if (getTurn().equals(p.getPieceColor())) {
                moves = getLegalMoves(p, false);
                for (Move m : moves) {
                    if (m.getEvaluation() == eval){
                        Log.d("moveFound", "Move found");
                        return m;
                    }
                }
            }
        }
        return null;
    }
    public ArrayList<Move> generateAllMoves(String turn){
        ArrayList<ArrayList<Move>> movesSquared = new ArrayList<ArrayList<Move>>();
        ArrayList<Move> moves = new ArrayList<Move>();
        for (Piece p: pieces){
            if (p.getPieceColor().equals(turn)){
                movesSquared.add(getLegalMoves(p,false));
            }
        }
        for (ArrayList<Move> move:movesSquared){
            moves.addAll(move);
        }
        return moves;
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
        ArrayList<Move> moves = generateAllMoves(getTurn());
        if (depth == 0)
            return searchAllCaptures(alpha,beta);
        int myKing=-1;
        for (Piece p: pieces){
            if (p.getPieceColor().equals(getTurn()) && p.getPieceType().equals("king")){
                myKing = getSquare(p);
            }
        }
        if (moves.size() == 0) {
            if (playerInCheck(getTurn(),myKing))
                return Integer.MIN_VALUE;
            return 0;
        }
        for (Move m: moves){
            makeMove(m,m.getPiece());
            numMoves++;
            int eval = -search(depth-1, -beta, -alpha);
            m.setEvaluation(eval);
            numMoves--;
            unmakeMove(m,m.getPiece());
            if (eval >= beta){
                //Move was too good, opponent will avoid this pos
                return beta;
            }
            alpha = Math.max(alpha, eval);
        }
        return alpha;
    }
    public int searchAllCaptures(int alpha, int beta){
        ArrayList<Move> moves;
        int eval = evaluate();
        if (eval >= beta)
            return beta;
        alpha = Math.max(alpha,eval);
        for(Piece p: pieces) {
            moves = getLegalMoves(p,true);
            orderMoves(moves,p);
            for(Move m: moves){
                makeMove(m,p);
                numMoves++;
                eval = -searchAllCaptures(-beta,-alpha);
                numMoves--;
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
            m.setEvaluation(moveScoreGuess);
        }
        Move move;
        for (int c = 0; c < moves.size(); c++){
            for (int c1 = 1; c1 < moves.size(); c1++){
                if (moves.get(c1).getEvaluation() > moves.get(c).getEvaluation() && getTurn().equals("white")){
                    move = moves.get(c1);
                    moves.set(c1, moves.get(c));
                    moves.set(c,move);
                } else if (moves.get(c1).getEvaluation() < moves.get(c).getEvaluation() && getTurn().equals("black")){
                    move = moves.get(c1);
                    moves.set(c1, moves.get(c));
                    moves.set(c,move);
                }
            }
        }
    }
    public int forceKingtoCornerEndgameEval (float endgameWeight){
        int opponentKingRank = pieces.get(4).getRank();
        int opponentKingFile = pieces.get(4).getFile();
        int opponentKingDstToCenterFile = Math.max(3-opponentKingFile,opponentKingFile-4);
        int opponentKingDstToCenterRank = Math.max(3-opponentKingRank,opponentKingRank-4);
        int eval = opponentKingDstToCenterFile + opponentKingDstToCenterRank;
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

    private void checkEnd() {
        ArrayList<ArrayList<Move>> allLegalMoves = new ArrayList<>();
        for (Piece p:pieces){
            if (getTurn().equals(p.getPieceColor()))
                allLegalMoves.add(getLegalMoves(p,false));
        }
        if (getTurn().equals("white")) {
            if (allLegalMoves.get(0) == null && !notAttacked(getSquare(pieces.get(4)))) {
                gameInProgress = false; //Checkmate
                Log.d("end", "White checkmated Black");
            } else if (allLegalMoves.get(0) == null) {
                gameInProgress = false; //Stalemate
                Log.d("end", "Stalemate");
            }
        } else {
            if (allLegalMoves.get(0) == null && !notAttacked(getSquare(pieces.get(29)))) {
                gameInProgress = false; //Checkmate
                Log.d("end", "Black checkmated White");
            } else if (allLegalMoves.get(0) == null) {
                gameInProgress = false; //Stalemate
                Log.d("end", "Stalemate");
            }
        }
        if (movesSinceLastPawnMove == 100){
            gameInProgress = false; //Draw by 50 Move Rule
            Log.d("end", "Draw by 50 move rule");
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
        if (whiteMaterial <= 3 && blackMaterial <= 3) {
            gameInProgress = false; //Draw by Insufficient Material
            Log.d("end", "Draw by Insufficient Material");

        }
        Log.d("game",""+gameInProgress);
    }

    private Square getSquarebyView(View v) {
        for (Square square:boardSquares){
            if (square.getView() == (ImageView) v){
                return square;
            }
        }
        return null;
    }

    private void setSquare (Piece p, Integer i){
        ImageView v = p.getPic();
        ImageView square = getSquarebyInt(i);
        Square s = getSquarebyView(square);
        if ((ViewGroup)v.getParent() != null)
            ((ViewGroup)v.getParent()).removeView(v);
        if (s != null) {
            s.getLayout().addView(v);
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(s.getLayout());
            constraintSet.connect(v.getId(),ConstraintSet.START,square.getId(),ConstraintSet.START);
            constraintSet.connect(v.getId(),ConstraintSet.END,square.getId(),ConstraintSet.END);
            constraintSet.connect(v.getId(),ConstraintSet.TOP,square.getId(),ConstraintSet.TOP);
            constraintSet.connect(v.getId(),ConstraintSet.BOTTOM,square.getId(),ConstraintSet.BOTTOM);
            p.setRank((i-1)/8+1);
            p.setFile((i-1)%8+1);
            constraintSet.setVisibility(v.getId(),View.VISIBLE);
            constraintSet.setTranslationZ(v.getId(),1);
            constraintSet.applyTo(s.getLayout());
        }
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

    private boolean movedTwoBySquare(int square) {
        Piece p = getPiecebySquare(square);
        return p.getMovedTwo();
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
        ArrayList <Integer> movesToDelete = new ArrayList<>();
        for (Move moveToVerify:moves){
            makeMove(moveToVerify,p);
            Log.d("chess6","move made " + moveToVerify.getCurrentSquare() + " to " + moveToVerify.getTargetSquare());
            if (playerInCheck(otherTurn, myKingSquare)){
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
        ArrayList <Move> pieceMoves;
        for (Piece p: pieces){
            if (!p.getPieceColor().equals(getTurn()) && !p.getPieceType().equals("king")) {
                pieceMoves = getMoves(p,true, p.getPieceColor());
                for (Move m : pieceMoves) {
                    if (m.getTargetSquare() == square)
                        return false;
                }
            }
        }
        Log.d("notattacked",square + " is not attacked");
        return true;
    }
    private boolean notAttacked(int square, String otherTurn) {
        ArrayList <Move> pieceMoves;
        for (Piece p: pieces){
            if (!p.getPieceColor().equals(otherTurn) && !p.getPieceType().equals("king")) {
                pieceMoves = getMoves(p,true, p.getPieceColor());
                for (Move m : pieceMoves) {
                    if (m.getTargetSquare() == square)
                        return false;
                }
            }
        }
        Log.d("notattacked",square + " is not attacked");
        return true;
    }
    private boolean playerInCheck(String otherTurn, int myKing){
        return !notAttacked(myKing,otherTurn);
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
    public ArrayList<Move> getMoves(Piece p, boolean capturesOnly, String turn) {
        ArrayList<Move> moves = new ArrayList<>();
        String piece = p.getPieceType();
        int currentSquare = getSquare(p);
        if (piece.equals("bishop")) {
            for (int upRight = currentSquare+9; upRight < 64; upRight += 9) {
                if (upRight % 8 == 1){
                    break;
                } else if (!openSquare(upRight) && !getPiecebySquare(upRight).getPieceColor().equals(turn) && !capturesOnly) {
                    moves.add(new Move(currentSquare, upRight, p));
                    break;
                } else if (!openSquare(upRight)) {
                    break;
                } else if (!capturesOnly) {
                    moves.add(new Move(currentSquare, upRight, p));
                }
            }
            for (int upLeft = currentSquare+7; upLeft < 58 || upLeft % 8 == 0; upLeft += 7) {
                if (upLeft % 8 == 0){
                    break;
                } else if (!openSquare(upLeft) && !getPiecebySquare(upLeft).getPieceColor().equals(turn) && !capturesOnly) {
                    moves.add(new Move(currentSquare, upLeft, p));
                    break;
                } else if (!openSquare(upLeft)) {
                    break;
                } else if (!capturesOnly){
                    moves.add(new Move(currentSquare, upLeft, p));
                }
            }
            for (int downRight = currentSquare-7; downRight > 7 || downRight % 8 == 1; downRight -= 7){
                if (downRight % 8 == 1){
                    break;
                } else if (!openSquare(downRight) && !getPiecebySquare(downRight).getPieceColor().equals(turn) && !capturesOnly) {
                    moves.add(new Move(currentSquare, downRight, p));
                    break;
                } else if (!openSquare(downRight)) {
                    break;
                } else if (!capturesOnly)
                    moves.add(new Move(currentSquare, downRight, p));
            }
            for (int downLeft = currentSquare-9; downLeft > 0 || downLeft % 8 == 0; downLeft -= 9) {
                if (downLeft % 8 == 0){
                    break;
                } else if (!openSquare(downLeft) && !getPiecebySquare(downLeft).getPieceColor().equals(turn) && !capturesOnly) {
                    moves.add(new Move(currentSquare, downLeft, p));
                    break;
                } else if (!openSquare(downLeft)) {
                    break;
                } else if (!capturesOnly)
                    moves.add(new Move(currentSquare, downLeft, p));
            }
        } else if (piece.equals("king")){
            if(p.checkFirstMove() && openSquare(2) && openSquare(3) && openSquare(4) && pieces.get(24).checkFirstMove() && notAttacked(5) && notAttacked(4) && notAttacked(3) && notAttacked(2) && p.getPieceColor().equals("white")){
                moves.add(new Move(currentSquare,3, p));
            } // long castle
            if (p.checkFirstMove() && openSquare(6) && openSquare(7) && pieces.get(31).checkFirstMove() && notAttacked(6) && notAttacked(7) && notAttacked(5) && p.getPieceColor().equals("white")){
                moves.add(new Move(currentSquare,7, p));
            }// short castle
            if(p.checkFirstMove() && openSquare(58) && openSquare(59) && openSquare(60) && pieces.get(0).checkFirstMove() && notAttacked(61) && notAttacked(60) && notAttacked(59) &&
                    notAttacked(58) && p.getPieceColor().equals("black")){
                moves.add(new Move(currentSquare,59, p));
            } // long castle
            if (p.checkFirstMove() && openSquare(62) && openSquare(63) && pieces.get(7).checkFirstMove() && notAttacked(62) && notAttacked(63) && notAttacked(61) && p.getPieceColor().equals("black")){
                moves.add(new Move(currentSquare,63, p));
            }// short castle
            if (notAttacked(currentSquare-1) && currentSquare % 8 != 1 && ((getPiecebySquare(currentSquare-1) != null && !getPiecebySquare(currentSquare -1).getPieceColor().equals(turn)) || getPiecebySquare(currentSquare-1) == null)){
                moves.add(new Move(currentSquare,currentSquare-1, p));
            }
            if (notAttacked(currentSquare+1) && currentSquare % 8 != 0 && ((getPiecebySquare(currentSquare+1) != null && !getPiecebySquare(currentSquare + 1).getPieceColor().equals(turn)) || getPiecebySquare(currentSquare+1) == null)){
                moves.add(new Move(currentSquare,currentSquare+1, p));
            }
            if (notAttacked(currentSquare+8) && currentSquare < 57 && ((getPiecebySquare(currentSquare+8) != null && !getPiecebySquare(currentSquare + 8).getPieceColor().equals(turn)) || getPiecebySquare(currentSquare+8) == null)){
                moves.add(new Move(currentSquare,currentSquare+8, p));
            }
            if (notAttacked(currentSquare-8) && currentSquare > 8 && ((getPiecebySquare(currentSquare-8) != null && !getPiecebySquare(currentSquare -8).getPieceColor().equals(turn)) || getPiecebySquare(currentSquare-8) == null)){
                moves.add(new Move(currentSquare,currentSquare-8, p));
            }
            if (notAttacked(currentSquare-9) && currentSquare > 9 && currentSquare % 8 != 1 && ((getPiecebySquare(currentSquare-9) != null && !getPiecebySquare(currentSquare - 9).getPieceColor().equals(turn)) || getPiecebySquare(currentSquare-9) == null)){
                moves.add(new Move (currentSquare,currentSquare-9, p));
            }
            if (notAttacked(currentSquare+9) && currentSquare < 56 && currentSquare % 8 != 0 && ((getPiecebySquare(currentSquare+9) != null && !getPiecebySquare(currentSquare + 9).getPieceColor().equals(turn)) || getPiecebySquare(currentSquare+9) == null)){
                moves.add(new Move(currentSquare,currentSquare+9, p));
            }
            if (notAttacked(currentSquare-7) && currentSquare > 8 && currentSquare % 8 != 0 && ((getPiecebySquare(currentSquare-7) != null && !getPiecebySquare(currentSquare - 7).getPieceColor().equals(turn)) || getPiecebySquare(currentSquare-7) == null)){
                moves.add(new Move(currentSquare,currentSquare-7, p));
            }
            if (notAttacked(currentSquare+7) && currentSquare < 56 && currentSquare % 8 != 1 && ((getPiecebySquare(currentSquare+7) != null && !getPiecebySquare(currentSquare + 7).getPieceColor().equals(turn)) || getPiecebySquare(currentSquare+7) == null)){
                moves.add(new Move(currentSquare,currentSquare+7, p));
            }
        } else if (piece.equals("knight")) {
            if (currentSquare % 8 != 0){
                if (currentSquare-15>=1 && (openSquare(currentSquare-15) || (!openSquare(currentSquare-15) && !getPiecebySquare(currentSquare - 15).getPieceColor().equals(turn))))
                    moves.add(new Move(currentSquare,currentSquare - 15, p));
                if (currentSquare+17<=64 && (openSquare(currentSquare+17) || (!openSquare(currentSquare+17) && !getPiecebySquare(currentSquare + 17).getPieceColor().equals(turn))))
                    moves.add(new Move(currentSquare,currentSquare+17, p));
            }
            if (currentSquare % 8 != 1) {
                if (currentSquare-17>=1 && (openSquare(currentSquare-17) || (!openSquare(currentSquare-17) && !getPiecebySquare(currentSquare - 17).getPieceColor().equals(turn))))
                    moves.add(new Move(currentSquare,currentSquare - 17, p));
                if (currentSquare+15 <= 64 && (openSquare(currentSquare+15) || (!openSquare(currentSquare+15) && !getPiecebySquare(currentSquare + 15).getPieceColor().equals(turn))))
                    moves.add(new Move(currentSquare,currentSquare+15, p));
            }
            if (currentSquare % 8 != 7 && currentSquare % 8 != 0) {
                if (currentSquare - 6 >= 1 && (openSquare(currentSquare-6) || (!openSquare(currentSquare-6) && !getPiecebySquare(currentSquare - 6).getPieceColor().equals(turn))))
                    moves.add(new Move (currentSquare,currentSquare - 6, p));
                if (currentSquare + 10 <= 64 && (openSquare(currentSquare+10) || (!openSquare(currentSquare+10) && !getPiecebySquare(currentSquare + 10).getPieceColor().equals(turn))))
                    moves.add(new Move (currentSquare,currentSquare+10, p));
            }
            if (currentSquare % 8 != 1 && currentSquare % 8 != 2) {
                if (currentSquare - 10 >= 1 && (openSquare(currentSquare-10) || (!openSquare(currentSquare-10) && !getPiecebySquare(currentSquare - 10).getPieceColor().equals(turn))))
                    moves.add(new Move(currentSquare,currentSquare - 10, p));
                if (currentSquare + 6 <= 64 && (openSquare(currentSquare+6) || (!openSquare(currentSquare+6) && !getPiecebySquare(currentSquare + 6).getPieceColor().equals(turn))))
                    moves.add(new Move(currentSquare,currentSquare + 6, p));
            }
        } else if (piece.equals("white pawn")) {
            if (openSquare(currentSquare+8) && !capturesOnly)
                moves.add(new Move(currentSquare,currentSquare + 8, p));
            if (p.checkFirstMove() && openSquare(currentSquare+16) && !capturesOnly && openSquare(currentSquare+8))
                moves.add(new Move(currentSquare, currentSquare + 16, p));
            if (!openSquare(currentSquare-1) && openSquare(currentSquare+7) && currentSquare < 41 && currentSquare > 33 && movedTwoBySquare(currentSquare-1))
                moves.add(new Move(currentSquare,currentSquare+7, p));
            //en passant left
            if (!openSquare(currentSquare+1) && openSquare(currentSquare+9) && currentSquare < 40 && currentSquare > 32 && movedTwoBySquare(currentSquare+1))
                moves.add(new Move(currentSquare,currentSquare+9, p));
            //en passant right
            if (!openSquare(currentSquare+7) && currentSquare % 8 != 1 && !getPiecebySquare(currentSquare + 7).getPieceColor().equals(turn))
                moves.add(new Move (currentSquare,currentSquare+7, p));
            if (!openSquare(currentSquare+9) && currentSquare % 8 != 0 && !getPiecebySquare(currentSquare + 9).getPieceColor().equals(turn))
                moves.add(new Move(currentSquare,currentSquare+9, p));
        } else if (piece.equals("black pawn")){
            if (openSquare(currentSquare-8) && !capturesOnly)
                moves.add(new Move(currentSquare,currentSquare - 8, p));
            if (p.checkFirstMove() && openSquare(currentSquare-16) && !capturesOnly && openSquare(currentSquare-8))
                moves.add(new Move (currentSquare,currentSquare - 16, p));
            if (!openSquare(currentSquare+1) && openSquare(currentSquare-7) && currentSquare < 32 && currentSquare > 24 && movedTwoBySquare(currentSquare+1))
                moves.add(new Move(currentSquare,currentSquare-7, p));
            //en passant right
            if (!openSquare(currentSquare-1) && openSquare(currentSquare-9) && currentSquare < 32 && currentSquare > 24 && movedTwoBySquare(currentSquare-1))
                moves.add(new Move(currentSquare,currentSquare-9, p));
            //en passant left
            if (!openSquare(currentSquare-7) && currentSquare % 8 != 0 && !getPiecebySquare(currentSquare - 7).getPieceColor().equals(turn))
                moves.add(new Move (currentSquare,currentSquare-7, p));
            if (!openSquare(currentSquare-9) && currentSquare % 8 != 1 && !getPiecebySquare(currentSquare - 9).getPieceColor().equals(turn))
                moves.add(new Move (currentSquare,currentSquare-9, p));
        } else if (piece.equals("queen")) {
            for (int y = currentSquare+8; y < 65; y += 8)
                if (!openSquare(y) && !turn.equals(getPiecebySquare(y).getPieceColor()) && !capturesOnly) {
                    moves.add(new Move(currentSquare, y, p));
                    break;
                } else if (!openSquare(y)) {
                    break;
                } else if (!capturesOnly) {
                    moves.add(new Move(currentSquare, y, p));
                }
            for (int y = currentSquare-8; y > 0; y -= 8)
                if (!openSquare(y) && !turn.equals(getPiecebySquare(y).getPieceColor()) && !capturesOnly){
                    moves.add(new Move(currentSquare, y, p));
                    break;
                } else if (!openSquare(y)) {
                    break;
                } else if (!capturesOnly) {
                    moves.add(new Move(currentSquare, y, p));
                }
            for (int x = currentSquare + 1; x < 65; x++)
                if (x % 8 == 1){
                    break;
                } else if (!openSquare(x) && !turn.equals(getPiecebySquare(x).getPieceColor()) && !capturesOnly) {
                    moves.add(new Move(currentSquare, x, p));
                    break;
                } else if (!openSquare(x)) {
                    break;
                } else if (!capturesOnly) {
                    moves.add(new Move(currentSquare, x, p));
                }
            for (int x = currentSquare - 1; x > 0; x--)
                if (x % 8 == 0){
                    break;
                } else if (!openSquare(x) && !turn.equals(getPiecebySquare(x).getPieceColor()) && !capturesOnly){
                    moves.add(new Move (currentSquare,x, p));
                    break;
                }else if (!openSquare(x)) {
                    break;
                } else if (!capturesOnly) {
                    moves.add(new Move(currentSquare, x, p));
                }
            for (int upRight = currentSquare+9; upRight < 64; upRight += 9) {
                if (upRight % 8 == 1){
                    break;
                } else if (!openSquare(upRight) && !getPiecebySquare(upRight).getPieceColor().equals(turn) && !capturesOnly) {
                    moves.add(new Move(currentSquare, upRight, p));
                    break;
                } else if (!openSquare(upRight)) {
                    break;
                } else if (!capturesOnly) {
                    moves.add(new Move(currentSquare, upRight,p));
                }
            }
            for (int upLeft = currentSquare+7; upLeft < 58 || upLeft % 8 == 0; upLeft += 7) {
                if (upLeft % 8 == 0){
                    break;
                } else if (!openSquare(upLeft) && !getPiecebySquare(upLeft).getPieceColor().equals(turn) && !capturesOnly) {
                    moves.add(new Move(currentSquare, upLeft,p));
                    break;
                } else if (!openSquare(upLeft)) {
                    break;
                } else if (!capturesOnly){
                    moves.add(new Move(currentSquare, upLeft,p));
                }
            }
            for (int downRight = currentSquare-7; downRight > 7 || downRight % 8 == 1; downRight -= 7){
                if (downRight % 8 == 1){
                    break;
                } else if (!openSquare(downRight) && !getPiecebySquare(downRight).getPieceColor().equals(turn) && !capturesOnly) {
                    moves.add(new Move(currentSquare, downRight,p));
                    break;
                } else if (!openSquare(downRight)) {
                    break;
                } else if (!capturesOnly)
                    moves.add(new Move(currentSquare, downRight,p));
            }
            for (int downLeft = currentSquare-9; downLeft > 0 || downLeft % 8 == 0; downLeft -= 9) {
                if (downLeft % 8 == 0){
                    break;
                } else if (!openSquare(downLeft) && !getPiecebySquare(downLeft).getPieceColor().equals(turn) && !capturesOnly) {
                    moves.add(new Move(currentSquare, downLeft,p));
                    break;
                } else if (!openSquare(downLeft)) {
                    break;
                } else if (!capturesOnly)
                    moves.add(new Move(currentSquare, downLeft,p));
            }
        } else if (piece.equals("rook")){
            for (int y = currentSquare+8; y < 65; y += 8)
                if (!openSquare(y) && !turn.equals(getPiecebySquare(y).getPieceColor()) && !capturesOnly) {
                    moves.add(new Move(currentSquare, y,p));
                    break;
                } else if (!openSquare(y)) {
                    break;
                } else if (!capturesOnly) {
                    moves.add(new Move(currentSquare, y,p));
                }
            for (int y = currentSquare-8; y > 0; y -= 8)
                if (!openSquare(y) && !turn.equals(getPiecebySquare(y).getPieceColor()) && !capturesOnly){
                    moves.add(new Move(currentSquare, y,p));
                    break;
                } else if (!openSquare(y)) {
                    break;
                } else if (!capturesOnly) {
                    moves.add(new Move(currentSquare, y,p));
                }
            for (int x = currentSquare + 1; x < 65; x++)
                if (x % 8 == 1){
                    break;
                } else if (!openSquare(x) && !turn.equals(getPiecebySquare(x).getPieceColor()) && !capturesOnly) {
                    moves.add(new Move(currentSquare, currentSquare + x,p));
                    break;
                } else if (!openSquare(x)) {
                    break;
                } else if (!capturesOnly) {
                    moves.add(new Move(currentSquare, x,p));
                }
            for (int x = currentSquare - 1; x > 0; x--)
                if (x % 8 == 0){
                    break;
                } else if (!openSquare(x) && !turn.equals(getPiecebySquare(x).getPieceColor()) && !capturesOnly){
                    moves.add(new Move (currentSquare,x,p));
                    break;
                }else if (!openSquare(x)) {
                    break;
                } else if (!capturesOnly) {
                    moves.add(new Move(currentSquare, x,p));
                }
        }
        return moves;
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
        private final int currentSquare;
        private final int targetSquare;
        private int evaluation;
        private final Piece piece;
        public Move(int current, int target, Piece p){
            currentSquare = current;
            targetSquare = target;
            piece = p;
        }
        public int getCurrentSquare(){
            return currentSquare;
        }
        public int getTargetSquare(){
            return targetSquare;
        }
        public void setEvaluation(int eval){evaluation = eval;}
        public int getEvaluation(){return evaluation;}
        public Piece getPiece() {
            return piece;
        }
    }
    public static class Square {
        private final int rank;
        private final int file;
        private final ImageView view;
        private final ConstraintLayout layout;
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
        private final String pieceType;
        private final String pieceColor;
        private boolean moved;
        private boolean movedTwo;
        private int movesSinceMovedTwo;
        private ImageView pic;
        public Piece(int f, int r, String c, String t){
            rank = r;
            file = f;
            pieceColor = c;
            pieceType = t;
            moved = true;
            movedTwo = false;
            movesSinceMovedTwo = -1;
        }
        public Piece(int f, int r, String c, String t, ImageView i){
            rank = r;
            file = f;
            pieceColor = c;
            pieceType = t;
            moved = false;
            movedTwo = false;
            pic = i;
            movesSinceMovedTwo = -1;
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
        public boolean getMovedTwo(){
            return movedTwo;
        }
        public void setMovedTwo(boolean twoMoved){
            movedTwo = twoMoved;
            this.setMovesSinceMovedTwo(0);
        }
        public void setMovesSinceMovedTwo (int moves){
            movesSinceMovedTwo = moves;
            if (movesSinceMovedTwo == 2){
                this.setMovesSinceMovedTwo(-1);
                this.setMovedTwo(false);
            }
        }
        public void promote(boolean white){
            if (white)
                pic.setImageResource(R.drawable.whitequeen);
            else
                pic.setImageResource(R.drawable.blackqueen);
        }

        public boolean getMoved() {
            return !moved;
        }

        public int getMovesSinceMovedTwo() {
            return movesSinceMovedTwo;
        }
    }

    //////Start Handling Button\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    public void returnHome() {
        goBackViaLoadingActivity();
    }

    public void onBackPressed() {
        goBackViaLoadingActivity();
    }

    //Loading animation goes up when returning back to Home Activity.
    private void goBackViaLoadingActivity() {
        Intent loadingIntent = new Intent(AIvAIChessActivity.this, LoadingActivity.class);
        loadingIntent.putExtra("Class Code", 0);
        startActivity(loadingIntent);
        finish();
    }
//////End Handling Button\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
}
