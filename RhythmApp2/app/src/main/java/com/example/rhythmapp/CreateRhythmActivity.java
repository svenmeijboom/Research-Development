package com.example.rhythmapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.Toast;

public class CreateRhythmActivity extends AppCompatActivity {

    private Measure measure;
    private MeasureView mView;
    private int curSig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_rhythm);

        measure = new Measure(4, Note.QUARTER_NOTE);

        curSig = 0;

        mView = (MeasureView) findViewById(R.id.measureView);
        mView.setCurrentMeasure(measure);

        setButtonActivities();
    }

    //Helper function to make the code more readable
    private void setButtonActivities()
    {
        ImageButton wholeNoteButton = (ImageButton) findViewById(R.id.whole_note_button);
        wholeNoteButton.setOnClickListener(v -> {

            measure.addNote(Note.WHOLE_NOTE);
            mView.setCurrentMeasure(measure);

        });

        ImageButton halfNoteButton = (ImageButton) findViewById(R.id.half_note_button);
        halfNoteButton.setOnClickListener(v -> {

            measure.addNote(Note.HALF_NOTE);
            mView.setCurrentMeasure(measure);

        });

        ImageButton quarterNoteButton = (ImageButton) findViewById(R.id.quarter_note_button);
        quarterNoteButton.setOnClickListener(v -> {
            measure.addNote(Note.QUARTER_NOTE);
            mView.setCurrentMeasure(measure);
        });

        ImageButton eighthNoteButton = (ImageButton) findViewById(R.id.eighth_note_button);
        eighthNoteButton.setOnClickListener(v -> {
            measure.addNote(Note.EIGHTH_NOTE);
            mView.setCurrentMeasure(measure);
        });

        ImageButton sixteenthNoteButton = (ImageButton) findViewById(R.id.sixteenth_note_button);
        sixteenthNoteButton.setOnClickListener(v -> {
            measure.addNote(Note.SIXTEENTH_NOTE);
            mView.setCurrentMeasure(measure);
        });

        ImageButton wholeRestButton = (ImageButton) findViewById(R.id.whole_rest_button);
        wholeRestButton.setOnClickListener(v -> {
            measure.addNote(Note.WHOLE_REST);
            mView.setCurrentMeasure(measure);
        });

        ImageButton halfRestButton = (ImageButton) findViewById(R.id.half_rest_button);
        halfRestButton.setOnClickListener(v -> {
            measure.addNote(Note.HALF_REST);
            mView.setCurrentMeasure(measure);
        });

        ImageButton quarterRestButton = (ImageButton) findViewById(R.id.quarter_rest_button);
        quarterRestButton.setOnClickListener(v -> {
            measure.addNote(Note.QUARTER_REST);
            mView.setCurrentMeasure(measure);
        });

        ImageButton eighthRestButton = (ImageButton) findViewById(R.id.eighth_rest_button);
        eighthRestButton.setOnClickListener(v -> {
            measure.addNote(Note.EIGHTH_REST);
            mView.setCurrentMeasure(measure);
        });

        ImageButton sixteenthRestButton = (ImageButton) findViewById(R.id.sixteenth_rest_button);
        sixteenthRestButton.setOnClickListener(v -> {
            measure.addNote(Note.SIXTEENTH_REST);
            mView.setCurrentMeasure(measure);
        });

        Button removeButton = (Button) findViewById(R.id.remove_note_button);
        removeButton.setOnClickListener(v -> {
            measure.removeNote();
            mView.setCurrentMeasure(measure);
        });

        Button dotButton = (Button) findViewById(R.id.dot_note_button);
        dotButton.setOnClickListener(v -> {
            Note temp = measure.getNote(measure.getSize() - 1);
            Note tBA;

            switch(temp)
            {
                case HALF_NOTE:
                    tBA = Note.HALF_NOTE_DOT;
                    break;

                case QUARTER_NOTE:
                    tBA = Note.QUARTER_NOTE_DOT;
                    break;

                case EIGHTH_NOTE:
                    tBA = Note.EIGHTH_NOTE_DOT;
                    break;

                default:
                    tBA = temp;
                    break;
            }

            measure.removeNote();
            if(!measure.addNote(tBA))
            {
                //If tBA cannot be added, simply put temp back
                measure.addNote(temp);
            }

            mView.setCurrentMeasure(measure);
        });


        Button practiceButton = (Button) findViewById(R.id.to_practice_button);
        practiceButton.setOnClickListener(v -> {
            //Switch to the practice activity
            if (measure.isFull())
            {

                Intent intent = new Intent(this, StartPracticeActivity.class);
                intent.putExtra("ENCODED_MEASURE", MeasureCoder.encodeMeasure(measure));
                Log.println(Log.DEBUG, "intTag", "Succesfully created intent with string: " + MeasureCoder.encodeMeasure(measure));
                startActivity(intent);

            }
            else
            {
                Toast t = Toast.makeText(this, "Please fill the measure!", Toast.LENGTH_SHORT);
                t.show();
            }

        });

        Button timeSigBut = (Button) findViewById(R.id.change_timesig_button);
        timeSigBut.setOnClickListener(v -> {
            //Switch time signature between 4/4, 3/4, 3/8 and 6/8
            curSig++;
            switch(curSig)
            {
                case 1: //Change to 3/4
                    measure = new Measure(3, Note.QUARTER_NOTE);
                    break;
                case 2: //Change to 3/8
                    measure = new Measure(3, Note.EIGHTH_NOTE);
                    break;
                case 3: //Change to 6/8
                    measure = new Measure(6, Note.EIGHTH_NOTE);
                    break;
                default: //Switch to 4/4
                    measure = new Measure(4, Note.QUARTER_NOTE);
                    curSig = 0;
                    break;
            }

            mView.setCurrentMeasure(measure);
        });
    }
}