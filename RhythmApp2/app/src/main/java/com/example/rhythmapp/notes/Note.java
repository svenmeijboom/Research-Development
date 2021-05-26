package com.example.rhythmapp.notes;

public enum Note {
    //The Note class represents the musical notes (as well as rests), with different durations
    //"Basic" notes
    WHOLE_NOTE (4, 0),
    HALF_NOTE (2, 1),
    QUARTER_NOTE(1,2),
    EIGHTH_NOTE(0.5,3),
    SIXTEENTH_NOTE(0.25,4),
    WHOLE_REST(4,5),
    HALF_REST(2,6),
    QUARTER_REST(1,7),
    EIGHTH_REST(0.5,8),
    SIXTEENTH_REST (0.25,9),

    //"Special" notes
    HALF_NOTE_DOT(3, 10),
    QUARTER_NOTE_DOT(1.5, 11),
    EIGHTH_NOTE_DOT(0.75, 12);

    private double duration; //The duration of the note in beats
    private int index; //Index is a helper variable to make drawing easier

    private Note(double duration, int index)
    {
        this.duration = duration;
        this.index = index;
    }

    public double getDuration()
    {
        return duration;
    }

    public int getIndex() { return index; }
}
