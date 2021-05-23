package com.example.rhythmapp;

import android.media.MediaPlayer;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Metronome extends Thread {

    private double bpm;
    private int measure, counter;
    private MediaPlayer playerHigh;
    private MediaPlayer playerLow;
    private long startTime;
    private boolean shouldStop;
    private Lock stopLock = new ReentrantLock();

    public Metronome(double bpm, int measure, MediaPlayer playerHigh, MediaPlayer playerLow){
        this.bpm = bpm;
        this.measure = measure;
        this.playerHigh = playerHigh;
        this.playerLow = playerLow;
        startTime = System.nanoTime();
        shouldStop = false;
    }

    private void soundHigh() {
        playerHigh.start();
    }

    private void soundLow() {
        playerHigh.start();
    }

    public void setStop()
    {
        stopLock.lock();
        try
        {
            shouldStop = true;
        }
        finally
        {
            stopLock.unlock();
        }
    }

    public boolean getStop() {
        stopLock.lock();
        try {
            return shouldStop;
        }
        finally
        {
            stopLock.unlock();
        }
    }

    @Override
    public void run() {
        counter = 0;

        while(counter < 100 && !getStop()){
            long timePassed = System.nanoTime();
            if(timePassed >= startTime + 1000000000*(60.0/bpm)){
                if (counter % measure==0){
                    soundHigh();
                    System.out.println("TICK");
                }else{
                    soundLow();
                    System.out.println("TOCK");
                }
                counter++;
                startTime = System.nanoTime();
            }
        }
    }
}
