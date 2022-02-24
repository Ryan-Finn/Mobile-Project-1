package edu.sdsmt.project1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import edu.sdsmt.project1.Control.GameBoardActivity;

public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void onStart(View view) {
        Intent intent = new Intent(this, GameBoardActivity.class);
        startActivity(intent);
    }
}
