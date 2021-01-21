package com.example.comercialesgeuy.pedidos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.comercialesgeuy.R;
import com.example.comercialesgeuy.cita.CalendarioNewActivity;
import com.example.comercialesgeuy.partners.Partner;
import com.example.comercialesgeuy.partners.XMLPullParserHandlerPartner;
import com.example.comercialesgeuy.productos.Producto;
import com.example.comercialesgeuy.productos.XMLPullParserHandlerProducto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

public class PedidoActivity extends AppCompatActivity {

    Spinner spinnerPartner;
    Button cancelar;
    Button confirmar;
    ListView lstProductos;
    File XMLfile;
    String partnerData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_pedido);

        spinnerPartner = findViewById(R.id.spinnerPartner);
        cancelar = findViewById(R.id.bCancelar);
        confirmar = findViewById(R.id.bConfirmar);
        lstProductos = findViewById(R.id.lstProductos);

        XMLfile = new File (Environment.getExternalStorageDirectory() + "/GEUY/productos.xml");

        listaProductosOn();

        spinnerPartnersOn();


    }

    private void spinnerPartnersOn() {
        List<Partner> partners;

        XMLPullParserHandlerPartner parser = new XMLPullParserHandlerPartner();
        partners = parser.parseXML();

        ArrayAdapter<Partner> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, partners);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPartner.setAdapter(adapter);
        spinnerPartner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Partner partner = (Partner) parent.getSelectedItem();
                displayUserData(partner);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void displayUserData(Partner partner) {
        String nombre = partner.getNombre();
        String apellidos = partner.getApellidos();
        partnerData = nombre + " " + apellidos;
        //Toast.makeText(this, userData, Toast.LENGTH_LONG).show();
    }

    private void listaProductosOn() {
        if(XMLfile.exists()){
            List<Producto> productos;
            XMLPullParserHandlerProducto parser = new XMLPullParserHandlerProducto();
            productos = parser.parseXML();
            ArrayAdapter<Producto> adapter = new ArrayAdapter<> (this,android.R.layout.simple_list_item_1, productos);
            lstProductos.setAdapter(adapter);
        } else{
            XMLfile.getParentFile().mkdirs();
            try{
                if(XMLfile.createNewFile()){
                    InputStream in = getAssets().open("productos.xml");
                    File file = new File(Environment.getExternalStorageDirectory() + "/GEUY/productos.xml");
                    FileOutputStream fileOutput = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    int bufferLength = 0;
                    while((bufferLength = in.read(buffer)) > 0) {
                        fileOutput.write(buffer, 0, bufferLength);
                    }
                    fileOutput.close();
                }
            } catch (Exception e) {

            }
            listaProductosOn();
        }
    }

    private void confirmarPedido() {
        Intent intent = new Intent(this, PedidoResumenActivity.class);
        intent.putExtra("partnerData", partnerData);
        startActivity(intent);
    }

}