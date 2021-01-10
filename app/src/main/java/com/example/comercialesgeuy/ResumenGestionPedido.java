package com.example.comercialesgeuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

public class ResumenGestionPedido extends AppCompatActivity {

    private Bundle bundle;
    private String partner;
    private String comercial;
    private String cant1;
    private String cant2;
    private String cant3;
    private String cant4;
    private TextView part, com, cantidad1, cantidad2, cantidad3, cantidad4, fech, pr1, pr2, pr3, pr4, tot;
    private int precio1, precio2, precio3, precio4, total = 0;
    private Button editar;
    private int fecha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen_gestion_pedido);

        bundle = getIntent().getExtras();
        part = (TextView) findViewById(R.id.tvPartner);
        com = (TextView) findViewById(R.id.tvComercial);
        fech = (TextView) findViewById(R.id.tvFecha);
        cantidad1 = (TextView) findViewById(R.id.tvCantidad1);
        cantidad2 = (TextView) findViewById(R.id.tvCantidad2);
        cantidad3 = (TextView) findViewById(R.id.tvCantidad3);
        cantidad4 = (TextView) findViewById(R.id.tvCantidad4);
        pr1 = (TextView) findViewById(R.id.tvPr1);
        pr2 = (TextView) findViewById(R.id.tvPr2);
        pr3 = (TextView) findViewById(R.id.tvPr3);
        pr4 = (TextView) findViewById(R.id.tvPr4);
        tot = (TextView) findViewById(R.id.textView9);


        editar = (Button) findViewById(R.id.btnEditar);

        partner = bundle.getString("partner");
        comercial = bundle.getString("comercial");
        cant1 = bundle.getString("Bat20");
        cant2 = bundle.getString("Bat60");
        cant3 = bundle.getString("BatSun");
        cant4 = bundle.getString("BatHaiz");

        part.setText("Partner:      " + partner);
        com.setText("Comercial:      " + comercial);
        cantidad1.setText(cant1);
        cantidad2.setText(cant2);
        cantidad3.setText(cant3);
        cantidad4.setText(cant4);

        int p1 = Integer.parseInt(cant1);
        precio1 = 7500 * p1;
        pr1.setText(precio1 + " $");

        int p2 = Integer.parseInt(cant2);
        precio2 = 7500 * p2;
        pr2.setText(precio2 + " $");

        int p3 = Integer.parseInt(cant3);
        precio3 = 7500 * p3;
        pr3.setText(precio3 + " $");

        int p4 = Integer.parseInt(cant4);
        precio4 = 7500 * p4;
        pr4.setText(precio4 + " $");

        total = precio1 + precio2 + precio3 + precio4;
        tot.setText(total + " $");

        String actual = DateFormat.getDateTimeInstance().format(new Date());

        fech.setText(actual);


        editar.setOnClickListener((View v) ->{
            onBackPressed();

        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}