package com.example.comercialesgeuy.pedidos.resumen;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comercialesgeuy.DBSQLite;
import com.example.comercialesgeuy.MyAppVariables;
import com.example.comercialesgeuy.R;
import com.example.comercialesgeuy.partners.Partner;
import com.example.comercialesgeuy.pedidos.Producto;

import java.util.ArrayList;
import java.util.List;

public class PedidoResumenActivity extends AppCompatActivity {

    TextView txtComercialRP, txtPartnerRP;

    RecyclerView rcvProductos;
    List<Producto> productList;
    Button btnCancelar, btnConfirmar;
    EditText edtFechaPedido, edtFechaEnvio, edtFechaPago;

    DBSQLite dbSQLite;
    SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_resumen);

        rcvProductos = findViewById(R.id.rcvProductosRP);
        txtComercialRP = findViewById(R.id.txtComercialRP);
        txtPartnerRP = findViewById(R.id.txtPartnerRP);
        btnCancelar = findViewById(R.id.btnCancelarRP);
        btnConfirmar = findViewById(R.id.btnConfirmarRP);
        edtFechaPedido = findViewById(R.id.edtFechaPedido);
        edtFechaEnvio = findViewById(R.id.edtFechaEnvio);
        edtFechaPago = findViewById(R.id.edtFechaPago);

        Bundle extras = getIntent().getExtras();
        Partner partner = (Partner) getIntent().getSerializableExtra("partner");
        productList = (ArrayList<Producto>) extras.getSerializable("arrayProductosPedido");

        txtComercialRP.setText(((MyAppVariables) this.getApplication()).getComercNombre());
        txtPartnerRP.setText(partner.getNombre() + " " + partner.getApellidos());

        listaProductosOn();

        //btnConfirmar.setEnabled(false);

        btnConfirmar.setOnClickListener(v -> {
            realizarPedido();
            atras();
        });

        btnCancelar.setOnClickListener(v -> finish());
    }

    private void realizarPedido() {
        if(recogerDatos()){
            escribirBD();
        }
    }

    private boolean recogerDatos() {
        boolean datos = false;

        return datos;
    }

    private void escribirBD() {
    }

    private void atras() {
        Toast.makeText(getApplicationContext(), "Compra realizada", Toast.LENGTH_SHORT).show();
        //volver al menu inicial
        Intent intent = new Intent();
        intent.putExtra("Atras", true);
        setResult(RESULT_OK, intent);
        //cerrar la actividad
        finish();
    }

    private void listaProductosOn() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rcvProductos.setLayoutManager(manager);

        RecyclerAdapterPedidoResumen adapter = new RecyclerAdapterPedidoResumen(productList);
        rcvProductos.setAdapter(adapter);
    }
}

