package com.example.rhythmapp;

import android.os.SystemClock;

import com.example.rhythmapp.notes.Measure;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RhythmThread extends Thread {

    //Rhythmthread controls the game. It is a loop that checks whether a tap occurs at the right time
    private Measure measure;
    private long lastTap;
    private long delta;
    private Lock tapLock = new ReentrantLock();
    private Lock stopLock = new ReentrantLock();
    private int BPM;
    private boolean shouldStop;
    private int points;

    public RhythmThread(Measure m, long delta, int BPM)
    {
        measure = m;
        lastTap = -1;
        this.delta = delta;
        shouldStop=false;
        points = 0;
    }

    public void setLastTap(long l)
    {
        tapLock.lock();
        try
        {
            lastTap = l;
        }
        finally {
            tapLock.unlock();
        }
    }

    private long getLastTap()
    {
        tapLock.lock();
        try
        {
            return lastTap;
        }
        finally
        {
            tapLock.unlock();
        }
    }

    public void shouldStop()
    {
        stopLock.lock();
        try
        {
            shouldStop = true;
        }
        finally {
            stopLock.unlock();
        }

    }

    private boolean getShouldStop()
    {
        stopLock.lock();
        try
        {
            return shouldStop;
        }
        finally {
            stopLock.unlock();
        }
    }

    @Override
    public void run()
    {

        //First calculate time stamps
        long[] timeStamps = new long[measure.getSize()];
        timeStamps[0] = 0;
        for (int i = 1; i < measure.getSize(); i++)
        {
            timeStamps[i] = timeStamps[i - 1] + ((long) (measure.getNote(i-1).getDuration() * 60.0 / BPM * 1000));
        }

        int curNote = 0;
        long startMeasureTime = SystemClock.elapsedRealtime();

        while(!getShouldStop())
        {
            long curTime = SystemClock.elapsedRealtime();

            //Have we pressed in the correct time interval?
            if (startMeasureTime + timeStamps[curNote] - delta <= getLastTap() && startMeasureTime + timeStamps[curNote] + delta >= getLastTap())
            {
                points += 10;
                curNote++;
            }

            //Have we passed the current note?
            if (startMeasureTime + timeStamps[curNote] >= curTime + delta)
            {
                points -= 10;
                curNote++;
            }

            //Have we reached the end of the measure
            if (curNote == measure.getSize())
            {
                startMeasureTime = SystemClock.elapsedRealtime();
                curNote = 0;
            }
        }
    }


}
