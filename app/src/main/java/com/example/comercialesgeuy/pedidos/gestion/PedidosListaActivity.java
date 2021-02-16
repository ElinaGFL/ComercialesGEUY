package com.example.comercialesgeuy.pedidos.gestion;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comercialesgeuy.DBSQLite;
import com.example.comercialesgeuy.MyAppVariables;
import com.example.comercialesgeuy.R;
import com.example.comercialesgeuy.pedidos.Albaran;
import com.example.comercialesgeuy.pedidos.Linea;
import com.example.comercialesgeuy.pedidos.nuevo.PedidoNuevoActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PedidosListaActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, RecyclerAdapterListaPedidos.RecyclerItemClick {

    private RecyclerView rcvPedido;
    private RecyclerAdapterListaPedidos adapter;
    private List<Albaran> pedidoList;
    private FloatingActionButton btnAddPedido;

    List<Albaran> lstPedido;
    DBSQLite dbSQLite;
    SQLiteDatabase database;

    int LAUNCH_SECOND_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_lista);

        btnAddPedido = findViewById(R.id.btnAddPedido);
        rcvPedido = findViewById(R.id.rvPedido);

        dbSQLite = new DBSQLite(this);
        database = dbSQLite.getWritableDatabase();

        initValues();

        btnAddPedido.setOnClickListener(v -> nuevoPedido());
    }

    private void nuevoPedido() {
        Intent intent = new Intent(this, PedidoNuevoActivity.class);
        startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
    }

    //rellenar y mostrar pedido
    private void initValues(){
        rcvPedido.setAdapter(null);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rcvPedido.setLayoutManager(manager);

        pedidoList = leerPedidos();

        System.out.println("11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
        for(Albaran lin : pedidoList) {
            System.out.println(lin.toString());
        }

        adapter = new RecyclerAdapterListaPedidos(pedidoList, this);
        rcvPedido.setAdapter(adapter);
    }

    private List<Albaran> leerPedidos() {
        lstPedido = new ArrayList<>();
        int comercId = ((MyAppVariables) this.getApplication()).getComercialId();

        lstPedido = dbSQLite.leerPedidos(comercId);
        return lstPedido;
    }

    public void itemClick(Albaran item) {
        Intent intent = new Intent(this, PedidoLineasActivity.class);
        intent.putExtra("albaran", item);
        startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == LAUNCH_SECOND_ACTIVITY && resultCode == RESULT_OK){
            boolean res = Objects.requireNonNull(intent.getExtras()).getBoolean("Atras");
            if (res){
                initValues();
            } else{
                initValues();
            }
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

}