package edu.sdsmt.group4.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;
import edu.sdsmt.group4.Model.CaptureObject;
import edu.sdsmt.group4.Model.CircleCapture;
import edu.sdsmt.group4.Model.Collectable;
import edu.sdsmt.group4.Model.GameBoard;
import edu.sdsmt.group4.Model.LineCapture;
import edu.sdsmt.group4.Model.SquareCapture;

public class GameBoardView extends View {
    private static final String CAPTURE_TYPE = "gameBoard.CaptureType" ;
    private static final String CAPTURE_COORDINATES = "gameBoard.CaptureCoordinates";
    private static final String SCREEN_SIZE = "gameBoard.PreviousScreenSize";
    private static final String PREVIOUS_ANGLE = "gameBoard.PreviousAngle";
    private static final String ROUNDS = "gameBoard.rounds";
    private GameBoard board;
    private static final Random random = new Random();
    public final int CIRCLE = 0;
    public final int RECTANGLE = 1;
    public final int LINE = 2;
    private final Touch touch1 = new Touch();
    private final Touch touch2 = new Touch();
    final float SCALE_IN_VIEW = 1.0f;
    private Paint fillPaint;
    private Paint outlinePaint;
    private Paint capturePaint;
    public int captureType = -1;
    private CaptureObject capture;

    public boolean isCaptureEnabled() {
        return captureType != -1;
    }

    private static class Touch {
        public int id = -1;
        public float x = 0;
        public float y = 0;
        public float lastX = 0;
        public float lastY = 0;
        public float dX = 0;
        public float dY = 0;

        public void updatePos(float newX, float newY) {
            lastX = x;
            lastY = y;
            x = newX;
            y = newY;
        }

        public void computeDeltas() {
            dX = x - lastX;
            dY = y - lastY;
        }

        public void clear() {
            id = -1;
            x = 0;
            y = 0;
            lastX = 0;
            lastY = 0;
            dX = 0;
            dY = 0;
        }

        public void move(Touch touch) {
            id = touch.id;
            x = touch.x;
            y = touch.y;
            lastX = touch.lastX;
            lastY = touch.lastY;
            dX = touch.dX;
            dY = touch.dY;
            touch.clear();
        }
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

    private void init() {
        board = new GameBoard(getContext());

        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(0xffcccccc);

        outlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outlinePaint.setStyle(Paint.Style.STROKE);
        outlinePaint.setColor(Color.BLUE);
        outlinePaint.setStrokeWidth(5.0f);

        capturePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        capturePaint.setColor(Color.RED);
        capturePaint.setAlpha(100);
    }

    public void setCapture(int cap) {
        captureType = cap;

        switch(cap) {
            case CIRCLE:
                capture = new CircleCapture();
                break;

            case RECTANGLE:
                capture = new SquareCapture();
                break;

            case LINE:
                capture = new LineCapture();
                break;

            default:
                capture = null;
        }

        if (capture != null) {
            capture.setX((float) getWidth()/2);
            capture.setY((float) getHeight()/2);
        }

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();

        int canvasX = (int) (width * (1.0f - SCALE_IN_VIEW) / 2);
        int canvasY = (int) (height * (1.0f - SCALE_IN_VIEW) / 2);

        width = (int)(width * SCALE_IN_VIEW);
        height = (int)(height * SCALE_IN_VIEW);

        canvas.save();

        canvas.drawRect(canvasX, canvasY, width, height, fillPaint);
        canvas.drawRect(canvasX, canvasY, width, height, outlinePaint);

        for(Collectable collectable : board.getCollectables()) {
            collectable.shuffle(canvas, canvasX, canvasY, random);
            collectable.setShuffle(false);
            collectable.draw(canvas, canvasX, canvasY);
        }

        if (captureType != -1) {
            capture.draw(canvas, capturePaint, random);
            // capture.debug(canvas, board.getCollectables()); // Show hit boxes with realtime collision updates
        }

        canvas.restore();
    }

