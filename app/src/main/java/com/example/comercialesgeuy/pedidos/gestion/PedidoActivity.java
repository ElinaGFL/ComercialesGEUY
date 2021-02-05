package com.example.comercialesgeuy.pedidos.gestion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.comercialesgeuy.R;
import com.example.comercialesgeuy.partners.Partner;
import com.example.comercialesgeuy.antiguo.XMLPullParserHandlerPartner;
import com.example.comercialesgeuy.pedidos.ListAdapter;
import com.example.comercialesgeuy.pedidos.resumen.PedidoResumenActivity;
import com.example.comercialesgeuy.pedidos.Producto;

import java.util.ArrayList;
import java.util.List;

public class PedidoActivity extends AppCompatActivity {

    Spinner spinnerPartner;
    Spinner spinnerComercial;
    Button btnCancelar;
    Button btnConfirmar;
    RecyclerView rcvProductos;
    String partnerData, comercialData;
    private ListAdapter listAdapter;
    ArrayList<Producto> productOrders = new ArrayList<>();
    ArrayList<Producto> productOrder;

    //File XMLfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_gestion);

        spinnerPartner = findViewById(R.id.spinnerPartner);
        spinnerComercial = findViewById(R.id.spinnerComercial);
        btnCancelar = findViewById(R.id.bCancelar);
        btnConfirmar = findViewById(R.id.bConfirmar);
        rcvProductos = findViewById(R.id.rcvProductos);

        //XMLfile = new File (Environment.getExternalStorageDirectory() + "/GEUY/productos.xml");
        //listaProductosOn();

        //spinnerPartnersOn();

        //spinnerComercialOn();

        /*
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

        spinnerComercial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                comercialData = (String) parent.getSelectedItem();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productOrder = hacerPedido();
                confirmarPedido();
            }
        });

         */
    }
    /*
    private ArrayList<Producto> hacerPedido() {
        productOrders.clear();
        for(int i = 0; i < listAdapter.listProductos.size(); i++) {
            if(listAdapter.listProductos.get(i).pedidos > 0) {
                Producto producto = new Producto(
                        listAdapter.listProductos.get(i).getCodigo(),
                        listAdapter.listProductos.get(i).getDescripcion(),
                        listAdapter.listProductos.get(i).getExistencias(),
                        listAdapter.listProductos.get(i).getPrecioUn(),
                        listAdapter.listProductos.get(i).pedidos
                );

                productOrders.add(producto);
            }
        }
        return productOrders;
    }

    private void spinnerComercialOn() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sComercial, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerComercial.setAdapter(adapter);
    }

    private void spinnerPartnersOn() {
        List<Partner> partners;

        XMLPullParserHandlerPartner parser = new XMLPullParserHandlerPartner();
        partners = parser.parseXML();

        ArrayAdapter<Partner> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, partners);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPartner.setAdapter(adapter);
    }

    private void displayUserData(Partner partner) {
        String nombre = partner.getNombre();
        String apellidos = partner.getApellidos();
        partnerData = nombre + " " + apellidos;
        //Toast.makeText(this, userData, Toast.LENGTH_LONG).show();
    }

    /*

    private void listaProductosOn() {
        if(XMLfile.exists()){
            List<Producto> productos;
            XMLPullParserHandlerProducto parser = new XMLPullParserHandlerProducto();
            productos = parser.parseXML();

            listAdapter = new ListAdapter(this, (ArrayList<Producto>) productos);
            lstProductos.setAdapter(listAdapter);
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

     */

    private void confirmarPedido() {
        Intent intent = new Intent(this, PedidoResumenActivity.class);
        intent.putExtra("partnerData", partnerData);
        intent.putExtra("comercialData", comercialData);
        intent.putExtra("arrayProductosPedido", productOrder);
        startActivity(intent);
    }

}