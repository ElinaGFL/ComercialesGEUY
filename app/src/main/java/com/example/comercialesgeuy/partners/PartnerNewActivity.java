package com.example.comercialesgeuy.partners;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.comercialesgeuy.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class PartnerNewActivity extends AppCompatActivity {

    EditText txtNuevoNombre;
    EditText txtNuevoApellidos;
    EditText txtNuevoTelefono;
    EditText txtNuevoCorreo;
    Button btnNuevoPartner;
    private String nombre, apellidos, telefono, correo;
    private File XMLfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_partner);

        txtNuevoNombre = findViewById(R.id.txtNuevoNombre);
        txtNuevoApellidos = findViewById(R.id.txtNuevoApellidos);
        txtNuevoTelefono = findViewById(R.id.txtNuevoTelefono);
        txtNuevoCorreo = findViewById(R.id.txtNuevoCorreo);
        btnNuevoPartner=findViewById(R.id.btnNuevoPartner);

        XMLfile = new File (Environment.getExternalStorageDirectory() + "/GEUY/partners.xml");

        btnNuevoPartner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
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
                 */
                guardarNuevoPartner();
            }
        });
    }

    private void guardarNuevoPartner() {

        recogerDatos();

        if(XMLfile.exists()){

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder;
            try {
                dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(XMLfile);
                doc.getDocumentElement().normalize();
                addElement(doc, nombre, apellidos, telefono, correo);
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(XMLfile);
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.transform(source, result);
                Toast.makeText(this, "Se ha añadido el partner al XML", Toast.LENGTH_SHORT).show();
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else{

            XMLfile.getParentFile().mkdirs();
            try{
                if(XMLfile.createNewFile()){
                    FileOutputStream fos = new FileOutputStream(XMLfile);
                    XmlSerializer serializer = Xml.newSerializer();
                    serializer.setOutput(fos, "UTF-8");
                    serializer.startDocument("UTF-8", true);
                    serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                    serializer.startTag("", "partners");

                    serializer.startTag("", "partner");

                    serializer.startTag("", "nombre");
                    serializer.text(nombre);
                    serializer.endTag("", "nombre");

                    serializer.startTag("", "apellidos");
                    serializer.text(apellidos);
                    serializer.endTag("", "apellidos");

                    serializer.startTag("", "telefono");
                    serializer.text(telefono);
                    serializer.endTag("", "telefono");

                    serializer.startTag("", "correo");
                    serializer.text(correo);
                    serializer.endTag("", "correo");

                    serializer.endTag("", "partner");

                    serializer.endTag("", "partners");
                    serializer.endDocument();
                    serializer.endDocument();
                    serializer.flush();
                    fos.close();
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "No se ha podido crear el archivo", Toast.LENGTH_SHORT).show();
                }
            }catch(Exception e){
                Log.e("Exception", "Error de creación nuevo fichero");
            }
        }
    }

    private void recogerDatos() {
        boolean relleno = true;

        //while (relleno) {
            if (txtNuevoNombre.getText().length() > 0) {
                nombre = txtNuevoNombre.getText().toString();
            } else {
                relleno = false;
                txtNuevoNombre.requestFocus();
                Toast toast = Toast.makeText(getApplicationContext(), "Nombre no puede ser vacio.", Toast.LENGTH_SHORT);
                toast.show();
            }

            if (txtNuevoApellidos.getText().length() > 0) {
                apellidos = txtNuevoApellidos.getText().toString();
            } else {
                relleno = false;
                txtNuevoApellidos.requestFocus();
                Toast toast = Toast.makeText(getApplicationContext(), "Apellido no puede ser vacio.", Toast.LENGTH_SHORT);
                toast.show();
            }

            if (txtNuevoTelefono.getText().length() > 0) {
                telefono = txtNuevoTelefono.getText().toString();
            } else {
                relleno = false;
                txtNuevoTelefono.requestFocus();
                Toast toast = Toast.makeText(getApplicationContext(), "Telefono no puede ser vacio.", Toast.LENGTH_SHORT);
                toast.show();
            }

            if (txtNuevoCorreo.getText().length() > 0) {
                correo = txtNuevoCorreo.getText().toString();
            } else {
                relleno = false;
                txtNuevoCorreo.requestFocus();
                Toast toast = Toast.makeText(getApplicationContext(), "Correo no puede ser vacio.", Toast.LENGTH_SHORT);
                toast.show();
            }
        //}

    }

    private void addElement(Document doc, String nombre, String apellidos, String telefono, String correo) {
        Node root = doc.getDocumentElement();

        root.appendChild(createElement(doc, nombre, apellidos, telefono, correo));
    }

    private static Node createElement(Document doc, String nombre, String apellidos, String telefono, String correo) {
        Element tipo = doc.createElement("partner");

        tipo.appendChild(createAllElements(doc, tipo, "nombre", nombre));
        tipo.appendChild(createAllElements(doc, tipo, "apellidos", apellidos));
        tipo.appendChild(createAllElements(doc, tipo, "telefono", telefono));
        tipo.appendChild(createAllElements(doc, tipo, "correo", correo));

        return tipo;
    }

    // utility method to create text node
    private static Node createAllElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }

    public void volverAtras(){
        Intent i = new Intent(this, PartnerActivity.class);
        startActivity(i);
    }
}