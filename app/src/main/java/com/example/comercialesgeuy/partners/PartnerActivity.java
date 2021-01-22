package com.example.comercialesgeuy.partners;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.comercialesgeuy.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class PartnerActivity extends AppCompatActivity implements RecyclerAdapter.RecyclerItemClick, SearchView.OnQueryTextListener {

    private RecyclerView rvPartners;
    private RecyclerAdapter adapter;
    private List<Partner> partnerList;
    private FloatingActionButton btnAddPartner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partners);

        btnAddPartner = findViewById(R.id.btnAddPartner);
        rvPartners = findViewById(R.id.rvPartners);

        initValues();

        btnAddPartner.setOnClickListener(v -> nuevoPartner());
    }

    private void nuevoPartner() {
        Intent i = new Intent(this, PartnerNewActivity.class);
        startActivity(i);
    }

    //rellenar y mostrar partner
    private void initValues(){
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvPartners.setLayoutManager(manager);

        partnerList = leerPartners();
        adapter = new RecyclerAdapter(partnerList, this);
        rvPartners.setAdapter(adapter);
    }

    private List<Partner> leerPartners() {
        List<Partner> partners;

        XMLPullParserHandlerPartner parser = new XMLPullParserHandlerPartner();
        partners = parser.parseXML();

        return partners;
    }

    /*
    public ArrayList<Partner> leerPartners(){

        ArrayList<Partner> listado = new ArrayList<Partner>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(getAssets().open("partner.xml"));
            Element raiz = doc.getDocumentElement();
            NodeList items = raiz.getElementsByTagName("partner");

            for( int i = 0; i < items.getLength(); i++ ) {
                Node nodoCliente = items.item(i);
                Partner partn = new Partner();

                if (nodoCliente.getNodeType() == Node.ELEMENT_NODE) {
                    Element partner1 = (Element) nodoCliente;

                    String nombre = partner1.getElementsByTagName("nombre").item(0).getTextContent();
                    partn.setNombre(nombre);
                    String apellidos = partner1.getElementsByTagName("apellido").item(0).getTextContent();
                    partn.setApellidos(apellidos);

                    String telefono = partner1.getElementsByTagName("telefono").item(0).getTextContent();
                    partn.setTelefono(telefono);
                    String correo = partner1.getElementsByTagName("correo").item(0).getTextContent();
                    partn.setCorreo(correo);

                    listado.add(partn);
                }
            }
        } catch (Exception ex) {

        }
        return listado;
    }
     */

    public void itemClick(Partner item) {
        Intent intent = new Intent(this, Contacto.class);
        intent.putExtra("nombre",item.getNombre());
        intent.putExtra("apellido",item.getApellidos());
        intent.putExtra("correo",item.getCorreo());
        intent.putExtra("telefono",item.getTelefono());
        startActivity(intent);
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

