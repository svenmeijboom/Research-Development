package com.example.rhythmapp;

import android.util.Log;

import com.example.rhythmapp.notes.Measure;
import com.example.rhythmapp.notes.Note;

public class MeasureCoder {

    //convert a measure to a string:
    public static String encodeMeasure(Measure m) {
        StringBuilder str = new StringBuilder();

        //Encode the time signature, e.g. 3/4 becomes 3,2, as QUARTER_NOTE has index 2
        str.append(String.valueOf(m.getTimeSignature()[0]));
        str.append(",");
        str.append(String.valueOf(m.getTimeSignature()[1]));

        //Separator line is uppercase i
        str.append("I");

        //Append the indices of the notes, separated by a ,
        for (int i = 0; i < m.getSize(); i++) {
            str.append(String.valueOf(m.getNote(i).getIndex()));
            str.append(",");
        }

        str.deleteCharAt(str.length() - 1); //The final character is a , which should not be there

        return str.toString();
    }

    //Convert string into a measure
    public static Measure decodeString(String s)
    {
        //First split the string into time signature and notes
        String[] split = new String[2];
        split = s.split("I");

        //Get the time signature
        String[] timeSignature = new String[2];
        timeSignature = split[0].split(",");
        int noteIndex = Integer.parseInt(timeSignature[1]);

        //Test with logs
        Log.println(Log.DEBUG, "decStr", "time signature: " + timeSignature[0] + "." + timeSignature[1]);


        //Initialize m in case something goes wrong
        Measure m = new Measure(4, Note.QUARTER_NOTE);

        for (Note n : Note.values())
        {
            if (n.getIndex() == noteIndex)
            {
                m = new Measure(Integer.parseInt(timeSignature[0]), n);
                Log.println(Log.DEBUG, "decStr", "Measure created, index: " + String.valueOf(n.getIndex()));
            }
        }

        //Set the notes in m
        String[] notesInString = split[1].split(",");
        for (String noteString : notesInString)
        {
            Log.println(Log.DEBUG, "decStr", "Note added: " + noteString);
            for (Note n : Note.values())
            {
                if (n.getIndex() == Integer.parseInt(noteString))
                {
                    m.addNote(n);
                    Log.println(Log.DEBUG, "decStr", "note added: " + String.valueOf(n.getIndex()));
                    break; //No need to check the other notes
                }
            }
        }

        return m;
    }

}
