package com.example.comercialesgeuy.partners;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.comercialesgeuy.R;

import java.net.URI;

public class Contacto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);

        TextView nom = (TextView)findViewById(R.id.nombrePartner);
        TextView ape = (TextView)findViewById(R.id.apellidoPartner);
        ImageButton llamada = (ImageButton)findViewById(R.id.ibLlamada);
        ImageButton correo = (ImageButton)findViewById(R.id.ibCorreo);

        Bundle extra = this.getIntent().getExtras();
        nom.setText(extra.getString("nombre"));
        ape.setText(extra.getString("apellido"));

        llamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent llamar = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+extra.getString("telefono")));

                startActivity(llamar);
            }
        });

        correo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",extra.getString("correo"), null));
                startActivity(email);
            }
        });
    }
}