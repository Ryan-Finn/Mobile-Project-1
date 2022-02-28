package edu.sdsmt.project1.Control;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import edu.sdsmt.project1.Control.CaptureSelectionActivity;
import edu.sdsmt.project1.Model.Player;
import edu.sdsmt.project1.R;
import edu.sdsmt.project1.View.GameBoardView;
import edu.sdsmt.project1.WelcomeActivity;

public class GameBoardActivity extends AppCompatActivity {
    private GameBoardView view;
    public static final String CAPTURED_INT = "edu.sdsmt.project1.RETURN_MESSAGE";
    private TextView player1Name;
    private TextView player2Name;
    private TextView player1Score;
    private TextView player2Score;
    private TextView rounds;
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

        view.addPlayer(name1,0);
        view.addPlayer(name2,1);
        view.setRounds(Integer.parseInt(r));
        view.setDefaultPlayer();

        player1Name = findViewById(R.id.player1Name);
        player2Name = findViewById(R.id.player2Name);
        player1Score = findViewById(R.id.player1Score);
        player2Score = findViewById(R.id.player2Score);
        rounds = findViewById(R.id.rounds);
        player1Name.setText(name1);
        player2Score.setText("0");
        player2Name.setText(name2);
        player1Score.setText("0");
        rounds.setText(r);
        player1Name.setTextColor(Color.parseColor("#FF0000"));

        Log.i("CurrentPlayer", "Reached gameboard");
       view.setOnTouchListener((view, motionEvent) -> {
            view.onTouchEvent(motionEvent);
            updateGUI();
            isEndGame();
            return true;
        });

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
              //  updateGUI();
            }
        });
    }

    private void isEndGame() {
        if(view.isEndRound())
        {
            Intent intent = new Intent(this, EndGameActivity.class);
            intent.putExtra(EndGameActivity.PLAYER1_MESSAGE, view.getPlayer1Name()
                    + "'s Score\n" + view.getPlayer1Score());
            intent.putExtra(EndGameActivity.PLAYER2_MESSAGE, view.getPlayer2Name()
                    + "'s Score\n" + view.getPlayer2Score());
            String winner = view.getPlayer2Name() ;
            if(Integer.parseInt(view.getPlayer1Score()) > Integer.parseInt(view.getPlayer2Score()))
                winner = "WINNER\n" + view.getPlayer1Name();

            intent.putExtra(EndGameActivity.WINNER_MESSAGE, winner);
            startActivity(intent);
            finish();
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    private void updateGUI() {

        if(view.getCurrentPlayerId() == 0) {
            player1Name.setTextColor(Color.parseColor("#FF0000"));
            player2Name.setTextColor(Color.parseColor("#FFFFFF"));
        }
        else if(view.getCurrentPlayerId() == 1) {
            player2Name.setTextColor(Color.parseColor("#FF0000"));
            player1Name.setTextColor(Color.parseColor("#FFFFFF"));
        }


       player1Score.setText(view.getPlayer1Score());
        player2Score.setText(view.getPlayer2Score());
        rounds.setText(view.getRounds());
    }


    public void onCaptureOptionsClick(View view) {
       // updateGUI();
        Intent switchActivityIntent = new Intent(this, CaptureSelectionActivity.class);
        activityLauncher.launch(switchActivityIntent);
    }

}
