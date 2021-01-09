package com.example.comercialesgeuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnCalendario;
    Button btnpartners;
    Button btnPedido;
    Button btnEnvio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCalendario = findViewById(R.id.btnCalend);

        btnpartners = findViewById(R.id.btnParner);

        btnPedido = findViewById(R.id.btnPed);

        btnEnvio = findViewById(R.id.btnEnvio);

        btnCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siguienteLayout(btnCalendario);
            }
        });
        btnpartners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siguienteLayout(btnpartners);
            }
        });
        btnPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siguienteLayout(btnPedido);
            }
        });
        btnEnvio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siguienteLayout(btnEnvio);
            }
        });
    }

    public void siguienteLayout(View v){
        Intent i;
        Button boton=(Button)v;
        if(boton==btnCalendario) {
            i = new Intent(this, CalendarioActivity.class);
        }
       else if(boton==btnpartners) {
          i = new Intent(this, Layoutpartners.class);
        }
        else if(boton==btnEnvio) {
            i = new Intent(this, EnvioDelegacion.class);
        }
       else {
           i = new Intent(this, GestionPedido.class);
        }
        startActivity(i);
    }
}