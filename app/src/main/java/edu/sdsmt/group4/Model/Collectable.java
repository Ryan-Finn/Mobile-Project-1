package edu.sdsmt.group4.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import java.util.Random;

public class Collectable {
    private final Bitmap bitmap;
    private float relX = -1;
    private float relY = -1;
    private float x = -1;
    private float y = -1;
    private final int id;
    private final int width;
    private final int height;
    private final float scale;
    private boolean doShuffle = true;

    public Collectable(Context context, int id, float scale) {
        this.id = id;
        this.scale = scale;
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), edu.sdsmt.group4.R.drawable.collectable);
        this.width = bitmap.getWidth();
        this.height = bitmap.getHeight();
    }

    public void draw(Canvas canvas, int canvasX, int canvasY) {
        int canvas_width = canvas.getWidth();
        int canvas_height = canvas.getHeight();

        x = canvasX + relX * canvas_width;
        y = canvasY + relY * canvas_height;

        canvas.save();

        canvas.translate(x, y);
        canvas.scale(scale, scale);
        canvas.translate(-width / 2f, -height / 2f);
        canvas.drawBitmap(bitmap, 0, 0, null);

        canvas.restore();
    }

    public void shuffle(Canvas canvas, int canvasX, int canvasY, Random rand) {
        if (!doShuffle) {
            return;
        }

        int canvas_width = canvas.getWidth();
        int canvas_height = canvas.getHeight();

        relX = rand.nextFloat();
        relY = rand.nextFloat();

        x = canvasX + relX * canvas_width;
        y = canvasY + relY * canvas_height;

        double minX = canvasX + width * scale / 2.0;
        double maxX = canvasX + canvas_width - width * scale / 2.0;
        double minY = canvasY + height * scale / 2.0;
        double maxY = canvasY + canvas_height - height * scale / 2.0;

        while (maxX <= x || x <= minX) {
            relX = rand.nextFloat();
            x = canvasX + relX * canvas_width;
        }
        while (maxY <= y || y <= minY) {
            relY = rand.nextFloat();
            y = canvasY + relY * canvas_height;
        }
    }

    public float getRelX() {
        return relX;
    }

    public float getRelY() {
        return relY;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public void setRelX(float x) {
        this.relX = x;
    }

    public void setRelY(float y) {
        this.relY = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setShuffle(boolean bool) { this.doShuffle = bool; }

    public float getRadius() {return (float) this.width * scale / 2;}
}
