package com.example.comercialesgeuy.partners;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comercialesgeuy.DBSQLite;
import com.example.comercialesgeuy.R;

public class PartnerInfoActivity extends AppCompatActivity {

    private Partner partner;
    private DBSQLite dbSQLite;
    private SQLiteDatabase database;
    final private int LAUNCH_SECOND_ACTIVITY = 1;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partners_info);

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

        nom.setText("Nombre: " + partner.getNombre());
        ape.setText("Apellidos: " + partner.getApellidos());
        pob.setText("Población: " + partner.getPoblacion());
        cif.setText("CIF: " + partner.getCif());

        llamada.setOnClickListener(v -> {
            Intent llamar = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + partner.getTelefono()));
            startActivity(llamar);
        });

        correo.setOnClickListener(v -> {
            Intent email = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", partner.getCorreo(), null));
            startActivity(email);
        });

        edit.setOnClickListener(v -> cambiarPartner());

        delete.setOnClickListener(v -> borrarPartner());
    }

    private void borrarPartner() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PartnerInfoActivity.this);
        builder.setTitle("Atención!");
        builder.setMessage("Seguro que quieres borrar este partner?");
        builder.setIcon(android.R.drawable.ic_delete);

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int ii) {

                int resp = dbSQLite.deletePartner(partner);

                if(resp > 0) {
                    Toast.makeText(getApplicationContext(), "Ha borrado el partner con exito", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "No se ha podido borrar el partner", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.show();
    }

    private void cambiarPartner() {
        Intent intent = new Intent(this, PartnerModificacionActivity.class);
        intent.putExtra("partner", partner);
        startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){
                finish();
            }
        /*
        if (resultCode == Activity.RESULT_CANCELED) {
            //Write your code if there's no result
        }
         */
        }
    }

}