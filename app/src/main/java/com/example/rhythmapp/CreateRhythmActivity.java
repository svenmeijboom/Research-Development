package com.example.rhythmapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rhythmapp.notes.Measure;
import com.example.rhythmapp.notes.Note;

import java.util.ArrayList;

public class CreateRhythmActivity extends AppCompatActivity {

    private ArrayList<Measure> song;

    private MeasureView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_rhythm);

        song = new ArrayList<Measure>();
        //Start with a 4/4 measure, for simplicity
        song.add(new Measure(4, Note.QUARTER_NOTE));
        song.get(song.size() - 1).addNote(Note.QUARTER_NOTE);
        song.get(song.size() - 1).addNote(Note.QUARTER_NOTE);


        mView = (MeasureView) findViewById(R.id.measureView);
        mView.setCurrentMeasure(song.get(song.size() - 1));

        //Set button activity
        Button testB = (Button) findViewById(R.id.test_quarter_button);
        testB.setOnClickListener(v -> {
            Log.println(Log.DEBUG, "TestButton", "Clicked!");
            song.get(song.size() - 1).addNote(Note.QUARTER_NOTE);
            mView.setCurrentMeasure(song.get(song.size() - 1));

        });
    }
}