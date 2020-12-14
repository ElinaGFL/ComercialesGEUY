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

        btnCalendario = findViewById(R.id.button1);

        btnpartners= findViewById(R.id.button2);

        btnPedidos = findViewById(R.id.button3);

        btnEnvio=findViewById(R.id.button4);

        btnCalendario.setOnClickListener(new View.OnClickListener() {
            int i;
            @Override
            public void onClick(View v) {
                i=1;
                siguienteLayout(v,i);
            }
        });

        btnpartners.setOnClickListener(new View.OnClickListener() {
            int i;
            @Override
            public void onClick(View v) {
                i=2;
                siguienteLayout(v,i);
            }
        });


        btnPedidos.setOnClickListener(new View.OnClickListener() {
            int i;
            @Override
            public void onClick(View v) {
                i=3;
                siguienteLayout(v,i);
            }
        });
        btnEnvio.setOnClickListener(new View.OnClickListener() {
            int i;
            @Override
            public void onClick(View v) {
                i=4;
                siguienteLayout(v,i);
            }
        });
    }


    public void siguienteLayout (View v,int i) {
        Intent intent;
        if (i==1){
            intent = new Intent(this, CalendarioActivity.class);
            startActivity(intent);
        }else if(i==2){
            intent = new Intent(this, layoutpartners.class);
            startActivity(intent);
        }else if(i==3){
            intent = new Intent(this, GestionPedido.class);
            startActivity(intent);
        }else if(i==4){
            intent = new Intent(this, GestionPedido.class);
            startActivity(intent);
        }

    }
}