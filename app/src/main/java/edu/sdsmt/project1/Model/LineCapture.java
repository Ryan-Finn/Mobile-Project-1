package edu.sdsmt.project1.Model;

import android.graphics.Canvas;
import android.graphics.Paint;
import java.util.ArrayList;

public class LineCapture extends CaptureObject {
    private final float strokeSize = 25;

    public float distToLine(float startX, float startY, float cx, float cy) {
        float dx = x - startX;
        float dy = y - startY;
        float closestX, closestY, t;

        if ((dx == 0) && (dy == 0)) {
            dx = cx - startX;
            dy = cy - startY;
            return (float) Math.sqrt(dx * dx + dy * dy);
        }

        t = ((cx - startX) * dx + (cy - startY) * dy) / (dx * dx + dy * dy);

        if (t < 0) {
            closestX = startX;
            closestY = startY;
            dx = cx - closestX;
            dy = cy - closestY;
        } else if (t > 1) {
            closestX = x;
            closestY = y;
            dx = cx - closestX;
            dy = cy - closestY;
        } else {
            closestX = startX + t * dx;
            closestY = startY + t * dy;
            dx = cx - closestX;
            dy = cy - closestY;
        }

        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public ArrayList<Collectable> getContainedCollectables(float viewWidth, float viewHeight,
                                                           ArrayList<Collectable> list) {
        ArrayList<Collectable> contained = new ArrayList<>();

        float startX = viewWidth / 2;
        float startY = 0;

        for (Collectable obj : list) {
            float objX = obj.getX() * viewWidth;
            float objY = obj.getY() * viewHeight;
            float dist = this.distToLine(startX, startY, objX, objY);

            if (dist < strokeSize / 2 + obj.getRadius()) {
                contained.add(obj);
            }
        }
        return contained;
    }

    @Override
    public void draw(Canvas canvas, Paint p) {
        // Draw a line on the screen
        p.setStrokeWidth(strokeSize);
        canvas.drawLine((float) canvas.getWidth() / 2, 0, x, y,p);
    }
}

