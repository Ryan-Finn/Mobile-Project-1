package edu.sdsmt.project1.Model;

import android.graphics.Canvas;
import android.graphics.Paint;
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
    public abstract ArrayList<Collectable> getContainedCollectables(View view,
                                                                    ArrayList<Collectable> list);

    public abstract void draw(Canvas canvas, Paint p);

    public float getX() { return x; }

    public float getY() { return y; }

    public void setX(float x) { this.x = x; }

    public void setY(float y) { this.y = y; }
}
