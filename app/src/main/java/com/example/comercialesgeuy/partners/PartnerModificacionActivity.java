package com.example.comercialesgeuy.partners;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comercialesgeuy.DBSQLite;
import com.example.comercialesgeuy.MyAppVariables;
import com.example.comercialesgeuy.R;
import com.example.comercialesgeuy.cita.Cita;

public class PartnerModificacionActivity extends AppCompatActivity {

    EditText txtNuevoNombre, txtNuevoApellidos, txtNuevoTelefono, txtNuevoCorreo, txtNuevoPoblacion, txtNuevoCif;
    private String nombre, apellidos, telefono, correo, poblacion, cif;
    TextView txtCabeceraPartner;
    Button btnNuevoPartner;
    Partner partner;

    DBSQLite dbSQLite;
    SQLiteDatabase database;

    @SuppressLint("SetTextI18n")
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
        txtCabeceraPartner = findViewById(R.id.txtCabeceraPartner);

        txtCabeceraPartner.setText("Cambiar Partner");
        btnNuevoPartner.setText("Modificar");

        dbSQLite = new DBSQLite(this);
        database = dbSQLite.getWritableDatabase();

        Bundle extras = getIntent().getExtras();
        partner = (Partner) getIntent().getSerializableExtra("partner");

        //XMLfile = new File (Environment.getExternalStorageDirectory() + "/GEUY/partners.xml");

        btnNuevoPartner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarNuevoPartner1();
            }
        });
    }

    private void guardarNuevoPartner1() {
        if(recogerDatos()){
            int comercId = ((MyAppVariables) this.getApplication()).getComercialId();
            
            if(comercId > 0) {
                dbSQLite.modificarPartner(partner);
                Toast.makeText(this, "Se ha modificado el partner", Toast.LENGTH_SHORT).show();
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
            partner.setNombre(nombre);
        } else {
            relleno = false;
            txtNuevoNombre.requestFocus();
            Toast toast = Toast.makeText(getApplicationContext(), "Nombre no puede ser vacio.", Toast.LENGTH_SHORT);
            toast.show();
        }

        if (txtNuevoApellidos.getText().length() > 0) {
            apellidos = txtNuevoApellidos.getText().toString();
            partner.setApellidos(apellidos);
        } else {
            relleno = false;
            txtNuevoApellidos.requestFocus();
            Toast toast = Toast.makeText(getApplicationContext(), "Apellido no puede ser vacio.", Toast.LENGTH_SHORT);
            toast.show();
        }

        if (txtNuevoTelefono.getText().length() > 0) {
            telefono = txtNuevoTelefono.getText().toString();
            partner.setTelefono(telefono);
        } else {
            relleno = false;
            txtNuevoTelefono.requestFocus();
            Toast toast = Toast.makeText(getApplicationContext(), "Telefono no puede ser vacio.", Toast.LENGTH_SHORT);
            toast.show();
        }

        if (txtNuevoCorreo.getText().length() > 0) {
            correo = txtNuevoCorreo.getText().toString();
            partner.setCorreo(correo);
        } else {
            relleno = false;
            txtNuevoCorreo.requestFocus();
            Toast toast = Toast.makeText(getApplicationContext(), "Correo no puede ser vacio.", Toast.LENGTH_SHORT);
            toast.show();
        }

        if (txtNuevoPoblacion.getText().length() > 0) {
            poblacion = txtNuevoNombre.getText().toString();
            partner.setPoblacion(poblacion);
        } else {
            relleno = false;
            txtNuevoPoblacion.requestFocus();
            Toast toast = Toast.makeText(getApplicationContext(), "Poblacion no puede ser vacio.", Toast.LENGTH_SHORT);
            toast.show();
        }

        if (txtNuevoCif.getText().length() > 0) {
            cif = txtNuevoCif.getText().toString();
            partner.setCif(cif);
        } else {
            relleno = false;
            txtNuevoCif.requestFocus();
            Toast toast = Toast.makeText(getApplicationContext(), "CIF no puede ser vacio.", Toast.LENGTH_SHORT);
            toast.show();
        }

        return relleno;

    }
}