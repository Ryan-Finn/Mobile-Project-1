package edu.sdsmt.project1.Model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import java.util.ArrayList;

public class CircleCapture extends CaptureObject {
    @Override
    public ArrayList<Collectable> getContainedCollectables(View view, ArrayList<Collectable> list) {
        // To do: Add capture rules and return the list of collected objects within list
        return null;
    }

    @Override
    public void draw(Canvas canvas, Paint p) {
        // Draw a circle on the screen
        canvas.drawCircle(x, y, 100, p);
    }
}

