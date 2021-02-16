package com.example.comercialesgeuy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.comercialesgeuy.cita.CalendarioActivity;
import com.example.comercialesgeuy.partners.PartnerActivity;
import com.example.comercialesgeuy.pedidos.gestion.PedidosListaActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private Button btnCalendario, btnpartners, btnPed, btnEnvio, btnLogOut;
    private GoogleMap mMap;
    static final int REQUEST_CODE = 123;

    private DBSQLite dbsqLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCalendario = findViewById(R.id.btnCalend);
        btnpartners = findViewById(R.id.btnParner);
        btnPed = findViewById(R.id.btnPed);
        btnEnvio = findViewById(R.id.btnEnvio);
        btnLogOut = findViewById(R.id.btnLogOut);

        pidePermisos();

        iniciarBD();

        btnCalendario.setOnClickListener(v -> siguienteLayout(btnCalendario));

        btnpartners.setOnClickListener(v -> siguienteLayout(btnpartners));

        btnEnvio.setOnClickListener(v -> siguienteLayout(btnEnvio));

        btnPed.setOnClickListener(v -> siguienteLayout(btnPed));

        btnLogOut.setOnClickListener(v -> atras());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private void atras() {
        //volver al menu inicial
        Intent intent = new Intent();
        intent.putExtra("Atras", true);
        setResult(RESULT_OK, intent);
        //cerrar la actividad
        finish();
    }

    private void iniciarBD() {
        dbsqLite = new DBSQLite(this);
    }

    public void llamar(View v){
        Intent llamar = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + ((MyAppVariables) this.getApplication()).getComercial().getTelefono()));
        startActivity(llamar);
    }
    public void correo(View v){
        Intent email = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",((MyAppVariables) this.getApplication()).getComercial().getEmail(), null));
        startActivity(email);
    }

    public void siguienteLayout(View v){
        Intent i;
        Button boton = (Button)v;
        if(boton == btnCalendario) {
            i = new Intent(this, CalendarioActivity.class);
        } else if(boton == btnpartners) {
            i = new Intent(this, PartnerActivity.class);
        } else if(boton == btnEnvio) {
            i = new Intent(this, EnvioDelegacion.class);
        } else if (boton == btnPed){
            i = new Intent(this, PedidosListaActivity.class);
        }else {
            i = new Intent(this, MainActivity.class);
        }
        startActivity(i);
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(43.30472446500069, -2.0168812128018994), 15.0f));
        // Add a marker in Sydney and move the camera
        LatLng comercialGEYU = new LatLng(43.30472446500069, -2.0168812128018994);
        mMap.addMarker(new MarkerOptions().position(comercialGEYU).title("Comercial GEYU"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(comercialGEYU));
    }

    public void pidePermisos() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) + ContextCompat.checkSelfPermission
                (MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale
                    (MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Permitir:");
                builder.setMessage("Usar la memoria para leer y escribir los archivos:");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(
                                MainActivity.this,
                                new String[]{
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.READ_EXTERNAL_STORAGE
                                }, REQUEST_CODE
                        );
                    }
                });
                builder.setNegativeButton("Cancelar", null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            } else {
                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                        }, REQUEST_CODE
                );
            }
        }
    }
}