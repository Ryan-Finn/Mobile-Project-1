package edu.sdsmt.project1.Model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import java.util.ArrayList;

public class SquareCapture extends CaptureObject {
    private final float width = 200;
    private final float height = 200;

    @Override
    public ArrayList<Collectable> getContainedCollectables(View view, ArrayList<Collectable> list) {
        // To do: Add capture rules and return the list of collected objects within list
        return null;
    }

    @Override
    public void draw(Canvas canvas, Paint p) {
        // Draw a square on the screen
        canvas.drawRect(x - width/2,y - height/2,x + width/2,y + height/2, p);
    }
}

