package edu.sdsmt.project1.Model;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

public class SquareCapture extends CaptureObject {
    private final float size = 200;
    private float scale = 1f;

    private boolean collides(Collectable obj, float viewWidth, float viewHeight) {
        float objX = obj.getX() * viewWidth;
        float objY = obj.getY() * viewHeight;
        float objRadius = obj.getRadius();
        float xDist = Math.abs(objX - x);
        float yDist = Math.abs(objY - y);
        float dx, dy;

        // Does not collide, end early
        if (xDist > (size/2 + objRadius) || yDist > (size/2 + objRadius)) {return false;}

        // They collide at a side of the rect, not corner
        if (xDist <= (size/2 + objRadius) || yDist <= (size/2 + objRadius)) {return true;}

        // Corner check if they collide
        dx = xDist - size/2;
        dy = yDist - size/2;

        return ( (dx*dx) + (dy*dy) <= (objRadius*objRadius) );
    }

    @Override
    public ArrayList<Collectable> getContainedCollectables(ArrayList<Collectable> list) {
        ArrayList<Collectable> contained = new ArrayList<>();

        for (Collectable obj : list) {
            if (collides(obj, width, height)) {
                contained.add(obj);
            }
        }

        return contained;
    }

    @Override
    public void draw(Canvas canvas, Paint p) {
        // Draw a square on the screen
        canvas.drawRect(x - size/2,y - size/2,x + size/2,y + size/2, p);
    }

    @Override
    public float getScale() { return scale; }

    @Override
    public void setScale(float scale) { this.scale = scale; }
}

