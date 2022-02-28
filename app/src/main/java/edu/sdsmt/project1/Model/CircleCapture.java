package edu.sdsmt.project1.Model;

import android.graphics.Canvas;
import android.graphics.Paint;
import java.util.ArrayList;

public class CircleCapture extends CaptureObject {
    @Override
    public ArrayList<Collectable> getContainedCollectables(ArrayList<Collectable> list) {
        ArrayList<Collectable> contained = new ArrayList<>();
        float dist;

        for (Collectable obj : list) {
            // Compute distance between the center of capture circle and obj circle
            dist = (float) Math.sqrt(Math.pow(x - obj.getX(), 2) + Math.pow(y - obj.getY(), 2));

            // Check if the two circles intersect
            if (obj.getRadius() + height > dist) {
                contained.add(obj);
            }
        }
        return contained;
    }

    @Override
    public void draw(Canvas canvas, Paint p) {
        // Draw a circle on the screen
        width = 0.1f * canvas.getHeight();
        height = 0.1f * canvas.getHeight();
        canvas.drawCircle(x, y, height, p);
    }
}

