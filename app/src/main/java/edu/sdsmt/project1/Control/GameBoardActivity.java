package edu.sdsmt.project1.Control;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import edu.sdsmt.project1.CaptureSelectionActivity;
import edu.sdsmt.project1.R;
import edu.sdsmt.project1.View.GameBoardView;

public class GameBoardActivity extends AppCompatActivity {
    private GameBoardView view;
    public static final String CAPTURED_INT = "edu.sdsmt.project1.RETURN_MESSAGE";
    private ActivityResultLauncher<Intent> activityLauncher;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        view.saveInstanceState(bundle);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        view.loadInstanceState(bundle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);
        view = this.findViewById(R.id.gameBoardView);

        //any target
        ActivityResultContracts.StartActivityForResult contract =
                new ActivityResultContracts.StartActivityForResult();
        activityLauncher = registerForActivityResult(contract, (result) ->
        {
            int resultCode = result.getResultCode();
            if (resultCode == Activity.RESULT_OK) {
                Intent data = result.getData();
                assert data != null;
                int captureType = data.getIntExtra(CAPTURED_INT, 0);
                view.setCaptureType(captureType);
               testReturnedCapture(captureType);
            }
        });
    }

    /*For testing that captureSelectionActivity returns correct option */
    public void testReturnedCapture(int type)
    {
        String option = "";
        switch (type) {
            case 0:
                option = "Circle";
                break;
            case 1:
                option = "Rect";
                break;
            case 2:
                option = "Line";
                break;
        }

        Log.i("Returned Option", option);
    }

    public void onCaptureOptionsClick(View view) {
        Intent switchActivityIntent = new Intent(this, CaptureSelectionActivity.class);
        activityLauncher.launch(switchActivityIntent);
    }
}