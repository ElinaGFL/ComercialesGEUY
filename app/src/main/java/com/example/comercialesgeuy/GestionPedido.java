package com.example.comercialesgeuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class GestionPedido extends AppCompatActivity {
    TextWatcher tt = null;


    Spinner partner;
    Spinner comercial;
    Button cancelar;
    Button confirmar;
    EditText cantid1;
    EditText cantid2;
    EditText cantid3;
    EditText cantid4;
    TextView pr1;
    TextView pr2;
    TextView pr3;
    TextView pr4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_pedido);

        partner = (Spinner) findViewById(R.id.spinner);
        comercial = (Spinner) findViewById(R.id.spinner2);
        cancelar = (Button) findViewById(R.id.bCancelar);
        confirmar = (Button) findViewById(R.id.bConfirmar);
        cantid1 = (EditText) findViewById(R.id.etBat20);
        cantid2 = (EditText) findViewById(R.id.etBat60);
        cantid3 = (EditText) findViewById(R.id.etBatSun);
        cantid4 = (EditText) findViewById(R.id.etBatHaiz);
        pr1 = (TextView) findViewById(R.id.tvPrecio);
        pr2 = (TextView) findViewById(R.id.tvPrecio2);
        pr3 = (TextView) findViewById(R.id.tvPrecio3);
        pr4 = (TextView) findViewById(R.id.tvPrecio4);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.sPartner, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(this, R.array.sComercial, android.R.layout.simple_spinner_item);

        partner.setAdapter(adapter);
        comercial.setAdapter(adapter2);

        cancelar.setOnClickListener((View v) ->{
            onBackPressed();

        });

        confirmar.setOnClickListener((View v) -> {
            if(validarDatos()) {
                Intent intent = new Intent(this, ResumenGestionPedido.class);
                intent.putExtra("partner", partner.getSelectedItem().toString());
                intent.putExtra("comercial", comercial.getSelectedItem().toString());
                intent.putExtra("Bat20", cantid1.getText().toString());
                intent.putExtra("Bat60", cantid2.getText().toString());
                intent.putExtra("BatSun", cantid3.getText().toString());
                intent.putExtra("BatHaiz", cantid4.getText().toString());

                startActivityForResult(intent, 1);

            }
        });



        cantid1.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                int total1=0;

                total1 = calcularTotal(cantid1);


                pr1.setText(total1 + " $");

            }
        });

        cantid2.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                int total2=0;

                total2 = calcularTotal(cantid2);

                pr2.setText(total2 + " $");

            }
        });

        cantid3.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                int total3=0;

                total3 = calcularTotal(cantid3);

                pr3.setText(total3 + " $");

            }
        });

        cantid4.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                int total4=0;

                total4 = calcularTotal(cantid4);

                pr4.setText(total4 + " $");

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
    public int calcularTotal(EditText t){
        String i = t.getText().toString();
        int total = 0;
        if(i.length() > 0) {
            int precio = 7500;

            String num = t.getText().toString();

            int cant = Integer.parseInt(num);
            total = precio * cant;

        }
        return total;

    }
    public boolean validarDatos() {
        boolean validado = false;
        boolean cant1 = false;
        boolean cant2 = false;
        boolean cant3 = false;
        boolean cant4 = false;



        cant1 = validarEt(cantid1);
        cant2 = validarEt(cantid2);
        cant3 = validarEt(cantid3);
        cant4 = validarEt(cantid4);


            if(cant1 && cant2 && cant3 && cant4) {
                validado = true;
            }

        return validado;
    }



    public boolean validarEt(EditText e){
        boolean validado = false;
        String val = e.getText().toString();
        if(val.length() <= 0) {
            e.setError("Este campo no puede estar vacio");
        }
        else {
            validado = true;
        }
        return validado;
    }
}