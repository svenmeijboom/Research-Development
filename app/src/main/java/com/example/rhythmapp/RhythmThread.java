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
        this.BPM = BPM;

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
        pointLock.lock();
        try
        {
            return points;
        }
        finally
        {
            pointLock.unlock();
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
            timeStamps[i] = timeStamps[i - 1] + ((long) (measure.getNote(i-1).getDuration() * 60.0 / BPM * 1000.0));
            Log.println(Log.DEBUG, "stampTag", "Stamp: " + timeStamps[i]);
        }

        int curNote = 0;
        long startMeasureTime = SystemClock.elapsedRealtime();
        boolean firstMeasureDone = false; //First measure does not count toward score


        while(!getShouldStop())
        {
            long curTime = SystemClock.elapsedRealtime();
            setLastTap(-1); //Reset lastTap to make sure it does not carry over the previous tap

            //Is the current note a rest?
            if (measure.getNote(curNote).isRest())
            {
                curNote++;
                if (curNote == measure.getSize())
                {
                    startMeasureTime = SystemClock.elapsedRealtime();
                    curNote = 0;
                }
            }

            //Have we pressed in the correct time interval?
            if (getLastTap() != -1 && startMeasureTime + timeStamps[curNote] - delta <= getLastTap() && startMeasureTime + timeStamps[curNote] + delta >= getLastTap())
            {
                if (firstMeasureDone)
                {
                    addPoints(10);
                }

                curNote++;
            }
            else if (getLastTap() != -1)
            {
                //Tapped at the wrong time
                if (firstMeasureDone)
                {
                    //addPoints(-10);
                }
            }
            else if (startMeasureTime + timeStamps[curNote] + delta <= curTime)
            {
                //We have already missed the current note
                if (firstMeasureDone)
                {
                    //addPoints(-10);
                }
                curNote++;
            }

            //Have we reached the end of the measure?
            if (curNote == measure.getSize())
            {
                startMeasureTime = SystemClock.elapsedRealtime();
                curNote = 0;
                firstMeasureDone = true;
            }
        }
    }


}