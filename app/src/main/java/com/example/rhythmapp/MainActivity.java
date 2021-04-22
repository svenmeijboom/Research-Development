package com.example.rhythmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button start_button = findViewById(R.id.start_button);
        start_button.setOnClickListener(v -> {
            Intent intent = new Intent(this, SelectionMenuActivity.class);
            startActivity(intent);
        });
    }
}