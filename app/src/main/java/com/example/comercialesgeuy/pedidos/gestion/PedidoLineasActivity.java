package com.example.comercialesgeuy.pedidos.gestion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comercialesgeuy.DBSQLite;
import com.example.comercialesgeuy.R;
import com.example.comercialesgeuy.partners.Partner;
import com.example.comercialesgeuy.partners.PartnerInfoActivity;
import com.example.comercialesgeuy.pedidos.Albaran;
import com.example.comercialesgeuy.pedidos.Linea;

import java.util.List;

public class PedidoLineasActivity extends AppCompatActivity{
    Button btnEliminar, btnAtras;
    TextView comercial,txtPartner, txtPedido;
    EditText fechaPedido, fechaEnvio, fechaPago;
    Partner partner;
    Albaran albaran;
    DBSQLite dbSQLite;
    SQLiteDatabase database;
    RecyclerView rvPed;
    List<Linea> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_resumen);

        rvPed = findViewById(R.id.rcvProductosRB);
        btnEliminar = findViewById(R.id.btnConfirmarLP);
        btnAtras = findViewById(R.id.btnCancelarLP);
        comercial = findViewById(R.id.txtcomercialPedido);
        txtPartner = findViewById(R.id.txtPartnerPedido);
        fechaEnvio = findViewById(R.id.edtFechaEnvio);
        fechaPago = findViewById(R.id.edtFechaPago);
        fechaPedido = findViewById(R.id.edtFechaPedido);
        txtPedido = findViewById(R.id.txtPedido);

        txtPedido.setText("Gestión del Pedido");
        btnEliminar.setText("Eliminar");

        albaran =(Albaran) getIntent().getSerializableExtra("albaran");

        dbSQLite = new DBSQLite(this);
        database = dbSQLite.getWritableDatabase();

        escribirDatos();
        listaProductosOn();

        btnAtras.setOnClickListener(v -> volverAtras());

        btnEliminar.setOnClickListener(v -> {
            eliminarPedido();
        });
    }

    private void eliminarPedido() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Atención!");
        builder.setMessage("Seguro que quieres borrar este pedido?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int ii) {
                dbSQLite.deleteLineasPorIdAlbaran(albaran.getId());
                dbSQLite.deletePedidosPorAlbaran(albaran);
                Toast.makeText(getApplicationContext(), "Se ha borrado el pedido con exito", Toast.LENGTH_SHORT).show();
                volverAtras();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int ii) {
                //
            }
        });
        builder.show();
    }

    private void escribirDatos() {
        partner = dbSQLite.leerPartner(albaran.getIdPartner());
        comercial.setText(dbSQLite.leerNombreComercial(albaran.getIdComerc()));
        txtPartner.setText(partner.getNombre());
        fechaPago.setText(albaran.getFechaPago());
        fechaPedido.setText(albaran.getFechaPedido());
        fechaEnvio.setText(albaran.getFechaEnvio());
    }

    public void volverAtras(){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result",1);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    private void listaProductosOn() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvPed.setLayoutManager(manager);
        lista = dbSQLite.leerLineas(albaran.getId());

        System.out.println("11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
        for(Linea lin : lista) {
            System.out.println(lin.toString());
        }

        RecyclerAdapterPedidoLineas adapter = new RecyclerAdapterPedidoLineas(lista);
        rvPed.setAdapter(adapter);
    }
}