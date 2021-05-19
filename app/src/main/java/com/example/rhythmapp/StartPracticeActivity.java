package com.example.rhythmapp;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rhythmapp.notes.Measure;

public class StartPracticeActivity extends AppCompatActivity {

    private Measure measure;
    private MeasureView mView;

    //Test BPM:
    private int BPM = 100;
    private boolean hasStarted;

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
            } else {
                startButton.setText("Start");
                hasStarted = false;
            }
        });
    }
}