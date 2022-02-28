package edu.sdsmt.project1;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import edu.sdsmt.project1.Control.GameBoardActivity;

public class WelcomeActivity extends AppCompatActivity {
    public final static String PLAYER1NAME_MESSAGE = "edu.sdsmt.project1.PLAYER1NAME_MESSAGE";
    public final static String PLAYER2NAME_MESSAGE  = "edu.sdsmt.project1.PLAYER2NAME_MESSAGE";
    public final static String ROUNDS_MESSAGE  = "edu.sdsmt.project1.ROUNDS_MESSAGE";
    TextView player1;
    TextView player2;
    TextView rounds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        player1 = findViewById(R.id.nameInput1);
        player2 = findViewById(R.id.nameInput2);
        rounds = findViewById(R.id.roundsInput);
    }

    public void onStart(View view) {
        Intent intent = new Intent(this, GameBoardActivity.class);

        intent.putExtra(PLAYER1NAME_MESSAGE, player1.getText().toString());
        intent.putExtra(PLAYER2NAME_MESSAGE, player2.getText().toString());
        intent.putExtra(ROUNDS_MESSAGE, rounds.getText().toString());
        startActivity(intent);
    }

    public void onHowToPlay(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(WelcomeActivity.this);
        builder.setTitle(R.string.HowToPlayTitle);
        builder.setMessage(R.string.HowToPlayMessage);
        builder.setPositiveButton(android.R.string.ok, null);
        builder.show();
    }
}
