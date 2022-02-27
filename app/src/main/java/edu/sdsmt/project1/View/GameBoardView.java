package edu.sdsmt.project1.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import edu.sdsmt.project1.Model.GameBoard;

public class GameBoardView extends View {
    private GameBoard board;
    private int capture;

    @Override
    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {
        return board.onTouchEvent(this, event);
    }

    public GameBoardView(Context context) {
        super(context);
        init();
    }

    public GameBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GameBoardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() { board = new GameBoard(this, getContext()); }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        board.draw(canvas);
        //capture.draw(canvas);
    }

    public void saveInstanceState(Bundle bundle) {
        board.saveInstanceState(bundle);
    }

    public void loadInstanceState(Bundle bundle) {
        board.loadInstanceState(bundle);
    }

    public GameBoard getBoard() {
        return board;
    }

    public void setCaptureOption(int optionNumber) {
        board.setCaptureOption(optionNumber);
    }

    public void addPlayer(String name, int id) {
        board.addPlayer(name, id);
    }

    public void setRounds(int r) {
        board.setRounds(r);
    }

    public String getRounds(){
        return board.getRounds();
    }

    public String getPlayer2Score() {
        return board.getPlayer2Score();
    }

    public String getPlayer1Score() {
        return board.getPlayer1Score();
    }

    public int getCurrentPlayerId() {
        return board.getCurrentPlayerId();
    }

    public boolean isEndRound(){return board.isEndGame();}

    public void setDefaultPlayer() {
        board.setDefaultPlayer();
    }

    public String getPlayer1Name() {
        return board.getPlayer1Name();
    }
    public String getPlayer2Name() {
        return board.getPlayer2Name();
    }
}

