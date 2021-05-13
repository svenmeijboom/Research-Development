package com.example.rhythmapp;

import android.os.Bundle;
import android.widget.ImageButton;

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

        mView = (MeasureView) findViewById(R.id.measureView);
        mView.setCurrentMeasure(song.get(song.size() - 1));

        setButtonActivities();
    }

    //Helper function to make the code more readable
    private void setButtonActivities()
    {
        ImageButton wholeNoteButton = (ImageButton) findViewById(R.id.whole_note_button);
        wholeNoteButton.setOnClickListener(v -> {

            song.get(song.size() - 1).addNote(Note.WHOLE_NOTE);
            mView.setCurrentMeasure(song.get(song.size() - 1));

        });

        ImageButton halfNoteButton = (ImageButton) findViewById(R.id.half_note_button);
        halfNoteButton.setOnClickListener(v -> {

            song.get(song.size() - 1).addNote(Note.HALF_NOTE);
            mView.setCurrentMeasure(song.get(song.size() - 1));

        });

        ImageButton quarterNoteButton = (ImageButton) findViewById(R.id.quarter_note_button);
        quarterNoteButton.setOnClickListener(v -> {
            song.get(song.size() - 1).addNote(Note.QUARTER_NOTE);
            mView.setCurrentMeasure(song.get(song.size() - 1));
        });

        ImageButton eighthNoteButton = (ImageButton) findViewById(R.id.eighth_note_button);
        eighthNoteButton.setOnClickListener(v -> {
            song.get(song.size() - 1).addNote(Note.EIGHTH_NOTE);
            mView.setCurrentMeasure(song.get(song.size() - 1));
        });

        ImageButton sixteenthNoteButton = (ImageButton) findViewById(R.id.sixteenth_note_button);
        sixteenthNoteButton.setOnClickListener(v -> {
            song.get(song.size() - 1).addNote(Note.SIXTEENTH_NOTE);
            mView.setCurrentMeasure(song.get(song.size() - 1));
        });

        ImageButton wholeRestButton = (ImageButton) findViewById(R.id.whole_rest_button);
        wholeRestButton.setOnClickListener(v -> {
            song.get(song.size() - 1).addNote(Note.WHOLE_REST);
            mView.setCurrentMeasure(song.get(song.size() - 1));
        });

        ImageButton halfRestButton = (ImageButton) findViewById(R.id.half_rest_button);
        halfRestButton.setOnClickListener(v -> {
            song.get(song.size() - 1).addNote(Note.HALF_REST);
            mView.setCurrentMeasure(song.get(song.size() - 1));
        });

        ImageButton quarterRestButton = (ImageButton) findViewById(R.id.quarter_rest_button);
        quarterRestButton.setOnClickListener(v -> {
            song.get(song.size() - 1).addNote(Note.QUARTER_REST);
            mView.setCurrentMeasure(song.get(song.size() - 1));
        });

        ImageButton eighthRestButton = (ImageButton) findViewById(R.id.eighth_rest_button);
        eighthRestButton.setOnClickListener(v -> {
            song.get(song.size() - 1).addNote(Note.EIGHTH_REST);
            mView.setCurrentMeasure(song.get(song.size() - 1));
        });

        ImageButton sixteenthRestButton = (ImageButton) findViewById(R.id.sixteenth_rest_button);
        sixteenthRestButton.setOnClickListener(v -> {
            song.get(song.size() - 1).addNote(Note.SIXTEENTH_REST);
            mView.setCurrentMeasure(song.get(song.size() - 1));
        });
    }
}