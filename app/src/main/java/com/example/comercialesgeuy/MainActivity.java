package com.example.comercialesgeuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnCalendario;
    Button btnpartners;
    Button btnPedidos;
    Button btnEnvio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCalendario = findViewById(R.id.btnCalend);

        btnpartners = findViewById(R.id.btnParner);

        btnPedidos = findViewById(R.id.btnPedido);

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
        btnPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siguienteLayout(btnPedidos);
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
          i = new Intent(this, layoutpartners.class);

        }
        else if(boton==btnPedidos) {
            i = new Intent(this, GestionPedido.class);

        }
       else {
           i = new Intent(this, EnvioDelegacion.class);

        }
        startActivity(i);
    }
}