package com.example.comercialesgeuy.pedidos.resumen;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comercialesgeuy.DBSQLite;
import com.example.comercialesgeuy.R;
import com.example.comercialesgeuy.pedidos.Producto;

import java.util.ArrayList;
import java.util.List;

public class PedidoResumenActivity extends AppCompatActivity {

    private Bundle bundle;
    RecyclerView rcvProductos;
    List<Producto> productList;

    DBSQLite dbSQLite;
    SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_resumen);

        rcvProductos = findViewById(R.id.rcvProductos1);

        bundle = getIntent().getExtras();


        listaProductosOn();
    }

    private void listaProductosOn() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rcvProductos.setLayoutManager(manager);

        productList = (ArrayList<Producto>) bundle.getSerializable("arrayProductosPedido");
        RecyclerAdapterPedidoResumen adapter = new RecyclerAdapterPedidoResumen(productList);
        rcvProductos.setAdapter(adapter);
    }
}

