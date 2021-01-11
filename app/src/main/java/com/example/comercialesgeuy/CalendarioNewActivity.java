package com.example.comercialesgeuy;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class CalendarioNewActivity extends AppCompatActivity {

    private EditText txtCabecera;
    private EditText txtTexto;
    private Button btnGuardar;
    private String fecha;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_new);

        btnGuardar = findViewById(R.id.btnGuardar);
        txtCabecera = findViewById(R.id.txtCabecera);
        txtTexto = findViewById(R.id.txtTexto);

        Bundle extras = getIntent().getExtras();
        fecha = extras.getString("fecha");

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .4));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = 0;

        getWindow().setAttributes(params);

        btnGuardar.setOnClickListener(v -> guardarNuevaCita());
    }

    private void guardarNuevaCita() {
        String titulo;
        String texto;

        if (txtCabecera.getText().length() > 0) {
            titulo = txtCabecera.getText().toString();
        } else {
            titulo = "El título esta vacio.";
        }

        if (txtTexto.getText().length() > 0) {
            texto = txtTexto.getText().toString();
        } else {
            texto = "El texto esta vacio.";
        }

        File newxmlfile = new File(Environment.getExternalStorageDirectory() + "/GEUY/citas.xml");
        XmlSerializer serializer = Xml.newSerializer();
        FileOutputStream fos = null;

        String filePath = Environment.getExternalStorageDirectory() + "/GEUY/citas.xml";
        System.out.println(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;

        if (!newxmlfile.exists()) {
            newxmlfile.getParentFile().mkdir();

            try {
                fos = new FileOutputStream(newxmlfile);
                newxmlfile.createNewFile();
                serializer.setOutput(fos, "UTF-8");
                serializer.startDocument(null, true);
                serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                serializer.startTag(null, "citas");
                serializer.startTag(null, "cita");
                serializer.startTag(null, "fecha");
                serializer.text(fecha);
                serializer.endTag(null, "fecha");
                serializer.startTag(null, "titulo");
                serializer.text(titulo);
                serializer.endTag(null, "titulo");
                serializer.startTag(null, "texto");
                serializer.text(texto);
                serializer.endTag(null, "texto");
                serializer.endTag(null, "cita");
                serializer.endTag(null, "citas");
                serializer.endDocument();
                serializer.flush();
                fos.close();
                finish();
            } catch (IOException e) {
                Log.e("IOException", "Error de creación nuevo fichero");
            }
        } else {
            try {
                dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(newxmlfile);
                doc.getDocumentElement().normalize();
                addElement(doc, fecha, titulo, texto);
                writeXMLFile(doc);
                finish();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException | TransformerException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeXMLFile(Document doc) throws TransformerFactoryConfigurationError, TransformerException {
        doc.getDocumentElement().normalize();
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(Environment.getExternalStorageDirectory() + "/GEUY/citas.xml"));
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(source, result);
    }

    private void addElement(Document doc, String fecha, String titulo, String descrip) {
        Node root = doc.getDocumentElement();

        Element nuevaCita = doc.createElement("cita");

        Element tituloCita = doc.createElement("titulo");
        tituloCita.appendChild(doc.createTextNode(titulo));
        nuevaCita.appendChild(tituloCita);
        System.out.println("titulo");

        Element fechaCita = doc.createElement("fecha");
        fechaCita.appendChild(doc.createTextNode(fecha));
        nuevaCita.appendChild(fechaCita);
        System.out.println("fecha");

        Element descripCita = doc.createElement("texto");
        descripCita.appendChild(doc.createTextNode(descrip));
        nuevaCita.appendChild(descripCita);
        root.appendChild(nuevaCita);
    }
}
