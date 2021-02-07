package com.example.comercialesgeuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.text.TextUtils;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private Button btnEntrar;
    private EditText edtUsuario;
    private EditText edtContrasenna;

    DBSQLite dbSQLite;
    SQLiteDatabase database;

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
                        Bundle mBundle = new Bundle();
                        mBundle.putString("user", comercial.getEmail());
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtras(mBundle);
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this, "Welcome " + comercial.getNombre(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                        edtContrasenna.setText("");
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Empty Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean emptyValidation() {
        if (TextUtils.isEmpty(edtUsuario.getText().toString()) || TextUtils.isEmpty(edtContrasenna.getText().toString())) {
            return true;
        }else {
            return false;
        }
    }
}