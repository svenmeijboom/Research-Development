package com.example.rhythmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StartPracticeActivity extends AppCompatActivity {
    private Metronome metronome;

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

        //Get the measure string, if it is available
        Intent intent = getIntent();
        if (intent.getExtras() != null)
        {
            String s = intent.getExtras().getString("ENCODED_MEASURE");
            measure = MeasureCoder.decodeString(s);
        }
        else
        {
            //Load the default test measure
            measure = MeasureCoder.decodeString("4,2I2,2,2,2");
        }

        mView = (MeasureView) findViewById(R.id.practiceMView);
        mView.setCurrentMeasure(measure);
        hasStarted = false;
        Button startButton = (Button) findViewById(R.id.start_practice_button);
        startButton.setOnClickListener(v -> {

            if (!hasStarted) {
                startButton.setText("Stop");
                startPractice();
                playMetronome();
            } else {
                startButton.setText("Start");
                thread.shouldStop();
                metronome.setStop();
                hasStarted = false;
            }
        });

        pointView = (TextView) findViewById(R.id.point_view);
    }

    public void playMetronome() {
        metronome = new Metronome();
        metronome.startMetronome(this, BPM, (int)measure.getTimeSignature()[0], 4, measure.getBeatUnit()==Note.EIGHTH_NOTE);
        //metronome.startMetronome(this, 120, 5, 4, true);
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
            pointView.setText("Points: " + p);
        });

    }

    private void startPractice()
    {
        pointView.setText("Points: " + 0);
        hasStarted = true;
        thread = new RhythmThread(measure, 150, BPM, () -> {
            int points = thread.getPoints();
            setPoints(points);
        });
        thread.start();
    }
}