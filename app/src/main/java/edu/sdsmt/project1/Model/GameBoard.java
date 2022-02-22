package edu.sdsmt.project1.Model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.Random;

public class GameBoard {
    private final View view;
    private static final Random random = new Random();
    public final ArrayList<Collectable> collectables = new ArrayList<>();
    private final static String LOCATIONS = "GameBoard.locations";
    private final static String IDS = "GameBoard.ids";
    final static float SCALE_IN_VIEW = 1.0f;
    private final Paint fillPaint;
    private final Paint outlinePaint;

    public GameBoard(View v, Context context) {
        view = v;

        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(0xffcccccc);

        outlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outlinePaint.setStyle(Paint.Style.STROKE);
        outlinePaint.setColor(Color.BLUE);
        outlinePaint.setStrokeWidth(5.0f);

        for (int i = 0; i < 20; i++) {
            Collectable collectable = new Collectable(context, edu.sdsmt.project1.R.drawable.collectable);
            collectables.add(collectable);
        }
    }

    public void draw(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        int canvasX = (int) (width * (1.0f - SCALE_IN_VIEW) / 2);
        int canvasY = (int) (height * (1.0f - SCALE_IN_VIEW) / 2);

        width = (int)(width * SCALE_IN_VIEW);
        height = (int)(height * SCALE_IN_VIEW);

        canvas.drawRect(canvasX, canvasY, width, height, fillPaint);
        canvas.drawRect(canvasX, canvasY, width, height, outlinePaint);

        for(Collectable collectable : collectables) {
            collectable.shuffle(canvas, canvasX, canvasY, 0.2f, random);
            collectable.draw(canvas, canvasX, canvasY, 0.2f);
        }
    }

    private boolean onTouched(float x, float y) {
        return true;
    }

    private boolean onReleased() {
        return true;
    }

    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    public void saveInstanceState(Bundle bundle) {
        float [] locations = new float[collectables.size() * 2];
        int [] ids = new int[collectables.size()];

        for (int i = 0; i < collectables.size(); i++) {
            Collectable collectable = collectables.get(i);
            locations[i * 2] = collectable.getX();
            locations[i * 2 + 1] = collectable.getY();
            ids[i] = collectable.getId();
        }

        bundle.putFloatArray(LOCATIONS, locations);
        bundle.putIntArray(IDS,  ids);
    }

    public void loadInstanceState(Bundle bundle) {
        float [] locations = bundle.getFloatArray(LOCATIONS);
        int [] ids = bundle.getIntArray(IDS);

        for (int i = 0; i < ids.length - 1; i++) {
            for(int j = i + 1; j < collectables.size(); j++) {
                if(ids[i] == collectables.get(j).getId()) {
                    Collectable collectable = collectables.get(i);
                    collectables.set(i, collectables.get(j));
                    collectables.set(j, collectable);
                }
            }
        }

        for (int i = 0; i < collectables.size(); i++) {
            Collectable collectable = collectables.get(i);
            collectable.setX(locations[i*2]);
            collectable.setY(locations[i*2+1]);
            collectable.setShuffle(false);
        }
    }
}
