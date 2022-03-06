package edu.sdsmt.project1;

/* *
 * Project 1 Grading
 *
 * Group:
 * ____ 6pt No redundant activities
 * Done 6pt How to play dialog
 * Done 6pt Icons
 * ____ 6pt End activity
 * ____ 6pt Back button handled
 * How to open the "how to play dialog": Done
 *
 * Individual:
 *
 * 	Play activity and custom view
 *
 * 		Done 9pt Activity appearance
 * 		Done 16pt Static Custom View
 * 		Done 20pt Dynamic part of the Custom View
 * 		Done 15pt Rotation
 *
 * 	Welcome activity and Game Class
 *
 * 		____ 13pt Welcome activity appearance
 * 		____ 20pt Applying capture rules
 * 		____ 12pt Game state
 * 		____ 15pt Rotation
 * 		What is the probability of the rectangle capture: _____
 *
 * 	Capture activity and activity sequencing
 *
 * 		Done 9pt Capture activity appearance
 * 		Done 16pt Player round sequencing
 * 		Done 20pt Move to next activity
 * 		Done 15pt Rotation
 *
 * 	Timer
 *
 * 		____ 9pt Timer activity
 * 		____ 24pt Graphic
 * 		____ 12pt Player turn end
 * 		____ 15pt Rotation
 *
 *
 * Please list any additional rules that may be needed to properly grade your project:
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
        player1.setText("");
        player2.setText("");
        rounds.setText("");
    }

    public void onHowToPlay(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(WelcomeActivity.this);
        builder.setTitle(R.string.HowToPlayTitle);
        builder.setMessage(R.string.HowToPlayMessage);
        builder.setPositiveButton(android.R.string.ok, null);
        builder.show();
    }
}
