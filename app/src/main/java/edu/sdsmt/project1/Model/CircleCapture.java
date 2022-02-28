package edu.sdsmt.project1.Model;

import android.graphics.Canvas;
import android.graphics.Paint;
import java.util.ArrayList;

public class CircleCapture extends CaptureObject {
    private final float scaleFactor = 0.06f;

    @Override
    public ArrayList<Collectable> getContainedCollectables(float viewWidth, float viewHeight
                                                                ,ArrayList<Collectable> list) {
        ArrayList<Collectable> contained = new ArrayList<>();
        float objX, objY, objRadius, dist;
        float radius = scaleFactor * viewHeight;
        boolean intersect;

        for (Collectable obj : list) {
            objX = obj.getX() * viewWidth;
            objY = obj.getY() * viewHeight;
            objRadius = obj.getRadius();

            // Compute distance between the center of capture circle and obj circle
            dist = (float) Math.sqrt(Math.pow(x - objX, 2) + Math.pow(y - objY, 2));

            // Check if the two circles intersect
            intersect = (objRadius - radius < dist && objRadius + radius > dist);
            if (intersect) {
                contained.add(obj);
            }
        }
        return contained;
    }

    @Override
    public void draw(Canvas canvas, Paint p) {
        // Draw a circle on the screen
        canvas.drawCircle(x, y, scaleFactor*canvas.getHeight(), p);
    }
}

