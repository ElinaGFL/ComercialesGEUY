package com.example.comercialesgeuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnCalendario;
    Button partners;
    Button btnPedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCalendario = findViewById(R.id.button1);

        partners= findViewById(R.id.button2);

        btnPedidos = findViewById(R.id.button3);

        btnCalendario.setOnClickListener(v -> abrirCalendarioActivity(v));
    }

    public void abrirCalendarioActivity (View view) {
        Intent intent = new Intent(this, CalendarioActivity.class);
        startActivity(intent);
    }
}