package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.lab3.game_logic.LinesGame;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(new LinesGame(this));
    }
}