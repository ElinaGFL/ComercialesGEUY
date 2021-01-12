package com.example.comercialesgeuy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnCalendario;
    Button btnpartners;
    Button btnPed;
    Button btnEnvio;
    Button btnllamar;
    Button btnCorreo;
    static final int REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCalendario = findViewById(R.id.btnCalend);

        btnpartners = findViewById(R.id.btnParner);

        btnPed = findViewById(R.id.btnPed);

        btnEnvio = findViewById(R.id.btnEnvio);

        pidePermisos();

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

        btnEnvio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siguienteLayout(btnEnvio);
            }
        });

        btnPed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siguienteLayout(btnPed);
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
       else if (boton==btnPed){
           i = new Intent(this, GestionPedido.class);
        }else {
           i= new Intent(this, MainActivity.class);
        }
        startActivity(i);
    }

    public void pidePermisos() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) + ContextCompat.checkSelfPermission
                (MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale
                    (MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Permitir:");
                builder.setMessage("Usar la memoria para leer y escribir los archivos:");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(
                                MainActivity.this,
                                new String[]{
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.READ_EXTERNAL_STORAGE
                                }, REQUEST_CODE
                        );
                    }
                });
                builder.setNegativeButton("Cancelar", null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            } else {
                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                        }, REQUEST_CODE
                );
            }
        }
    }
}