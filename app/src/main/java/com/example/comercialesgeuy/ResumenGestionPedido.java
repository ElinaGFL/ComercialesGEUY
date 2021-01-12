package com.example.comercialesgeuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class ResumenGestionPedido extends AppCompatActivity {

    private Bundle bundle;
    private String partner;
    private String comercial;
    private String cant1;
    private String cant2;
    private String cant3;
    private String cant4;
    private String actual;
    private TextView part, com, cantidad1, cantidad2, cantidad3, cantidad4, fech, pr1, pr2, pr3, pr4, tot;
    private int precio1, precio2, precio3, precio4, total, p1, p2, p3, p4;
    private Button editar;
    private Button confirmar;
    private File XMLfile;

    private int fecha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen_gestion_pedido);

        bundle = getIntent().getExtras();
        part = (TextView) findViewById(R.id.tvPartner);
        com = (TextView) findViewById(R.id.tvComercial);
        fech = (TextView) findViewById(R.id.tvFecha);
        cantidad1 = (TextView) findViewById(R.id.tvCantidad1);
        cantidad2 = (TextView) findViewById(R.id.tvCantidad2);
        cantidad3 = (TextView) findViewById(R.id.tvCantidad3);
        cantidad4 = (TextView) findViewById(R.id.tvCantidad4);
        pr1 = (TextView) findViewById(R.id.tvPr1);
        pr2 = (TextView) findViewById(R.id.tvPr2);
        pr3 = (TextView) findViewById(R.id.tvPr3);
        pr4 = (TextView) findViewById(R.id.tvPr4);
        tot = (TextView) findViewById(R.id.textView9);
        editar = (Button) findViewById(R.id.btnEditar);
        confirmar = (Button) findViewById(R.id.btnConfirmar);

        XMLfile = new File (Environment.getExternalStorageDirectory() + "/GEUY/pedidos.xml");

        partner = bundle.getString("partner");
        comercial = bundle.getString("comercial");
        cant1 = bundle.getString("Bat20");
        cant2 = bundle.getString("Bat60");
        cant3 = bundle.getString("BatSun");
        cant4 = bundle.getString("BatHaiz");

        part.setText("Partner:      " + partner);
        com.setText("Comercial:      " + comercial);
        cantidad1.setText(cant1);
        cantidad2.setText(cant2);
        cantidad3.setText(cant3);
        cantidad4.setText(cant4);

        p1 = Integer.parseInt(cant1);
        precio1 = 7500 * p1;
        pr1.setText(precio1 + " $");

        p2 = Integer.parseInt(cant2);
        precio2 = 7500 * p2;
        pr2.setText(precio2 + " $");

        p3 = Integer.parseInt(cant3);
        precio3 = 7500 * p3;
        pr3.setText(precio3 + " $");

        p4 = Integer.parseInt(cant4);
        precio4 = 7500 * p4;
        pr4.setText(precio4 + " $");

        total = precio1 + precio2 + precio3 + precio4;
        tot.setText(total + " $");

        actual = DateFormat.getDateTimeInstance().format(new Date());

        fech.setText(actual);

        editar.setOnClickListener((View v) -> onBackPressed());

        confirmar.setOnClickListener((View v) -> verificarPedido());

    }

    private void verificarPedido() {
        boolean confirm = false;

        if(p1 > 0){
            confirm = true;
        }
        if(p2 > 0){
            confirm = true;
        }
        if(p3 > 0){
            confirm = true;
        }
        if(p4 > 0){
            confirm = true;
        }

        if(confirm){
            guardarNuevoPedido();
        }else{
            Toast.makeText(this, "El pedido esta vacio", Toast.LENGTH_SHORT).show();
        }
    }

    private void guardarNuevoPedido() {
        if(XMLfile.exists()){
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder;
            try {
                dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(XMLfile);
                doc.getDocumentElement().normalize();
                //
                addElement(doc, partner, comercial, actual, String.valueOf(total), p1, p2, p3 ,p4);
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(XMLfile);
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.transform(source, result);
                Toast.makeText(this, "Se ha añadido el pedido al XML", Toast.LENGTH_SHORT).show();
                //finalizar(1);
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
                    serializer.startTag("", "pedidos");

                    serializer.startTag("", "pedido");

                    serializer.startTag("", "partner");
                    serializer.text(partner);
                    serializer.endTag("", "partner");

                    serializer.startTag("", "comercial");
                    serializer.text(comercial);
                    serializer.endTag("", "comercial");

                    serializer.startTag("", "fecha_pedido");
                    serializer.text(actual);
                    serializer.endTag("", "fecha_pedido");

                    serializer.startTag("", "precio_total");
                    serializer.text(String.valueOf(total));
                    serializer.endTag("", "precio_total");

                    serializer.startTag("", "lineas");

                    rellenarLineas(serializer);

                    serializer.endTag("", "lineas");

                    serializer.endTag("", "pedido");

                    serializer.endTag("", "pedidos");
                    serializer.endDocument();
                    serializer.endDocument();
                    serializer.flush();
                    fos.close();Toast.makeText(this, "Se ha añadido el pedido al XML", Toast.LENGTH_SHORT).show();
                    //finalizar(1);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "No se ha podido crear el archivo", Toast.LENGTH_SHORT).show();
                }
            }catch(Exception e){
                Log.e("Exception", "Error de creación nuevo fichero");
            }
        }
    }
    /*
    private void finalizar(int i) {
        Intent intent = new Intent();
        intent.putExtra("result",i);
        setResult(RESULT_OK, intent);
        finish();
    }
    */

    private void rellenarLineas(XmlSerializer serializer) {
        if(p1 > 0){
            rellenarLineasPorUno(serializer, "IBattery-20", p1);
        }
        if(p2 > 0){
            rellenarLineasPorUno(serializer, "IBattery-60", p2);
        }
        if(p3 > 0){
            rellenarLineasPorUno(serializer, "IBattery+PackSun", p3);
        }
        if(p4 > 0){
            rellenarLineasPorUno(serializer, "IBattery-PackHaizea", p4);
        }
    }

    private void rellenarLineasPorUno(XmlSerializer serializer, String texto, int cant) {
        try{
            serializer.startTag("", "linea");

            serializer.startTag("", "articulo");
            serializer.text(texto);
            serializer.endTag("", "articulo");

            serializer.startTag("", "cantidad");
            serializer.text(String.valueOf(cant));
            serializer.endTag("", "cantidad");

            serializer.endTag("", "linea");
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private void addElement(Document doc, String partner, String comercial, String fecha, String total, int p1, int p2, int p3, int p4) {
        Node root = doc.getDocumentElement();

        root.appendChild(createElement(doc, partner, comercial, fecha, total, p1, p2, p3, p4));
    }

    private static Node createElement(Document doc, String partner, String comercial, String fecha, String total, int p1, int p2, int p3, int p4) {
        Element tipo = doc.createElement("pedido");

        tipo.appendChild(createAllElements(doc, "partner", partner));
        tipo.appendChild(createAllElements(doc, "comercial", comercial));
        tipo.appendChild(createAllElements(doc, "fecha", fecha));
        tipo.appendChild(createAllElements(doc, "total", total));

        Element nodeLineas = doc.createElement("lineas");
        tipo.appendChild(nodeLineas);

        Element nodeLinea = doc.createElement("linea");
        nodeLineas.appendChild(nodeLinea);

        if(p1 > 0){
            Element nodeArticulo = doc.createElement("articulo");
            nodeArticulo.appendChild(doc.createTextNode("IBattery-20"));
            nodeLineas.appendChild(nodeArticulo);

            Element nodeCantidad = doc.createElement("cantidad");
            nodeCantidad.appendChild(doc.createTextNode(String.valueOf(p1)));
            nodeLineas.appendChild(nodeCantidad);
        }
        if(p2 > 0){
            Element nodeArticulo = doc.createElement("articulo");
            nodeArticulo.appendChild(doc.createTextNode("IBattery-60"));
            nodeLineas.appendChild(nodeArticulo);

            Element nodeCantidad = doc.createElement("cantidad");
            nodeCantidad.appendChild(doc.createTextNode(String.valueOf(p2)));
            nodeLineas.appendChild(nodeCantidad);
        }
        if(p3 > 0){
            Element nodeArticulo = doc.createElement("articulo");
            nodeArticulo.appendChild(doc.createTextNode("IBattery+PackSun"));
            nodeLineas.appendChild(nodeArticulo);

            Element nodeCantidad = doc.createElement("cantidad");
            nodeCantidad.appendChild(doc.createTextNode(String.valueOf(p3)));
            nodeLineas.appendChild(nodeCantidad);
        }
        if(p4 > 0){
            Element nodeArticulo = doc.createElement("articulo");
            nodeArticulo.appendChild(doc.createTextNode("IBattery-PackHaizea"));
            nodeLineas.appendChild(nodeArticulo);

            Element nodeCantidad = doc.createElement("cantidad");
            nodeCantidad.appendChild(doc.createTextNode(String.valueOf(p3)));
            nodeLineas.appendChild(nodeCantidad);
        }

        return tipo;
    }

    // utility method to create text node
    private static Node createAllElements(Document doc, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }

}