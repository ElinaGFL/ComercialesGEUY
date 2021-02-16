package com.example.comercialesgeuy;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private Button btnEntrar;
    private EditText edtUsuario;
    private EditText edtContrasenna;

    private DBSQLite dbSQLite;
    private SQLiteDatabase database;

    private final int LAUNCH_SECOND_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnEntrar = findViewById(R.id.btnEntrar);
        edtUsuario = findViewById(R.id.edtUsuario);
        edtContrasenna = findViewById(R.id.edtContrasenna);

        //creamos el objeto de la clase DBSQLite
        dbSQLite = new DBSQLite(this);
        database = dbSQLite.getWritableDatabase();

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!emptyValidation()) {
                    Comercial comercial = dbSQLite.queryUsuario(edtUsuario.getText().toString(), edtContrasenna.getText().toString());
                    if (comercial != null) {
                        ((MyAppVariables) LoginActivity.this.getApplication()).setComercial(comercial);
                        confirmarUsuario(comercial);
                    } else {
                        Toast.makeText(LoginActivity.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                        edtContrasenna.setText("");
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Campos vacíos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void confirmarUsuario(Comercial comercial) {
        Toast.makeText(LoginActivity.this, "Bienvenido " + comercial.getNombre(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == LAUNCH_SECOND_ACTIVITY && resultCode == RESULT_OK){
            boolean res = Objects.requireNonNull(intent.getExtras()).getBoolean("Atras");
            if (res){
                Toast.makeText(LoginActivity.this, "Usted ha cerrado la sesión", Toast.LENGTH_SHORT).show();
                edtContrasenna.setText("");
            }
        }
    }

    private boolean emptyValidation() {
        if (TextUtils.isEmpty(edtUsuario.getText().toString()) || TextUtils.isEmpty(edtContrasenna.getText().toString())) {
            return true;
        }else {
            return false;
        }
    }
}