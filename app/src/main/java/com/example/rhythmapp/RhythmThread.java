package com.example.rhythmapp;

import android.os.SystemClock;
import android.util.Log;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RhythmThread extends Thread {

    //Rhythmthread controls the game. It is a loop that checks whether a tap occurs at the right time
    private Measure measure;
    private long lastTap;
    private long delta;
    private Lock tapLock = new ReentrantLock();
    private Lock stopLock = new ReentrantLock();
    private Lock pointLock = new ReentrantLock();

    private int BPM;
    private boolean shouldStop;
    private int points;

    private PointListener listener;

    public RhythmThread(Measure m, long delta, int BPM, PointListener pointListener)
    {
        measure = m;
        lastTap = -1;
        this.delta = delta;
        shouldStop=false;
        points = 0;
        this.listener = pointListener;


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

    private void addPoints(int p)
    {
        pointLock.lock();
        try
        {
            points += p;
            Log.println(Log.DEBUG, "pointsTag", "Points: " + points);
            if (points < 0)
            {
                points = 0;
            }

            listener.onPointUpdate();
        }
        finally
        {
            pointLock.unlock();
        }
    }

    public int getPoints() {
        return points;
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
            Log.println(Log.DEBUG, "stampTag", "Stamp: " + timeStamps[i]);
        }

        int curNote = 0;
        long startMeasureTime = SystemClock.elapsedRealtime();

        while(!getShouldStop())
        {
            long curTime = SystemClock.elapsedRealtime();

            //Is the current note a rest?
            if (measure.getNote(curNote).isRest())
            {
                curNote++;
            }

            //Have we pressed in the correct time interval?
            if (getLastTap() != -1 && startMeasureTime + timeStamps[curNote] - delta <= getLastTap() && startMeasureTime + timeStamps[curNote] + delta >= getLastTap())
            {
                addPoints(100);
                curNote++;
            }
            else if (getLastTap() != -1)
            {
                //Tapped at the wrong time
                addPoints(-10);
            }
            else if (startMeasureTime + timeStamps[curNote] + delta <= curTime) //Have we passed the current note?
            {
                Log.println(Log.DEBUG, "timeTag", "Times: " + (startMeasureTime + timeStamps[curNote] + delta) + ", " + curTime);
                addPoints(-10);
                curNote++;
            }

            setLastTap(-1);

            //Have we reached the end of the measure
            if (curNote == measure.getSize())
            {
                startMeasureTime = SystemClock.elapsedRealtime();
                curNote = 0;
            }
        }
    }


}