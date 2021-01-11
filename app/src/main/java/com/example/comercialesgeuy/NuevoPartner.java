package com.example.comercialesgeuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class NuevoPartner extends AppCompatActivity {
    EditText nvNombre;
    EditText nvApellido;
    EditText nvTelefono;
    EditText nvCorreo;
    Button btnNuevoPartner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_partner);

        nvNombre=findViewById(R.id.txtNuevoNombre);
        nvApellido=findViewById(R.id.txtNuevoApellido);
        nvTelefono=findViewById(R.id.txtNuevoNumero);
        nvCorreo=findViewById(R.id.txtNuevoCorreo);

        btnNuevoPartner=findViewById(R.id.btnNuevoPartner);

        btnNuevoPartner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nvNombre.getText().length()<=0){
                    nvNombre.setError("No puedes dejar el Nombre del nuevo parten en vacio!");
                }else if(nvApellido.getText().length()<=0) {
                    nvApellido.setError("No puedes dejar el Nombre del nuevo parten en vacio!");
                } else if(nvTelefono.getText().length()<=0) {
                    nvTelefono.setError("No puedes dejar el Nombre del nuevo parten en vacio!");
                } else if(nvNombre.getText().length()<=0) {
                    nvCorreo.setError("No puedes dejar el Nombre del nuevo parten en vacio!");
                }else {
                    try {
                        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder builder = factory.newDocumentBuilder();

                        Document doc = builder.parse(getAssets().open("partners.xml"));
                        Element raiz = doc.getDocumentElement();
                        NodeList items = raiz.getElementsByTagName("partners");

                        Element partner = doc.createElement("partner");

                        partner.appendChild(raiz);

                        Element nombre = doc.createElement("nombre");
                        nombre.appendChild(doc.createTextNode((nvNombre.toString())));

                        Element apellido = doc.createElement("apellido");
                        apellido.appendChild(doc.createTextNode((nvNombre.toString())));

                        Element telefono = doc.createElement("telefono");
                        telefono.appendChild(doc.createTextNode((nvNombre.toString())));

                        Element correo = doc.createElement("correo");
                        correo.appendChild(doc.createTextNode((nvNombre.toString())));

                        items.item(0).appendChild(nombre);
                        items.item(1).appendChild(apellido);
                        items.item(2).appendChild(telefono);
                        items.item(3).appendChild(correo);


                    } catch (Exception ex) {

                    }
                }

                finish();


            }


        });
    }

    public void volverAtras(){
        Intent i=new Intent(this, Layoutpartners.class);
        startActivity(i);
    }
}