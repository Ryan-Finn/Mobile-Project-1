package edu.sdsmt.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class EndGameActivity extends AppCompatActivity {

    public final static String PLAYER1_MESSAGE = "edu.sdsmt.project1.PLAYER1_MESSAGE";
    public final static String PLAYER2_MESSAGE  = "edu.sdsmt.project1.PLAYER2_MESSAGE";
    public final static String WINNER_MESSAGE  = "edu.sdsmt.project1.WINNER_MESSAGE";
    TextView player1;
    TextView player2;
    TextView winner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        player1 = findViewById(R.id.Player1Score);
        player2 = findViewById(R.id.Player2Score);
        winner = findViewById(R.id.winnerTextView);

        // Get the message from the intent
        Intent intent = getIntent();

        player1.setText(intent.getStringExtra(PLAYER1_MESSAGE));
        player2.setText(intent.getStringExtra(PLAYER2_MESSAGE));
        winner.setText(intent.getStringExtra(WINNER_MESSAGE));

    }
}