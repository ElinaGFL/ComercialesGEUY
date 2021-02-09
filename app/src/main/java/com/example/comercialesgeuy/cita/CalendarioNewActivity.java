package com.example.comercialesgeuy.cita;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comercialesgeuy.DBSQLite;
import com.example.comercialesgeuy.MyAppVariables;
import com.example.comercialesgeuy.R;

public class CalendarioNewActivity extends AppCompatActivity {

    private EditText txtCabecera;
    private EditText txtTexto;
    private Button btnGuardar;
    private String fecha, titulo, texto;

    DBSQLite dbSQLite;
    SQLiteDatabase database;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_new);

        btnGuardar = findViewById(R.id.btnGuardar);
        txtCabecera = findViewById(R.id.txtCabecera);
        txtTexto = findViewById(R.id.txtTexto);

        dbSQLite = new DBSQLite(this);
        database = dbSQLite.getWritableDatabase();

        Bundle extras = getIntent().getExtras();
        fecha = extras.getString("fecha");

        crearVentana();

        btnGuardar.setOnClickListener(v -> guardarNuevaCita1());
    }

    private void guardarNuevaCita1() {
        recogerDatos();

        int comercId = ((MyAppVariables) this.getApplication()).getComercialId();

        if(comercId > 0) {
            dbSQLite.insertarCita(fecha, titulo, texto, comercId);
        }

        finalizar();
    }

    private void finalizar() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result",1);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    private void crearVentana() {
        //DisplayMetrics class se utiliza para obtener información diversa sobre la pantalla en Android
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        //obtenemos la altura y anchura de la pantalla del movil
        int width = dm.widthPixels;

        //los numeros definen el tamaño de la ventana
        getWindow().setLayout((int) (width * .8), WindowManager.LayoutParams.WRAP_CONTENT);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = 0;

        getWindow().setAttributes(params);
    }

    private void recogerDatos() {
        if (txtCabecera.getText().length() > 0) {
            titulo = txtCabecera.getText().toString();
        } else {
            titulo = "Mi evento";
        }

        if (txtTexto.getText().length() > 0) {
            texto = txtTexto.getText().toString();
        } else {
            texto = "";
        }
    }
}
