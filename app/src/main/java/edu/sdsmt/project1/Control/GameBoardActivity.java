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
import android.widget.TextView;

import edu.sdsmt.project1.Control.CaptureSelectionActivity;
import edu.sdsmt.project1.Model.Player;
import edu.sdsmt.project1.R;
import edu.sdsmt.project1.View.GameBoardView;
import edu.sdsmt.project1.WelcomeActivity;

public class GameBoardActivity extends AppCompatActivity {
    private GameBoardView view;
    private Player player1, player2;
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
        String name1 = intent.getStringExtra(WelcomeActivity.PLAYER1NAME_MESSAGE);
        String name2 = intent.getStringExtra(WelcomeActivity.PLAYER2NAME_MESSAGE);
        String r = intent.getStringExtra(WelcomeActivity.ROUNDS_MESSAGE);

        if (name1.isEmpty()) {
            name1 = getString(R.string.Name1);
        }
        if (name2.isEmpty()) {
            name2 = getString(R.string.Name2);
        }

        player1 = new Player(name1, 1);
        player2 = new Player(name2, 2);

        ((TextView)findViewById(R.id.player1Name)).setText(name1);
        ((TextView)findViewById(R.id.player1Score)).setText("0");
        ((TextView)findViewById(R.id.player2Name)).setText(name2);
        ((TextView)findViewById(R.id.player2Score)).setText("0");
        ((TextView)findViewById(R.id.rounds)).setText(r);

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
