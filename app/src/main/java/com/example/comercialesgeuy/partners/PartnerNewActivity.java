package com.example.comercialesgeuy.partners;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comercialesgeuy.DBSQLite;
import com.example.comercialesgeuy.MyAppVariables;
import com.example.comercialesgeuy.R;

public class PartnerNewActivity extends AppCompatActivity {

    EditText txtNuevoNombre;
    EditText txtNuevoApellidos;
    EditText txtNuevoTelefono;
    EditText txtNuevoCorreo;
    EditText txtNuevoPoblacion;
    EditText txtNuevoCif;
    Button btnNuevoPartner;
    private String nombre, apellidos, telefono, correo, poblacion, cif;

    DBSQLite dbSQLite;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partners_new);

        txtNuevoNombre = findViewById(R.id.txtNuevoNombre);
        txtNuevoApellidos = findViewById(R.id.txtNuevoApellidos);
        txtNuevoTelefono = findViewById(R.id.txtNuevoTelefono);
        txtNuevoCorreo = findViewById(R.id.txtNuevoCorreo);
        txtNuevoPoblacion = findViewById(R.id.txtNuevoPoblacion);
        txtNuevoCif = findViewById(R.id.txtNuevoCIF);
        btnNuevoPartner=findViewById(R.id.btnNuevoPartner);

        dbSQLite = new DBSQLite(this);
        database = dbSQLite.getWritableDatabase();

        //XMLfile = new File (Environment.getExternalStorageDirectory() + "/GEUY/partners.xml");

        btnNuevoPartner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarPartner();
            }
        });
    }

    private void guardarPartner() {
        if(recogerDatos()){
            int comercId = ((MyAppVariables) this.getApplication()).getComercialId();
            if(comercId > 0) {
                dbSQLite.insertarPartner(nombre, apellidos, correo, telefono, poblacion, cif, comercId);
                Toast.makeText(this, "Se ha añadido el partner", Toast.LENGTH_SHORT).show();
            }
            finalizar();
        }
        //Toast.makeText(this, "Se ha añadido el partner", Toast.LENGTH_SHORT).show();
    }

    private void finalizar() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result",1);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    private boolean recogerDatos() {
        boolean relleno = true;

        if (txtNuevoNombre.getText().length() > 0) {
            nombre = txtNuevoNombre.getText().toString();
        } else {
            relleno = false;
            txtNuevoNombre.requestFocus();
            Toast toast = Toast.makeText(getApplicationContext(), "Nombre no puede ser vacio.", Toast.LENGTH_SHORT);
            toast.show();
        }

        if (txtNuevoApellidos.getText().length() > 0) {
            apellidos = txtNuevoApellidos.getText().toString();
        } else {
            relleno = false;
            txtNuevoApellidos.requestFocus();
            Toast toast = Toast.makeText(getApplicationContext(), "Apellido no puede ser vacio.", Toast.LENGTH_SHORT);
            toast.show();
        }

        if (txtNuevoTelefono.getText().length() > 0) {
            telefono = txtNuevoTelefono.getText().toString();
        } else {
            relleno = false;
            txtNuevoTelefono.requestFocus();
            Toast toast = Toast.makeText(getApplicationContext(), "Telefono no puede ser vacio.", Toast.LENGTH_SHORT);
            toast.show();
        }

        if (txtNuevoCorreo.getText().length() > 0) {
            correo = txtNuevoCorreo.getText().toString();
        } else {
            relleno = false;
            txtNuevoCorreo.requestFocus();
            Toast toast = Toast.makeText(getApplicationContext(), "Correo no puede ser vacio.", Toast.LENGTH_SHORT);
            toast.show();
        }

        if (txtNuevoPoblacion.getText().length() > 0) {
            poblacion = txtNuevoPoblacion.getText().toString();
        } else {
            relleno = false;
            txtNuevoPoblacion.requestFocus();
            Toast toast = Toast.makeText(getApplicationContext(), "Poblacion no puede ser vacio.", Toast.LENGTH_SHORT);
            toast.show();
        }

        if (txtNuevoCif.getText().length() > 0) {
            cif = txtNuevoCif.getText().toString();
        } else {
            relleno = false;
            txtNuevoCif.requestFocus();
            Toast toast = Toast.makeText(getApplicationContext(), "CIF no puede ser vacio.", Toast.LENGTH_SHORT);
            toast.show();
        }

        return relleno;

    }
}