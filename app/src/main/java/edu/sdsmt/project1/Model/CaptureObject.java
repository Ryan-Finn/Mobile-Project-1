package edu.sdsmt.project1.Model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public abstract class CaptureObject {
    protected float x = 0;
    protected float y = 0;

    /**
     * Gets collectables that are contained within a capture object from the GameBoardActivity's
     * list of displayed collectables. This is called when the player lifts all of their fingers,
     * thus ending their turn.
     *
     * @param list - List of collectables in the main class
     *
     * @return Collectables contained within the capture object.
     */
    public abstract ArrayList<Collectable> getContainedCollectables(float viewWidth, float viewHeight,
                                                                    ArrayList<Collectable> list);

    public abstract void draw(Canvas canvas, Paint p);

    public float getX() { return x; }

    public float getY() { return y; }

    public void setX(float x) { this.x = x; }

    public void setY(float y) { this.y = y; }

    public void debug(Canvas canvas, ArrayList<Collectable> list) {
        float x, y;

        Paint p = new Paint();

        ArrayList<Collectable> collected = getContainedCollectables(canvas.getWidth(), canvas.getHeight(), list);

        for (Collectable obj : list) {
            x = obj.getX() * canvas.getWidth();
            y = obj.getY() * canvas.getHeight();
            if (!collected.contains(obj))
                p.setColor(Color.BLUE);
            else
                p.setColor(Color.GREEN);
            p.setAlpha(40);
            canvas.drawCircle(x, y, obj.getRadius(), p);
        }
    }

}

