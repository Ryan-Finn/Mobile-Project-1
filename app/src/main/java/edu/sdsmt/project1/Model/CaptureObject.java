package edu.sdsmt.project1.Model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

public class CaptureObject {
    protected float x = 0;
    protected float y = 0;
    protected float width;
    protected float height;

    /**
     * Gets collectables that are contained within a capture object from the GameBoardActivity's
     * list of displayed collectables. This is called when the player lifts all of their fingers,
     * thus ending their turn.
     *
     * @param list - List of collectables in the main class
     *
     * @return Collectables contained within the capture object.
     */
    public ArrayList<Collectable> getContainedCollectables(ArrayList<Collectable> list) {
        return new ArrayList<>();
    }

    public void draw(Canvas canvas, Paint p) {}

    public float getX() { return x; }

    public float getY() { return y; }

    public float getScale() { return 0; }

    public float getAngle() { return 0; }

    public void setX(float x) { this.x = x; }

    public void setY(float y) { this.y = y; }

    public void setScale(float scale) {}

    public void setAngle(float angle) {}

    public void debug(Canvas canvas, ArrayList<Collectable> list) {
        Paint p = new Paint();
        ArrayList<Collectable> collected = getContainedCollectables(list);

        for (Collectable obj : list) {
            if (!collected.contains(obj))
                p.setColor(Color.BLUE);
            else
                p.setColor(Color.GREEN);
            p.setAlpha(40);
            canvas.drawCircle(obj.getX(), obj.getY(), obj.getRadius(), p);
        }
    }

}

