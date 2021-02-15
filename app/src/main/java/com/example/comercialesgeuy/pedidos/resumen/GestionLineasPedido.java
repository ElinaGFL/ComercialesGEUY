package com.example.comercialesgeuy.pedidos.resumen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.comercialesgeuy.DBSQLite;
import com.example.comercialesgeuy.R;
import com.example.comercialesgeuy.partners.Partner;
import com.example.comercialesgeuy.pedidos.Albaran;
import com.example.comercialesgeuy.pedidos.Linea;
import com.example.comercialesgeuy.pedidos.Producto;
import com.example.comercialesgeuy.pedidos.gestion.ListaPedidosActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GestionLineasPedido extends AppCompatActivity{
    Button btnEliminar;
    Button btnAtras;
    TextView comercial,txtPartner,fechapedido,fechaEnvio,fechapago;
    Partner partner;
    Albaran alba;
    DBSQLite dbSQLite;
    SQLiteDatabase database;
    RecyclerView rvPed;
    List<Linea> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_lineas_pedido);
        Bundle extras = getIntent().getExtras();
        alba=(Albaran) getIntent().getSerializableExtra("albaran");

        rvPed=findViewById(R.id.rcvProductosRP);
        btnEliminar=findViewById(R.id.btnEliminarPedido);
        btnAtras=findViewById(R.id.btnCancelarLP);
        comercial=findViewById(R.id.txtcomercialPedido);
        txtPartner=findViewById(R.id.txtPartnerPedido);
        fechaEnvio=findViewById(R.id.txtFechaEnvioResum);
        fechapago=findViewById(R.id.txtFechaPagoResum);
        fechapedido=findViewById(R.id.txtFechaPedidoResum);

        dbSQLite = new DBSQLite(this);
        database = dbSQLite.getWritableDatabase();
        partner= dbSQLite.leerPartner(alba.getIdPartner());
        comercial.setText(dbSQLite.leerComercial(alba.getIdComerc()));
        txtPartner.setText(partner.getNombre());
        System.out.println(partner.getNombre());
        fechapago.setText(alba.getFechaPago());
        fechapedido.setText(alba.getFechaPedido());
        fechaEnvio.setText(alba.getFechaEnvio());

        listaProductosOn();


        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               volverAtras(v);
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbSQLite.deleteLineasOnCascade(alba.getId());
                dbSQLite.deletePedidosOnCascade(alba);
                volverAtras(v);
            }
        });

    }

    private ArrayList<Producto> recogerProductos() {
        ArrayList<Producto> productOrders = new ArrayList<>();
        //productOrders.clear();
        int size = ((RecyclerAdapterPedidoResumen) Objects.requireNonNull(rvPed.getAdapter())).getItemCount();

        for (int i = 0; i < size; i++) {
            if (((RecyclerAdapterPedidoResumen) rvPed.getAdapter()).getItem(i).getCantidadPedida() > 0) {
                Producto producto = ((RecyclerAdapterPedidoResumen) rvPed.getAdapter()).getItem(i);
                productOrders.add(producto);
            }
        }

        return productOrders;
    }
    public void volverAtras(View v){
        Intent i=new Intent(this, ListaPedidosActivity.class);
        startActivity(i);
    }
    private void listaProductosOn() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvPed.setLayoutManager(manager);
        lista=dbSQLite.leerLineas(alba.getId());
        for(Linea list: lista ){
            System.out.println(list.toString());
        }

        RecyclerAdapterLineasResumen adapter = new RecyclerAdapterLineasResumen(lista);
        rvPed.setAdapter(adapter);
    }
}