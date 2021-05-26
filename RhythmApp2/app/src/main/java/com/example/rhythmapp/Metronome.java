package com.example.rhythmapp;

import android.content.Context;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.view.View;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Metronome extends Thread{
    private MediaPlayer playerHigh;
    private MediaPlayer playerMid;
    private MediaPlayer playerLow;
    private long startTime;
    private boolean preCount;
    private boolean shouldStop;
    private Lock stopLock = new ReentrantLock();


    private void soundHigh() {
        playerHigh.start();
    }
    private void soundMid() { playerMid.start(); }
    private void soundLow() { playerLow.start(); }

    private void playMetronome(double bpm, int measure, int bars, boolean eight) {
        int counter = 0;

        if(eight){
            int internalCounter = 0;
            while(counter < bars*measure+measure && !shouldStop){
                long timePassed = System.nanoTime();
                if(timePassed >= startTime + 1000000000*(60.0/bpm)/2){
                    if (counter%measure==0){
                        soundHigh();
                        internalCounter = 0;
                    } else if(internalCounter%2==0) {
                        soundMid();
                    } else {
                        soundLow();
                    }
                    counter++;
                    internalCounter++;
                    startTime = System.nanoTime();
                    if(counter == measure) {
                        preCount = false;
                        System.out.println("Go");
                    }
                }
            }
        } else {
            while(counter < bars*measure+measure && !shouldStop){
                long timePassed = System.nanoTime();
                if(timePassed >= startTime + 1000000000*(60.0/bpm)){
                    if (counter%measure==0){
                        soundHigh();
                    } else {
                        soundLow();
                    }
                    counter++;
                    startTime = System.nanoTime();
                    if(counter == measure) {
                        preCount = false;
                        System.out.println("Go");
                    }
                }
            }
        }
    }

    public void startMetronome (Context context, double bpm, int measure, int bars, boolean eight) {
        if(playerHigh == null) {
            playerHigh = MediaPlayer.create(context, R.raw.high);
        }
        if(playerMid == null) {
            playerMid = MediaPlayer.create(context, R.raw.mid);
        }
        if(playerLow == null) {
            playerLow = MediaPlayer.create(context, R.raw.click);
        }

        shouldStop = false;
        preCount = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                playMetronome(bpm, measure, bars, eight);

                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                stopPlayerHigh();
                stopPlayerMid();
                stopPlayerLow();
            }
        }).start();
    }

    public void stop(View view) {
        shouldStop = true;
    }

    private void stopPlayerHigh() {
        if(playerHigh != null) {
            playerHigh.release();
            playerHigh = null;
        }
    }

    private void stopPlayerMid() {
        if(playerMid != null) {
            playerMid.release();
            playerMid = null;
        }
    }

    private void stopPlayerLow() {
        if(playerLow != null) {
            playerLow.release();
            playerLow = null;
        }
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
}
