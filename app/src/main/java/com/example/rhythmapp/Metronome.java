package com.example.rhythmapp;

import android.media.MediaPlayer;

class Metronome{
    double bpm;
    int measure, counter;
    MediaPlayer playerHigh;
    MediaPlayer playerLow;
    long startTime;

    public Metronome(double bpm, int measure, MediaPlayer playerHigh, MediaPlayer playerLow){
        this.bpm = bpm;
        this.measure = measure;
        this.playerHigh = playerHigh;
        this.playerLow = playerLow;
        startTime = System.nanoTime();
    }

    private void soundHigh() {
        playerHigh.start();
    }

    private void soundLow() {
        playerHigh.start();
    }

    public void start() {
        counter = 0;

        while(counter < 100){
            long timePassed = System.nanoTime();
            if(timePassed >= startTime + 1000000000*(60.0/bpm)){
                if (counter%measure==0){
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
