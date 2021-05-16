package com.example.rhythmapp;

import com.example.rhythmapp.notes.Measure;
import com.example.rhythmapp.notes.Note;

public class MeasureCoder {

    //convert a measure to a string:
    public static String encodeMeasure(Measure m) {
        StringBuilder str = new StringBuilder();

        //Encode the time signature, e.g. 3/4 becomes 3.2, as QUARTER_NOTE has index 2
        str.append(String.valueOf(m.getTimeSignature()[0]));
        str.append(".");
        str.append(String.valueOf(m.getTimeSignature()[1]));

        //Separator line
        str.append("|");

        for (int i = 0; i < m.getSize(); i++) {
            str.append(String.valueOf(m.getNote(i).getIndex()));
            str.append(".");
        }

        return str.toString();
    }

    //Convert string into a measure
    public static Measure decodeString(String s)
    {
        //First split the string into time signature and notes
        String[] split = new String[2];
        split = s.split("|");

        //Get the time signature
        String[] timeSignature = new String[2];
        timeSignature = split[0].split(".");
        int noteIndex = Integer.parseInt(timeSignature[1]);

        //Initialize m in case something goes wrong
        Measure m = new Measure(4, Note.QUARTER_NOTE);

        for (Note n : Note.values())
        {
            if (n.getIndex() == noteIndex)
            {
                m = new Measure(Integer.parseInt(timeSignature[0]), n);

            }
        }

        //Set the notes in m
        String[] notesInString = split[2].split(".");
        for (String noteString : notesInString)
        {
            for (Note n : Note.values())
            {
                if (n.getIndex() == Integer.parseInt(noteString))
                {
                    m.addNote(n);
                    continue; //No need to check the other notes
                }
            }
        }

        return m;
    }

}
