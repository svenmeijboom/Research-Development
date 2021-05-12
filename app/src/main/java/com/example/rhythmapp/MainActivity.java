package com.example.rhythmapp;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    private int screenWidth;
    private int screenHeight;

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

        musicNote_Image.setX(-200.0f);
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
        musicNote_X += 10;
        if (musicNote_Image.getX() > screenWidth){
            musicNote_X = -2*screenWidth;
        }
        musicNote_Image.setX(musicNote_X);
        //musicNote_Image.setY(musicNote_Y);
    }
}