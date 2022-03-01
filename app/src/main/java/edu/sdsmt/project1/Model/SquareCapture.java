package edu.sdsmt.project1.Model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;

public class SquareCapture extends CaptureObject {
    public SquareCapture() {
        this.scale = .2f;
    }

    private boolean intersects(Collectable obj) {
        float objX = obj.getX();
        float objY = obj.getY();
        float objRadius = obj.getRadius();
        float dx = objX - Math.max(x - width/2, Math.min(objX, x + width/2));
        float dy = objY - Math.max(y - height/2, Math.min(objY, y + height/2));

        return (dx * dx + dy * dy) < (objRadius * objRadius);
    }

    @Override
    public ArrayList<Collectable> getContainedCollectables(ArrayList<Collectable> list) {
        ArrayList<Collectable> contained = new ArrayList<>();

        for (Collectable obj : list) {
            if (intersects(obj)) {
                contained.add(obj);
            }
        }

        return contained;
    }

    @Override
    public void draw(Canvas canvas, Paint p) {
        this.width = scale * canvas.getWidth();
        this.height = scale * canvas.getWidth();

        // Draw a square on the screen
        canvas.drawRect(x - width/2,y - height/2,x + width/2,y + height/2, p);
    }
}

