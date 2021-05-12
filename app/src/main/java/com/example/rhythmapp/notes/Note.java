package com.example.rhythmapp.notes;

public enum Note {
    //The Note class represents the musical notes (as well as rests), with different durations
    WHOLE_NOTE (4),
    HALF_NOTE (2),
    QUARTER_NOTE(1),
    EIGHTH_NOTE(1/2),
    SIXTEENTH_NOTE(1/4),
    WHOLE_REST(4),
    HALF_REST(2),
    QUARTER_REST(1),
    EIGHT_REST(1/2),
    SIXTEENTH_REST (1/4);

    private double duration; //The duration of the note in beats

    private Note(double duration)
    {
        this.duration = duration;
    }

    public double getDuration()
    {
        return duration;
    }
}
