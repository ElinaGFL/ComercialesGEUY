package com.example.comercialesgeuy.partners;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comercialesgeuy.DBSQLite;
import com.example.comercialesgeuy.R;
import com.example.comercialesgeuy.cita.CalendarioActivity;
import com.example.comercialesgeuy.cita.Cita;

import java.net.URI;

public class Contacto extends AppCompatActivity {

    Partner partner;
    DBSQLite dbSQLite;
    SQLiteDatabase database;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);

        TextView nom = (TextView)findViewById(R.id.nombrePartner);
        TextView ape = (TextView)findViewById(R.id.apellidoPartner);
        TextView pob = (TextView)findViewById(R.id.PoblacionPartner);
        TextView cif = (TextView)findViewById(R.id.cifPartner);
        ImageButton llamada = (ImageButton)findViewById(R.id.ibLlamada);
        ImageButton correo = (ImageButton)findViewById(R.id.ibCorreo);
        ImageButton edit = (ImageButton)findViewById(R.id.ibEdit);
        ImageButton delete = (ImageButton)findViewById(R.id.ibDelete);

        dbSQLite = new DBSQLite(this);
        database = dbSQLite.getWritableDatabase();

        Bundle extra = this.getIntent().getExtras();
        partner = (Partner) getIntent().getSerializableExtra("partner");

        nom.setText("Nombre:" + partner.getNombre());
        ape.setText("Apellidos:" + partner.getApellidos());
        pob.setText("Población:" + partner.getPoblacion());
        cif.setText("CIF:" + partner.getCif());

        llamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent llamar = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+partner.getTelefono()));

                startActivity(llamar);
            }
        });

        correo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",partner.getCorreo(), null));
                startActivity(email);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Contacto.this);
                builder.setTitle("Atención!");
                builder.setMessage("Seguro que quieres borrar este partner?");
                builder.setIcon(android.R.drawable.ic_delete);

                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int ii) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(Contacto.this);
                        builder.setTitle("Atención!");
                        builder.setMessage("Van a ser borrados todos los pedidos con el y no se puede recuperar");
                        builder.setIcon(android.R.drawable.ic_dialog_alert);

                        builder.setPositiveButton("Acepto", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int ii) {
                                int resp = dbSQLite.deletePartnerOnCascade(partner);

                                if(resp > 0) {
                                    Toast.makeText(getApplicationContext(), "Ha borrado el partner y todos sus datos", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else {
                                    Toast.makeText(getApplicationContext(), "No se ha podido borrar el partner", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        builder.setNegativeButton("Rechazo", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int ii) {
                                //
                            }
                        });
                        builder.show();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int ii) {
                        //
                    }
                });

                builder.show();
            }
        });
    }
}