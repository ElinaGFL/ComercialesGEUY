package com.example.comercialesgeuy.pedidos.gestion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comercialesgeuy.DBSQLite;
import com.example.comercialesgeuy.MyAppVariables;
import com.example.comercialesgeuy.R;
import com.example.comercialesgeuy.partners.Partner;
import com.example.comercialesgeuy.pedidos.Producto;
import com.example.comercialesgeuy.pedidos.resumen.PedidoResumenActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PedidoGestionActivity extends AppCompatActivity {

    TextView txtComercialGP;
    Spinner spinnerPartner;
    RecyclerView rcvProductos;
    Button btnCancelar, btnConfirmar;
    String partnerData;
    Partner partner;
    ArrayList<Producto> productOrder;

    DBSQLite dbSQLite;
    SQLiteDatabase database;

    private final int LAUNCH_SECOND_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_gestion);

        spinnerPartner = findViewById(R.id.spinnerPartner);
        txtComercialGP = findViewById(R.id.txtComercialGP);
        btnCancelar = findViewById(R.id.btnCancelarGP);
        btnConfirmar = findViewById(R.id.btnConfirmarGP);
        rcvProductos = findViewById(R.id.rcvProductosGP);

        dbSQLite = new DBSQLite(this);
        database = dbSQLite.getWritableDatabase();

        spinnerPartnersOn();
        txtComercialOn();
        listaProductosOn();

        spinnerPartner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                partner = (Partner) parent.getSelectedItem();
                displayUserData(partner);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnConfirmar.setOnClickListener(v -> {
            productOrder = hacerPedido();
            if(productOrder.size() > 0) {
                if(spinnerPartner.getSelectedItem() != null) {
                    confirmarPedido(productOrder);
                }else {
                    Toast.makeText(this, "No hay partner", Toast.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(this, "El pedido está vacío", Toast.LENGTH_LONG).show();
            }

        });

        btnCancelar.setOnClickListener(v -> finish());
    }

    private void listaProductosOn() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rcvProductos.setLayoutManager(manager);

        List<Producto> productList = dbSQLite.leerProductos();
        RecyclerAdapterPedidoGestion adapter = new RecyclerAdapterPedidoGestion(productList);
        rcvProductos.setAdapter(adapter);
    }

    private void displayUserData(Partner partner) {
        String nombre = partner.getNombre();
        String apellidos = partner.getApellidos();
        partnerData = nombre + " " + apellidos;
        //Toast.makeText(this, userData, Toast.LENGTH_LONG).show();
    }

    //recogemos el nombre desde la clase MyAppVariables con todos variables globales
    private void txtComercialOn() {
        txtComercialGP.setText(((MyAppVariables) this.getApplication()).getComercialNombre());
    }

    private void spinnerPartnersOn() {
        int comercId = ((MyAppVariables) this.getApplication()).getComercialId();
        List<Partner> partnerList = dbSQLite.leerPartners(comercId);
        if(partnerList.size() > 0) {
            ArrayAdapter<Partner> adapter = new ArrayAdapter<>(this, R.layout.spinner_item_partner, partnerList);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerPartner.setAdapter(adapter);
        }else {
            Toast.makeText(this, "No hay partners", Toast.LENGTH_LONG).show();

        }

    }

    private ArrayList<Producto> hacerPedido() {
        ArrayList<Producto> productOrders = new ArrayList<>();
        //productOrders.clear();
        int size = ((RecyclerAdapterPedidoGestion) Objects.requireNonNull(rcvProductos.getAdapter())).getItemCount();

        for (int i = 0; i < size; i++) {
            if (((RecyclerAdapterPedidoGestion) rcvProductos.getAdapter()).getItem(i).getCantidadPedida() > 0) {
                Producto producto = ((RecyclerAdapterPedidoGestion) rcvProductos.getAdapter()).getItem(i);
                productOrders.add(producto);
            }
        }

        return productOrders;
    }

    private void confirmarPedido(ArrayList<Producto> productOrder) {
        Intent intent = new Intent(this, PedidoResumenActivity.class);
        intent.putExtra("partner", partner);
        intent.putExtra("arrayProductosPedido", productOrder);
        startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == LAUNCH_SECOND_ACTIVITY && resultCode == RESULT_OK){
            boolean res = Objects.requireNonNull(intent.getExtras()).getBoolean("Atras");
            if (res){
                finish();
            }
        }
    }
}
