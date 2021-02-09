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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comercialesgeuy.DBSQLite;
import com.example.comercialesgeuy.MyAppVariables;
import com.example.comercialesgeuy.R;
import com.example.comercialesgeuy.partners.Partner;

public class CalendarioModificacionActivity extends AppCompatActivity {

    private EditText txtCabecera, txtTexto;
    private TextView txtTituloVentana;
    private Button btnGuardar;
    private String fecha;
    Cita cita;

    DBSQLite dbSQLite;
    SQLiteDatabase database;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_new);

        btnGuardar = findViewById(R.id.btnGuardar);
        txtCabecera = findViewById(R.id.txtCabecera);
        txtTexto = findViewById(R.id.txtTexto);
        txtTituloVentana = findViewById(R.id.txtTituloVentana);

        dbSQLite = new DBSQLite(this);
        database = dbSQLite.getWritableDatabase();

        txtTituloVentana.setText("CAMBIAR CITA");
        txtCabecera.setHint("Nuevo titulo de cita");
        txtTexto.setHint("Nuevo información de cita");

        Bundle extras = getIntent().getExtras();
        fecha = extras.getString("fecha");
        cita = (Cita) getIntent().getSerializableExtra("cita");

        crearVentana();

        btnGuardar.setOnClickListener(v -> guardarNuevaCita1());
    }

    private void guardarNuevaCita1() {
        recogerDatos();

        int resp = dbSQLite.modificarCita(cita, fecha);

        if(resp > 0) {
            Toast.makeText(getApplicationContext(), "Ha modificado la cita", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(), "No se ha podido modificar", Toast.LENGTH_SHORT).show();
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
            cita.setCabecera(txtCabecera.getText().toString());
        } else {
            cita.setCabecera("Mi evento");
        }

        if (txtTexto.getText().length() > 0) {
            cita.setTexto(txtTexto.getText().toString());
        } else {
            cita.setTexto("");
        }
    }
}
