package com.example.comercialesgeuy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.comercialesgeuy.adapter.RecyclerAdapter;
import com.example.comercialesgeuy.model.DatosPartners;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Layoutpartners extends AppCompatActivity {
    private RecyclerView rvlista;
    private RecyclerAdapter adapter;
    private List<DatosPartners> items;
    private LinearLayout popup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partners);


        initViews();
        initValues();



    }

    private void initViews(){
        rvlista=findViewById(R.id.cvpartners);

    }



    private void initValues(){
        LinearLayoutManager manager=new LinearLayoutManager(this);
        rvlista.setLayoutManager(manager);

        items = leerPartners();
        adapter = new RecyclerAdapter(items);
        rvlista.setAdapter(adapter);

        rvlista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup = (LinearLayout)findViewById(R.id.layoutLlama);
                popup.setVisibility(popup.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            }
        });
    }


    public ArrayList<DatosPartners> leerPartners(){
        ArrayList<DatosPartners> listado = new ArrayList<DatosPartners>();


        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse(getResources().openRawResource(R.raw.partner));

            Element raiz = doc.getDocumentElement();

            NodeList items = raiz.getElementsByTagName("partners");


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

    public void mandar(View v){
        ArrayList<DatosPartners> listado = new ArrayList<DatosPartners>();


        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse(getResources().openRawResource(R.raw.partner));

            Element raiz = doc.getDocumentElement();

            NodeList items = raiz.getElementsByTagName("partners");


            for( int i = 0; i < items.getLength(); i++ ) {
                Node nodoCliente = items.item(i);
                DatosPartners partn = new DatosPartners();

                if (nodoCliente.getNodeType() == Node.ELEMENT_NODE) {

                    Element partner1 = (Element) nodoCliente;


                    String correo = partner1.getElementsByTagName("correo").item(0).getTextContent();
                    partn.setCorreo(correo);


                    listado.add(partn);
                }
            }
            String correo;
            correo=listado.get(0).getCorreo();
            Log.e("msg", ""+correo);

            final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("plain/text");
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{correo});

            startActivity(emailIntent);


        } catch (Exception ex) {

        }

    }
    public void llamar(View v){
        ArrayList<DatosPartners> listado = new ArrayList<DatosPartners>();


        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse(getResources().openRawResource(R.raw.partner));

            Element raiz = doc.getDocumentElement();

            NodeList items = raiz.getElementsByTagName("partners");


            for( int i = 0; i < items.getLength(); i++ ) {
                Node nodoCliente = items.item(i);
                DatosPartners partn = new DatosPartners();

                if (nodoCliente.getNodeType() == Node.ELEMENT_NODE) {

                    Element partner1 = (Element) nodoCliente;


                    String telefono = partner1.getElementsByTagName("telefono").item(0).getTextContent();
                    partn.setTelefono(telefono);


                    listado.add(partn);
                }
            }
            Log.e("msg", ""+listado.get(0).getTelefono());
            Intent llamar=new Intent(Intent.ACTION_CALL);
            llamar.setData(Uri.parse(listado.get(0).getTelefono()));
            startActivity(llamar);


        } catch (Exception ex) {

        }

    }



}

