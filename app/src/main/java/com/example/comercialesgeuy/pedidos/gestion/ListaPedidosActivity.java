package com.example.comercialesgeuy.pedidos.gestion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.comercialesgeuy.DBSQLite;
import com.example.comercialesgeuy.MyAppVariables;
import com.example.comercialesgeuy.R;
import com.example.comercialesgeuy.partners.Partner;
import com.example.comercialesgeuy.partners.PartnerInfoActivity;
import com.example.comercialesgeuy.partners.PartnerNewActivity;
import com.example.comercialesgeuy.partners.RecyclerAdapter;
import com.example.comercialesgeuy.pedidos.Albaran;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ListaPedidosActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, RecyclerAdapterPedidoLista.RecyclerItemClick {

    private RecyclerView rcvPedido;
    private RecyclerAdapterPedidoLista adapter;
    private List<Albaran> pedidoList;
    private FloatingActionButton btnAddPedido;

    List<Albaran> lstPedido;
    DBSQLite dbSQLite;
    SQLiteDatabase database;

    int LAUNCH_SECOND_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedidos);

        btnAddPedido = findViewById(R.id.btnAddPedido);
        rcvPedido = findViewById(R.id.rvPedido);

        dbSQLite = new DBSQLite(this);
        database = dbSQLite.getWritableDatabase();

        initValues();

        btnAddPedido.setOnClickListener(v -> nuevoPedido());
    }

    private void nuevoPedido() {
        Intent intent = new Intent(this, PedidoGestionActivity.class);
        startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
    }

    //rellenar y mostrar pedido
    private void initValues(){
        rcvPedido.setAdapter(null);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rcvPedido.setLayoutManager(manager);

        pedidoList = leerPedido();
        adapter = new RecyclerAdapterPedidoLista(pedidoList, this);
        rcvPedido.setAdapter(adapter);
    }

    private List<Albaran> leerPedido() {
        lstPedido = new ArrayList<>();
        lstPedido = dbSQLite.leerPedido();
        return lstPedido;
    }

    public void itemClick(Albaran item) {
        //Intent intent = new Intent(this, PartnerInfoActivity.class);
        //intent.putExtra("partner", partner);
        //startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
    }

    //@Override
    //protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        //if (requestCode == LAUNCH_SECOND_ACTIVITY) {
        //    initValues();
        //}
    //}

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

}