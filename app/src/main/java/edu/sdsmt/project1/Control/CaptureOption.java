package edu.sdsmt.project1.Control;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.Random;

public class CaptureOption {

    private final Bitmap bitmap;
    private float x = -1;
    private float y = -1;
    private final int id;
    private final int width;
    private final int height;
    private boolean doShuffle = true;

    public CaptureOption(Context context, int id) {
        this.id = id;
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), id);
        this.width = bitmap.getWidth();
        this.height = bitmap.getHeight();
    }

    public void draw(Canvas canvas) {

    }


    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

}
