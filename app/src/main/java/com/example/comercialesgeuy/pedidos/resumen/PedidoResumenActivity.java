package com.example.comercialesgeuy.pedidos.resumen;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class PedidoResumenActivity extends AppCompatActivity {

    TextView txtComercialRP, txtPartnerRP;

    RecyclerView rcvProductosRP;
    List<Producto> productList;
    Button btnCancelar, btnConfirmar;
    EditText edtFechaPedido, edtFechaEnvio, edtFechaPago;
    String sEdtFechaPedido, sEdtFechaEnvio, sEdtFechaPago;
    private String date_time;
    EditText clickedEditText;
    public Partner partner;

    DBSQLite dbSQLite;
    SQLiteDatabase database;

    final Calendar myCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_resumen);

        rcvProductosRP = findViewById(R.id.rcvProductosRB);
        txtComercialRP = findViewById(R.id.txtcomercialPedido);
        txtPartnerRP = findViewById(R.id.txtPartnerPedido);
        btnCancelar = findViewById(R.id.btnCancelarLP);
        btnConfirmar = findViewById(R.id.btnEliminarPedido);
        edtFechaPedido = findViewById(R.id.edtFechaPedido);
        edtFechaEnvio = findViewById(R.id.edtFechaEnvio);
        edtFechaPago = findViewById(R.id.edtFechaPago);

        dbSQLite = new DBSQLite(this);
        database = dbSQLite.getWritableDatabase();

        Bundle extras = getIntent().getExtras();
        partner = (Partner) getIntent().getSerializableExtra("partner");
        productList = (ArrayList<Producto>) extras.getSerializable("arrayProductosPedido");

        txtComercialRP.setText(((MyAppVariables) this.getApplication()).getComercialNombre());
        txtPartnerRP.setText(partner.getNombre() + " " + partner.getApellidos());

        listaProductosOn();


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                update();
            }
        };

        edtFechaPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedEditText = (EditText) v;
                new DatePickerDialog(PedidoResumenActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        edtFechaEnvio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedEditText = (EditText) v;
                new DatePickerDialog(PedidoResumenActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        edtFechaPago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedEditText = (EditText) v;
                new DatePickerDialog(PedidoResumenActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnConfirmar.setOnClickListener(v -> {
            realizarPedido();
        });

        btnCancelar.setOnClickListener(v -> finish());
    }

    private void update() {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        //clickedEditText.setText(sdf.format(myCalendar.getTime()));

        try {
            Date parsed = myCalendar.getTime();
            Date now = Calendar.getInstance().getTime();
            int retVal = parsed.compareTo(now);

            if(retVal >= 0){
                clickedEditText.setText(sdf.format(myCalendar.getTime()));
            } else {
                Toast.makeText(this, "Fecha no puede ser retrasada", Toast.LENGTH_SHORT).show();
                clickedEditText.requestFocus();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void realizarPedido() {
        if(recogerDatos()){
            escribirBD();
            atras();
        }
    }

    private boolean recogerDatos() {
        boolean datos = true;

        if (edtFechaPedido.getText().length() > 0) {
            sEdtFechaPedido = edtFechaPedido.getText().toString();
        } else {
            datos = false;
            edtFechaPedido.requestFocus();
            Toast.makeText(this, "Fecha de pedido no puede ser vacia.", Toast.LENGTH_SHORT).show();
        }

        if (edtFechaEnvio.getText().length() > 0) {
            sEdtFechaEnvio = edtFechaEnvio.getText().toString();
        } else {
            datos = false;
            edtFechaEnvio.requestFocus();
            Toast.makeText(this, "Fecha de envio no puede ser vacia.", Toast.LENGTH_SHORT).show();
        }

        if (edtFechaPago.getText().length() > 0) {
            sEdtFechaPago = edtFechaPago.getText().toString();
        } else {
            datos = false;
            edtFechaEnvio.requestFocus();
            Toast.makeText(this, "Fecha de pago no puede ser vacia.", Toast.LENGTH_SHORT).show();
        }

        return datos;
    }

    private void escribirBD() {
        ArrayList<Producto> productOrder = recogerProductos();
        int comercId = ((MyAppVariables) this.getApplication()).getComercialId();
        dbSQLite.insertarPedido(productOrder, comercId, partner, sEdtFechaPedido, sEdtFechaEnvio, sEdtFechaPago);
    }

    private ArrayList<Producto> recogerProductos() {
        ArrayList<Producto> productOrders = new ArrayList<>();
        //productOrders.clear();
        int size = ((RecyclerAdapterPedidoResumen) Objects.requireNonNull(rcvProductosRP.getAdapter())).getItemCount();

        for (int i = 0; i < size; i++) {
            if (((RecyclerAdapterPedidoResumen) rcvProductosRP.getAdapter()).getItem(i).getCantidadPedida() > 0) {
                Producto producto = ((RecyclerAdapterPedidoResumen) rcvProductosRP.getAdapter()).getItem(i);
                productOrders.add(producto);
            }
        }

        return productOrders;
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
        rcvProductosRP.setLayoutManager(manager);

        RecyclerAdapterPedidoResumen adapter = new RecyclerAdapterPedidoResumen(productList);
        rcvProductosRP.setAdapter(adapter);
    }
}

