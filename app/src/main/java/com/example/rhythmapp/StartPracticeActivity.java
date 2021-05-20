package com.example.rhythmapp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StartPracticeActivity extends AppCompatActivity {
    public MediaPlayer playerHigh;
    public MediaPlayer playerLow;

    private Measure measure;
    private MeasureView mView;
    private TextView pointView;

    //Test BPM:
    private int BPM = 100;
    private boolean hasStarted;

    private RhythmThread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_practice);

        //Test measure
        measure = MeasureCoder.decodeString("4,2I2,2,3,3,2");
        mView = (MeasureView) findViewById(R.id.practiceMView);
        mView.setCurrentMeasure(measure);
        hasStarted = false;

        Button startButton = (Button) findViewById(R.id.start_practice_button);
        startButton.setOnClickListener(v -> {

            if (!hasStarted) {
                startButton.setText("Stop");
                hasStarted = true;
                startPractice();
            } else {
                startButton.setText("Start");
                hasStarted = false;
            }
        });

        pointView = (TextView) findViewById(R.id.point_view);
    }

    public void play(View view) {
        if(playerHigh == null) {
            playerHigh = MediaPlayer.create(this, R.raw.high);
        }
        if(playerLow == null) {
            playerLow = MediaPlayer.create(this, R.raw.click);
        }

        Metronome metronome1 = new Metronome(BPM,4, playerHigh, playerLow);
        metronome1.start();
    }

    public void screenTapped(View v)
    {
        if (hasStarted)
        {
            thread.setLastTap(SystemClock.elapsedRealtime());
        }
    }

    private void setPoints(int p) {

        runOnUiThread(() -> {
            pointView.setText("Points: " + String.valueOf(p));
            Log.println(Log.DEBUG, "pointsTag", "Setting points to: " + p);
        });

    }

    private void startPractice()
    {
        hasStarted = true;

        thread = new RhythmThread(measure, 50, BPM, () -> {

            Log.println(Log.DEBUG, "pointsTag", "Edited points!");

            int points = thread.getPoints();

            setPoints(points);

        });

        thread.start();
    }
}