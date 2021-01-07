package com.example.comercialesgeuy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EnvioDelegacion extends AppCompatActivity {

    Button enviar;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envio_delegacion);

        enviar=findViewById(R.id.button);
        enviar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String[] TO = {"garikpitz.m@gmail.com"}; //Direcciones email  a enviar.
                String[] CC = {"garikpitz.m@gmail.com"}; //Direcciones email con copia.

                Intent emailIntent = new Intent(Intent.ACTION_SEND);

                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_CC, CC);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Tu Asunto...");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "tuemail@email.com"); // * configurar email aquí!

                try {
                    startActivity(Intent.createChooser(emailIntent, "Enviar email."));
                    Log.i("EMAIL", "Enviando email...");
                }
                catch (android.content.ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "No encontrado nada", Toast.LENGTH_SHORT);
                }
            }

        });
    }
}