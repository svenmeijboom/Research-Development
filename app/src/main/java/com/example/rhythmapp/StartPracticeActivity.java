package com.example.rhythmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class StartPracticeActivity extends AppCompatActivity {
    public MediaPlayer playerHigh;
    public MediaPlayer playerLow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_practice);
    }

    public void play(View view) {
        if(playerHigh == null) {
            playerHigh = MediaPlayer.create(this, R.raw.high);
        }
        if(playerLow == null) {
            playerLow = MediaPlayer.create(this, R.raw.click);
        }

        Metronome metronome1 = new Metronome(180,4, playerHigh, playerLow);
        metronome1.start();
    }
}