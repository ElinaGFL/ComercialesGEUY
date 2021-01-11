package com.example.comercialesgeuy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.comercialesgeuy.adapter.RecyclerAdapter;
import com.example.comercialesgeuy.model.DatosPartners;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Layoutpartners extends AppCompatActivity implements RecyclerAdapter.RecyclerItemClick, SearchView.OnQueryTextListener {
    private RecyclerView rvlista;
    private RecyclerAdapter adapter;
    private List<DatosPartners> items;
    private FloatingActionButton btnaddPartner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partners);
        btnaddPartner=findViewById(R.id.btnaddPartner);

        initViews();
        initValues();
        btnaddPartner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              nuevoPartner();

            }
        });

    }

    private void nuevoPartner() {
        Intent i=new Intent(this, NuevoPartner.class);
        startActivity(i);
    }


    private void initViews(){
        rvlista=findViewById(R.id.cvpartners);
    }

    private void initValues(){
        LinearLayoutManager manager=new LinearLayoutManager(this);
        rvlista.setLayoutManager(manager);

        items = leerPartners();
        adapter = new RecyclerAdapter(items, this);
        rvlista.setAdapter(adapter);
    }

    public ArrayList<DatosPartners> leerPartners(){
        ArrayList<DatosPartners> listado = new ArrayList<DatosPartners>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(getAssets().open("partner.xml"));
            Element raiz = doc.getDocumentElement();
            NodeList items = raiz.getElementsByTagName("partner");

            for( int i = 0; i < items.getLength(); i++ ) {
                Node nodoCliente = items.item(i);
                DatosPartners partn = new DatosPartners();

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

    public void itemClick(DatosPartners item) {
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

