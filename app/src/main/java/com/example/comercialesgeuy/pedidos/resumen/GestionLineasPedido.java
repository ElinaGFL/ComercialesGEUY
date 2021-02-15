package com.example.comercialesgeuy.pedidos.resumen;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.comercialesgeuy.DBSQLite;
import com.example.comercialesgeuy.MyAppVariables;
import com.example.comercialesgeuy.R;
import com.example.comercialesgeuy.partners.Partner;
import com.example.comercialesgeuy.pedidos.Albaran;

public class GestionLineasPedido extends AppCompatActivity {
    Button btnEliminar;
    Button btnAtras;
    TextView comercial,txtPartner,fechapedido,fechaEnvio,fechapago;
    Partner partner;
    Albaran alba;
    DBSQLite dbSQLite;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_lineas_pedido);
        Bundle extras = getIntent().getExtras();
        alba=(Albaran) getIntent().getSerializableExtra("albaran");

        btnEliminar=findViewById(R.id.btnEliminarPedido);
        btnAtras=findViewById(R.id.btnCancelarLP);
        comercial=findViewById(R.id.txtcomercialPedido);
        txtPartner=findViewById(R.id.txtcomercialPedido);
        fechaEnvio=findViewById(R.id.txtFechaEnvioResum);
        fechapago=findViewById(R.id.txtFechaPagoResum);
        fechapedido=findViewById(R.id.txtFechaPedidoResum);

        dbSQLite = new DBSQLite(this);
        database = dbSQLite.getWritableDatabase();



       // comercial.setText(alba.getIdComerc());
      //  txtPartner.setText(alba.getIdPartner());
        fechapago.setText(alba.getFechaPago());
        fechapedido.setText(alba.getFechaPedido());
        fechaEnvio.setText(alba.getFechaEnvio());


    }
}