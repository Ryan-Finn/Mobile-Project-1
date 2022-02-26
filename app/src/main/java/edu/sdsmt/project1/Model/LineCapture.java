package edu.sdsmt.project1.Model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import java.lang.Integer;

import java.sql.Array;
import java.util.ArrayList;

public class LineCapture extends CaptureObject {
    private final float strokeSize = 25;

    @Override
    public ArrayList<Collectable> getContainedCollectables(View view, ArrayList<Collectable> list) {
        ArrayList<Collectable> contained = new ArrayList<>();

        float startX = (float) view.getWidth() / 2;
        float startY = 0;
        float epsilon, distance;

        for (Collectable obj : list) {
            // Line equation
            float objX = obj.getX() * view.getWidth();
            float objY = obj.getY() * view.getHeight();
            epsilon = strokeSize;

            // Dist from collectable center to the line
            distance =
                    Math.abs((x - startX) * (startY - objY) - (startX - objX) * (y - startY)) /
                            (float) Math.sqrt(Math.pow(x - startX, 2) + Math.pow(y - startY, 2));

            if (distance < epsilon) {
                // Object is within the bounds of the line and not just in the direction
                if (objX > Math.min(x, startX) && objX < Math.max(x, startX)) {
                    contained.add(obj);
                }
            }
        }
        return contained;
    }

    @Override
    public void draw(Canvas canvas, Paint p) {
        // Draw a line on the screen
        p.setStrokeWidth(strokeSize);
        canvas.drawLine((float) canvas.getWidth() / 2,0,x,y,p);
    }
}