    @Override
    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {
        int id = event.getPointerId(event.getActionIndex());

        if (captureType != -1) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    touch1.id = id;
                    touch2.id = -1;
                    getPositions(event);
                    return true;

                case MotionEvent.ACTION_POINTER_DOWN:
                    if (touch1.id >= 0 && touch2.id < 0) {
                        touch2.id = id;
                        getPositions(event);
                        return true;
                    }
                    break;

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_POINTER_UP:
                    if (id == touch2.id) {
                        touch2.clear();
                    } else if (id == touch1.id) {
                        touch2.move(touch1);
                    }
                    invalidate();
                    return true;

                case MotionEvent.ACTION_MOVE:
                    getPositions(event);
                    move();
                    return true;
            }
        }
        return false;
    }

    public void captureClicked() {
        board.capture(capture);
        captureType = -1;
        touch1.clear();
        touch2.clear();
        invalidate();
    }
    private void getPositions(MotionEvent event) {
        for (int i = 0; i < event.getPointerCount(); i++) {
            int id = event.getPointerId(i);

            if (id == touch1.id) {
                touch1.updatePos(event.getX(i), event.getY(i));
            } else if (id == touch2.id) {
                touch2.updatePos(event.getX(i), event.getY(i));
            }
        }

        invalidate();
    }

    private void move() {
        if (touch1.id < 0) { return; }

        touch1.computeDeltas();
        capture.setX(capture.getX() + touch1.dX);
        capture.setY(capture.getY() + touch1.dY);

        if (touch2.id >= 0) {
            if (captureType == LINE) {
                float angle1 = angle(touch1.lastX, touch1.lastY, touch2.lastX, touch2.lastY);
                float angle2 = angle(touch1.x, touch1.y, touch2.x, touch2.y);
                float da = angle2 - angle1;
                rotate(da, touch1.x, touch1.y);
            }
            else {
                float delLastX = touch2.lastX - touch1.lastX;
                float delLastY = touch2.lastY - touch1.lastY;
                float disLast = delLastX * delLastX + delLastY * delLastY;

                float delX = touch2.x - touch1.x;
                float delY = touch2.y - touch1.y;
                float dis = delX * delX + delY * delY;

                if (captureType == RECTANGLE) {
                    float ds = (float) Math.sqrt(dis / disLast);
                    scale(ds, touch1.x, touch1.y);
                }
            }
        }
    }

    public void rotate(float dAngle, float x1, float y1) {
        capture.setAngle(capture.getAngle() + (float)Math.toDegrees(dAngle));
        float ca = (float)Math.cos(dAngle);
        float sa = (float)Math.sin(dAngle);
        capture.setX((capture.getX() - x1) * ca - (capture.getY() - y1) * sa + x1);
        capture.setY((capture.getX() - x1) * sa + (capture.getY() - y1) * ca + y1);
    }

    private float angle(float x1, float y1, float x2, float y2) {
        float dx = x2 - x1;
        float dy = y2 - y1;
        return (float)Math.atan2(dy, dx);
    }

    public void scale(float dScale, float x1, float y1) {
        if (capture.getScale() * dScale >= 0.5f && capture.getScale() * dScale <= 1.0f) {
            capture.setScale(capture.getScale() * dScale);
            capture.setX(x1 - (x1 - capture.getX()) * dScale);
            capture.setY(y1 - (y1 - capture.getY()) * dScale);
        }
    }

    public void saveInstanceState(Bundle bundle) {
        Log.i("inside save", String.valueOf(captureType));
        float xPos, yPos;
        float[] screenSize = new float[]{getWidth(), getHeight()};

        // Save the capture type & data
        bundle.putInt(CAPTURE_TYPE, captureType);
        bundle.putString(ROUNDS, board.getRounds());
        if (capture != null) {
            xPos = capture.getX() / screenSize[0];
            yPos = capture.getY() / screenSize[1];

            // Put the capture objects back on the screen if they go off of it
            if (xPos > 1) {
                xPos = .95f;
            } else if (xPos < 0) {
                xPos = 0f;
            }

            if (yPos > 1) {
                yPos = .95f;
            } else if (yPos < 0) {
                yPos = 0f;
            }

            // Save the angle if the capture is a line
            if (captureType == LINE) {
                bundle.putFloat(PREVIOUS_ANGLE, capture.getAngle());
            }
            bundle.putFloatArray(CAPTURE_COORDINATES, new float[]{xPos, yPos});
            bundle.putFloatArray(SCREEN_SIZE, screenSize);
        }
        board.saveInstanceState(bundle);
        }

    public void loadInstanceState(Bundle bundle) {
        board.loadInstanceState(bundle);
        board.setRounds(Integer.parseInt(bundle.getString(ROUNDS)));
        // Set the capture type and saved coordinates
        setCapture(bundle.getInt(CAPTURE_TYPE));

        if(captureType != -1) {
            float[] coordinates = bundle.getFloatArray(CAPTURE_COORDINATES);
            float[] screenSize = bundle.getFloatArray(SCREEN_SIZE);
            coordinates[0] = coordinates[0] * screenSize[1];
            coordinates[1] = coordinates[1] * screenSize[0];
            if (capture != null) {
                capture.setX(coordinates[0]);
                capture.setY(coordinates[1]);
                if (bundle.getInt(CAPTURE_TYPE) == LINE) {
                    capture.setAngle(bundle.getFloat(PREVIOUS_ANGLE));
                }
            }
        }
    }

    public void addPlayer(String name, int id) { board.addPlayer(name, id); }

    public void setRounds(int r) { board.setRounds(r); }

    public String getRounds(){ return board.getRounds(); }

    public String getPlayer1Score() { return board.getPlayer1Score(); }

    public String getPlayer2Score() { return board.getPlayer2Score(); }

    public int getCurrentPlayerId() { return board.getCurrentPlayerId(); }

    public boolean isEndGame() { return board.isEndGame(); }

    public void setDefaultPlayer() { board.setDefaultPlayer(); }

    public String getPlayer1Name() { return board.getPlayer1Name(); }

    public String getPlayer2Name() { return board.getPlayer2Name(); }
}

