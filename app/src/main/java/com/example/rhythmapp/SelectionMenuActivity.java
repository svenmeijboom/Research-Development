package com.example.rhythmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class SelectionMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_menu);

        Button createRhythm_button = findViewById(R.id.createRhythm_button);
        createRhythm_button.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreateRhythmActivity.class);
            startActivity(intent);
        });

        Button startPractice_button = findViewById(R.id.startPractice_button);
        startPractice_button.setOnClickListener(v -> {
            Intent intent = new Intent(this, StartPracticeActivity.class);
            startActivity(intent);
        });

        Button leaderboard_button = findViewById(R.id.leaderboard_button);
        leaderboard_button.setOnClickListener(v -> {
            Intent intent = new Intent(this, LeaderboardActivity.class);
            startActivity(intent);
        });
    }
}