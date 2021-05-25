package com.example.rhythmapp;

import java.util.ArrayList;

public class Measure {
    //The measure class represents a musical measure.

    public ArrayList<Note> measure; //A container that contains the actual notes in the measure
    private double[] timeSignature; //Represents the musical time signature in front of the measure
    private double currentDuration; //A helper variable to determine if more notes can be added
    private double fullDuration; //The full duration of the measure
    private Note beatUnit;

    //The constructor contains the numBeats and beatUnit parameters, which are to be used as follows:
    //If the musical measure is for example 3/4, set numBeats = 3 and beatUnit = QUARTER_NOTE
    public Measure(double numBeats, Note beatUnit)
    {
        timeSignature = new double[2];
        timeSignature[0] = numBeats;
        timeSignature[1] = beatUnit.getDuration();
        fullDuration = numBeats * beatUnit.getDuration();
        currentDuration = 0;
        this.beatUnit = beatUnit;
        measure = new ArrayList<Note>();
    }

    public boolean addNote(Note n)
    {
        //Adds a note to the measure, after checking if it fits in the measure.
        //Returns true if the note could be added to the measure.
        double duration = n.getDuration();

        if (currentDuration + duration > fullDuration)
        {
            return false;
        }

        currentDuration += duration;
        measure.add(n);

        return true;
    }

    //Remove the final note of the measure
    public void removeNote()
    {
        if (measure.size() > 0) {
            currentDuration -= measure.get(measure.size() - 1).getDuration();
            measure.remove(measure.size() - 1);
        }
    }

    //isFull returns true if the current measure is full
    public boolean isFull()
    {
        return currentDuration == fullDuration;
    }

    //Returns the time signature of the measure
    public double[] getTimeSignature()
    {
        return timeSignature;
    }

    public Note getNote(int i)
    {
        return measure.get(i);
    }

    public int getSize()
    {
        return measure.size();
    }

    public double getDuration()
    {
        return currentDuration;
    }

    public Note getBeatUnit() { return  beatUnit; }
}