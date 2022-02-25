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

import edu.sdsmt.project1.Control.CaptureSelectionActivity;
import edu.sdsmt.project1.R;
import edu.sdsmt.project1.View.GameBoardView;
import edu.sdsmt.project1.WelcomeActivity;

public class GameBoardActivity extends AppCompatActivity {
    private GameBoardView view;
    public static final String CAPTURED_INT = "edu.sdsmt.project1.RETURN_MESSAGE";
    public static final String PARAMETERS = "PARAMS";
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

        //get player names and no of rounds from prev
        Intent intent = getIntent();
        String player1Name = intent.getStringExtra(WelcomeActivity.PLAYER1NAME_MESSAGE);
        String player2Name = intent.getStringExtra(WelcomeActivity.PLAYER2NAME_MESSAGE);
        String rounds = intent.getStringExtra(WelcomeActivity.ROUNDS_MESSAGE);

        //testing if it passing info works
        Log.i("P1", player1Name);
        Log.i("P2", player2Name);
        Log.i("r", rounds);


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
                view.setCaptureOption(captureType);
            }
        });
    }


    public void onCaptureOptionsClick(View view) {
        Intent switchActivityIntent = new Intent(this, CaptureSelectionActivity.class);
        activityLauncher.launch(switchActivityIntent);
    }
}
