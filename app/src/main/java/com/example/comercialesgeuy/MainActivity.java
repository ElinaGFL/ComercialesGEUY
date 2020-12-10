package com.example.comercialesgeuy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnCalendario;
    Button partners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnCalendario = findViewById(R.id.button1);

        partners= findViewById(R.id.button2);

    }
}