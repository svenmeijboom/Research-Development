package com.example.rhythmapp;

public enum Note {
    //The Note class represents the musical notes (as well as rests), with different durations
    //"Basic" notes
    WHOLE_NOTE (4, 0, false),
    HALF_NOTE (2, 1, false),
    QUARTER_NOTE(1,2, false),
    EIGHTH_NOTE(0.5,3, false),
    SIXTEENTH_NOTE(0.25,4, false),
    WHOLE_REST(4,5, true),
    HALF_REST(2,6, true),
    QUARTER_REST(1,7, true),
    EIGHTH_REST(0.5,8, true),
    SIXTEENTH_REST (0.25,9, true),

    //"Special" notes
    HALF_NOTE_DOT(3, 10, false),
    QUARTER_NOTE_DOT(1.5, 11, false),
    EIGHTH_NOTE_DOT(0.75, 12, false);


    private final double duration; //The duration of the note in beats
    private final int index; //Index is a helper variable to make drawing easier
    private final boolean isRest;

    private Note(double duration, int index, boolean rest)
    {
        this.duration = duration;
        this.index = index;
        this.isRest = rest;
    }

    public double getDuration()
    {
        return duration;
    }

    public int getIndex() {
        return index;
    }

    public boolean isRest() { return isRest; }
}