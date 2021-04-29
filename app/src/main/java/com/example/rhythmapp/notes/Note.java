package com.example.rhythmapp.notes;

public enum Note {
    //The Note class represents the musical notes (as well as rests), with different durations
    WHOLE_NOTE (1),
    HALF_NOTE (1/2),
    QUARTER_NOTE(1/4),
    EIGHTH_NOTE(1/8),
    SIXTEENTH_NOTE(1/16),
    WHOLE_REST(1),
    HALF_REST(1/2),
    QUARTER_REST(1/4),
    EIGHT_REST(1/8),
    SIXTEENTH_REST (1/16);

    private double duration;

    private Note(double duration)
    {
        this.duration = duration;
    }

    public double getDuration()
    {
        return duration;
    }
}
