package com.example.comercialesgeuy.pedidos.gestion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

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
    String partnerData, empresa;
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
            confirmarPedido(productOrder);
        });

        btnCancelar.setOnClickListener(v -> finish());

        /*
        XMLfile = new File (Environment.getExternalStorageDirectory() + "/GEUY/productos.xml");
        listaProductosOn();

        spinnerPartnersOn();
        spinnerComercialOn();

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

    private void listaProductosOn() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rcvProductos.setLayoutManager(manager);

        List<Producto> productList = leerProductos();
        RecyclerAdapterPedidoGestion adapter = new RecyclerAdapterPedidoGestion(productList);
        rcvProductos.setAdapter(adapter);
    }

    private void displayUserData(Partner partner) {
        String nombre = partner.getNombre();
        String apellidos = partner.getApellidos();
        partnerData = nombre + " " + apellidos;
        //Toast.makeText(this, userData, Toast.LENGTH_LONG).show();
    }

    private List<Producto> leerProductos() {
        ArrayList<Producto> productos = new ArrayList<>();
        Producto producto;

        Cursor cursor = database.query(DBSQLite.TABLE_PRODUCTOS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {

            int idIndex = cursor.getColumnIndex(DBSQLite.PRODUCTOS_KEY_ID);
            int codigoIndex = cursor.getColumnIndex(DBSQLite.PRODUCTOS_KEY_CODIGO);
            int descripIndex = cursor.getColumnIndex(DBSQLite.PRODUCTOS_KEY_DESCRIPCION);
            int prventIndex = cursor.getColumnIndex(DBSQLite.PRODUCTOS_KEY_PRVENT);
            int existencIndex = cursor.getColumnIndex(DBSQLite.PRODUCTOS_KEY_EXISTENCIAS);
            int imgIndex = cursor.getColumnIndex(DBSQLite.PRODUCTOS_KEY_IMG);

            do {
                producto = new Producto();

                producto.setId(cursor.getInt(idIndex));
                producto.setCodigo(cursor.getString(codigoIndex));
                producto.setDescripcion(cursor.getString(descripIndex));
                producto.setPrvent(cursor.getInt(prventIndex));
                producto.setExistencias(cursor.getInt(existencIndex));
                producto.setImg(cursor.getString(imgIndex));
                productos.add(producto);
                /*
                Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                        ", fecha = " + cursor.getString(fechaIndex) +
                        ", cabecera = " + cursor.getString(cabeceraIndex) +
                        ", texto = " + cursor.getString(textoIndex));
                 */
            } while (cursor.moveToNext());
        } else {
            Log.d("mLog", "0 rows");
        }
        //освобождаем память, т.к. курсор уже не будет нигде использоваться
        cursor.close();

        return productos;
    }

    //recogemos el nombre desde la clase MyAppVariables con todos variables globales
    private void txtComercialOn() {
        txtComercialGP.setText(((MyAppVariables) this.getApplication()).getComercNombre());
    }

    private void spinnerPartnersOn() {
        List<Partner> partnerList = rellenarPartnerList();
        ArrayAdapter<Partner> adapter = new ArrayAdapter<>(this, R.layout.spinner_item_partner, partnerList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPartner.setAdapter(adapter);
    }

    private List<Partner> rellenarPartnerList() {
        List<Partner> partnerList  = new ArrayList<>();
        Partner partner;
        Cursor cursor = database.query(DBSQLite.TABLE_PARTNERS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBSQLite.PARTNERS_KEY_ID);
            int nombreIndex = cursor.getColumnIndex(DBSQLite.PARTNERS_KEY_NOMBRE);
            int apellidosIndex = cursor.getColumnIndex(DBSQLite.PARTNERS_KEY_APELLIDOS);
            int emailIndex = cursor.getColumnIndex(DBSQLite.PARTNERS_KEY_EMAIL);
            int tlfnIndex = cursor.getColumnIndex(DBSQLite.PARTNERS_KEY_TLFN);

            do {
                partner = new Partner();
                partner.setId(cursor.getInt(idIndex));
                partner.setNombre(cursor.getString(nombreIndex));
                partner.setApellidos(cursor.getString(apellidosIndex));
                partner.setCorreo(cursor.getString(emailIndex));
                partner.setTelefono(cursor.getString(tlfnIndex));
                partnerList.add(partner);
                /*
                Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                        ", fecha = " + cursor.getString(fechaIndex) +
                        ", cabecera = " + cursor.getString(cabeceraIndex) +
                        ", texto = " + cursor.getString(textoIndex));
                 */
            } while (cursor.moveToNext());
        } else {
            Log.d("mLog", "0 rows");
        }
        cursor.close();
        return partnerList;
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

        /*

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
        */

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

    /*
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

    private void listaProductosOn() {
        if(XMLfile.exists()){
            List<Producto> productos;
            XMLParserProducto parser = new XMLParserProducto();
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
}
