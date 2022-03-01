package edu.sdsmt.project1.Model;

import android.graphics.Canvas;
import android.graphics.Paint;
import java.util.ArrayList;

public class CircleCapture extends CaptureObject {
    private float radius;

    public CircleCapture() {
        setScale(.2f);
        radius = 0;
    }

    @Override
    public ArrayList<Collectable> getContainedCollectables(ArrayList<Collectable> list) {
        ArrayList<Collectable> contained = new ArrayList<>();
        float dist;

        for (Collectable obj : list) {
            // Compute distance between the center of capture circle and obj circle
            dist = (float) Math.sqrt(Math.pow(x - obj.getX(), 2) + Math.pow(y - obj.getY(), 2));

            // Check if the two circles intersect
            if (obj.getRadius() + radius > dist) {
                contained.add(obj);
            }
        }
        return contained;
    }

    @Override
    public void draw(Canvas canvas, Paint p) {
        // Draw a circle on the screen
        radius = scale * canvas.getWidth();
        canvas.drawCircle(x, y, radius, p);
    }
}

