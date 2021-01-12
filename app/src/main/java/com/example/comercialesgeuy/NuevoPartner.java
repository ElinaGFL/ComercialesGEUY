package com.example.comercialesgeuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class NuevoPartner extends AppCompatActivity {
    EditText nvNombre;
    EditText nvApellido;
    EditText nvTelefono;
    EditText nvCorreo;
    Button btnNuevoPartner;
    private File XMLfile;
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
                if (nvNombre.getText().length() <= 0) {
                    nvNombre.setError("No puedes dejar el Nombre del nuevo parten en vacio!");
                } else if (nvApellido.getText().length() <= 0) {
                    nvApellido.setError("No puedes dejar el Nombre del nuevo parten en vacio!");
                } else if (nvTelefono.getText().length() <= 0) {
                    nvTelefono.setError("No puedes dejar el Nombre del nuevo parten en vacio!");
                } else if (nvNombre.getText().length() <= 0) {
                    nvCorreo.setError("No puedes dejar el Nombre del nuevo parten en vacio!");
                } else {
                    try {
                        Toast toast1 =
                                Toast.makeText(getApplicationContext(),
                                        "Todavia no esta la funciona disponible.", Toast.LENGTH_SHORT);

                        toast1.show();

                    } catch (Exception ex) {
                        Log.e("msg", "No se pudo introducir el nuevo partner");
                    }
                }

                volverAtras();
            }

        });
    }


    public void volverAtras(){
        Intent i=new Intent(this, Layoutpartners.class);
        startActivity(i);
    }
}