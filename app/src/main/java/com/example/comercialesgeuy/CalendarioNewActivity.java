package com.example.comercialesgeuy;

import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class CalendarioNewActivity extends AppCompatActivity {

    private EditText txtCabecera;
    private EditText txtTexto;
    private Button btnGuardar;
    private String fecha, titulo, texto;
    private File XMLfile;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_new);

        btnGuardar = findViewById(R.id.btnGuardar);
        txtCabecera = findViewById(R.id.txtCabecera);
        txtTexto = findViewById(R.id.txtTexto);

        Bundle extras = getIntent().getExtras();
        fecha = extras.getString("fecha");

        XMLfile = new File (Environment.getExternalStorageDirectory() + "/GEUY/citas.xml");

        crearVentana();

        btnGuardar.setOnClickListener(v -> guardarNuevaCita());
    }

    private void crearVentana() {
        //DisplayMetrics class se utiliza para obtener información diversa sobre la pantalla en Android
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        //obtenemos la altura y anchura de la pantalla del movil
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        //los numeros definen el tamaño de la ventana
        getWindow().setLayout((int) (width * .8), (int) (height * .4));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = 0;

        getWindow().setAttributes(params);
    }

    private void guardarNuevaCita() {
        recogerDatos();

        if(XMLfile.exists()){
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder;
            try {
                dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(XMLfile);
                doc.getDocumentElement().normalize();
                addElement(doc, fecha, titulo, texto);
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(XMLfile);
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.transform(source, result);
                Toast.makeText(this, "Se ha añadido la cita al XML", Toast.LENGTH_SHORT).show();
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
                    serializer.startTag("", "citas");

                    serializer.startTag("", "cita");

                    serializer.startTag("", "fecha");
                    serializer.text(fecha);
                    serializer.endTag("", "fecha");

                    serializer.startTag("", "titulo");
                    serializer.text(titulo);
                    serializer.endTag("", "titulo");

                    serializer.startTag("", "texto");
                    serializer.text(texto);
                    serializer.endTag("", "texto");

                    serializer.endTag("", "cita");

                    serializer.endTag("", "citas");
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
        if (txtCabecera.getText().length() > 0) {
            titulo = txtCabecera.getText().toString();
        } else {
            titulo = "Mi evento";
        }

        if (txtTexto.getText().length() > 0) {
            texto = txtTexto.getText().toString();
        } else {
            texto = "";
        }
    }

    private void addElement(Document doc, String fecha, String titulo, String texto) {
        Node root = doc.getDocumentElement();

        root.appendChild(createElement(doc, fecha, titulo, texto));
    }

    private static Node createElement(Document doc, String fecha, String titulo, String texto) {
        Element tipo = doc.createElement("cita");

        tipo.appendChild(createAllElements(doc, tipo, "fecha", fecha));
        tipo.appendChild(createAllElements(doc, tipo, "titulo", titulo));
        tipo.appendChild(createAllElements(doc, tipo, "texto", texto));

        return tipo;
    }

    // utility method to create text node
    private static Node createAllElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
}
