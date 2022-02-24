package edu.sdsmt.project1.Control;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import edu.sdsmt.project1.Control.GameBoardActivity;
import edu.sdsmt.project1.R;

public class CaptureSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_selection);
    }

    public void onCircleClick(View view) {

         clicked(0);
    }

    public void onRectangleClick(View view) {
        clicked(1);
    }

    public void onLineClick(View view) {
        clicked(2);
    }

    public void clicked(int captureType)
    {
        Intent resultIntent = new Intent();


        //grab the clicked button's text, and tag it for the caller activity to later find
        resultIntent.putExtra(GameBoardActivity.CAPTURED_INT, captureType);

        //say everything went OK
        setResult(Activity.RESULT_OK, resultIntent);

        //stop this activity and return
        finish();


    }

}
