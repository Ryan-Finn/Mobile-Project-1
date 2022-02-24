package edu.sdsmt.project1.Model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import java.util.ArrayList;

public class SquareCapture extends CaptureObject {
    @Override
    public ArrayList<Collectable> getContainedCollectables(View view, ArrayList<Collectable> list) {
        // To do: Add capture rules and return the list of collected objects within list
        return null;
    }

    @Override
    public void draw(Canvas canvas, Paint p) {
        // Draw a square on the screen
        canvas.drawRect(x-100,y-100,x+100,y+100,p);
    }
}

