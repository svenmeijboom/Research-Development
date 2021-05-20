package com.example.rhythmapp;

import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    private int screenWidth;
    private int screenHeight;

    int counter;
    MediaPlayer playerHigh;
    MediaPlayer playerLow;
    long startTime;

    private ImageView musicNote_Image;
    private float musicNote_X;
    //private float musicNote_Y;

    private Handler handler = new Handler();
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        musicNote_Image = (ImageView)findViewById(R.id.musicNote_image);
        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        musicNote_Image.setX(screenWidth);
        musicNote_Image.setY(200.0f);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        changePos();
                    }
                });
            }
        }, 0, 20);

        Button start_button = findViewById(R.id.start_button);
        start_button.setOnClickListener(v -> {
            Intent intent = new Intent(this, SelectionMenuActivity.class);
            startActivity(intent);
        });
    }

    public void changePos() {
        musicNote_X += -10;
        if (musicNote_Image.getX() < -2*screenWidth){
            musicNote_X = screenWidth;
        }
        musicNote_Image.setX(musicNote_X);
        //musicNote_Image.setY(musicNote_Y);
    }

    private void soundHigh() {
        playerHigh.start();
    }

    private void soundLow() {
        playerLow.start();
    }

    private void start(double bpm, int measure, int bars) {
        counter = 0;

        while(counter < bars*measure){
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

    public void play(View view) {
        if(playerHigh == null) {
            playerHigh = MediaPlayer.create(this, R.raw.high);
            playerHigh.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayerHigh();
                }
            });
        }
        if(playerLow == null) {
            playerLow = MediaPlayer.create(this, R.raw.click);
            playerLow.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayerLow();
                }
            });
        }

        start(140, 5, 8);
    }

    private void stopPlayerHigh() {
        if(playerHigh != null) {
            playerHigh.release();
            playerHigh = null;
        }
    }

    private void stopPlayerLow() {
        if(playerLow != null) {
            playerLow.release();
            playerLow = null;
        }
    }
}