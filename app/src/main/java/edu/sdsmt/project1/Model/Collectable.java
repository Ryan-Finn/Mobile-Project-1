package edu.sdsmt.project1.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

public class Collectable {
    private final Bitmap bitmap;
    private float x = -1;
    private float y = -1;
    private final int id;
    private final int width;
    private final int height;
    private boolean doShuffle = true;

    public Collectable(Context context, int id) {
        this.id = id;
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), id);
        this.width = bitmap.getWidth();
        this.height = bitmap.getHeight();
    }

    public void draw(Canvas canvas, int canvasX, int canvasY, float scaleFactor) {
        int canvas_width = canvas.getWidth();
        int canvas_height = canvas.getHeight();

        canvas.save();

        canvas.translate(canvasX + x * canvas_width, canvasY + y * canvas_height);
        canvas.scale(scaleFactor, scaleFactor);
        canvas.translate(-width / 2f, -height / 2f);
        canvas.drawBitmap(bitmap, 0, 0, null);

        canvas.restore();
    }

    public boolean hit(float testX, float testY, int size, float scaleFactor) {
        int pX = (int)((testX - x) * size / scaleFactor) + bitmap.getWidth() / 2;
        int pY = (int)((testY - y) * size / scaleFactor) + bitmap.getHeight() / 2;

        if(pX < 0 || pX >= bitmap.getWidth() || pY < 0 || pY >= bitmap.getHeight()) {
            return false;
        }

        return (bitmap.getPixel(pX, pY) & 0xff000000) != 0;
    }

    public void shuffle(Canvas canvas, int canvasX, int canvasY, float scale, Random rand) {
        if (!doShuffle) {
            return;
        }

        int canvas_width = canvas.getWidth();
        int canvas_height = canvas.getHeight();

        x = rand.nextFloat();
        y = rand.nextFloat();

        float relX = canvasX + x * canvas_width;
        float relY = canvasY + y * canvas_height;

        double minX = canvasX + width * scale / 2.0;
        double maxX = canvasX + canvas_width - width * scale / 2.0;
        double minY = canvasY + height * scale / 2.0;
        double maxY = canvasY + canvas_height - height * scale / 2.0;

        while (maxX <= relX || relX <= minX) {
            x = rand.nextFloat();
            relX = canvasX + x * canvas_width;
        }
        while (maxY <= relY || relY <= minY) {
            y = rand.nextFloat();
            relY = canvasY + y * canvas_height;
        }
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

    public void setShuffle(boolean bool) { this.doShuffle = bool; }

    public boolean getShuffle() { return this.doShuffle; }

    public float getRadius() {return (float) this.width / 11;}

    public void debugDraw(boolean condition) {
        int c = condition ? Color.GREEN : Color.BLUE;

    }
}
