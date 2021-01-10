package com.example.comercialesgeuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

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
    private TextView part, com, cantidad1, cantidad2, cantidad3, cantidad4, fech, pr1, pr2, pr3, pr4, tot;
    private int precio1, precio2, precio3, precio4, total = 0;
    private Button editar;
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

        int p1 = Integer.parseInt(cant1);
        precio1 = 7500 * p1;
        pr1.setText(precio1 + " $");

        int p2 = Integer.parseInt(cant2);
        precio2 = 7500 * p2;
        pr2.setText(precio2 + " $");

        int p3 = Integer.parseInt(cant3);
        precio3 = 7500 * p3;
        pr3.setText(precio3 + " $");

        int p4 = Integer.parseInt(cant4);
        precio4 = 7500 * p4;
        pr4.setText(precio4 + " $");

        total = precio1 + precio2 + precio3 + precio4;
        tot.setText(total + " $");

        String actual = DateFormat.getDateTimeInstance().format(new Date());

        fech.setText(actual);


        editar.setOnClickListener((View v) -> {
            onBackPressed();

        });

        String nombre_archivo = "seat";
        ArrayList nombre = new ArrayList();
        ArrayList combustible = new ArrayList();
        ArrayList potencia = new ArrayList();
        ArrayList precio = new ArrayList();

        nombre.add("ibiza td");
        combustible.add("diesel");
        potencia.add("75cv");
        precio.add("10000");

        nombre.add("ibiza tdi");
        combustible.add("diesel");
        potencia.add("125cv");
        precio.add("16000");

        nombre.add("ibiza sti");
        combustible.add("gasolina");
        potencia.add("125cv");
        precio.add("15000");

        try {
            generate(nombre_archivo, nombre, combustible, potencia, precio);
        } catch (Exception e) {
        }

    }

    public static void generate(String name, ArrayList<String> nombre, ArrayList<String> combustible, ArrayList<String> potencia, ArrayList<String> precio) throws Exception {

        if (nombre.isEmpty() || combustible.isEmpty() || nombre.size() != combustible.size()) {
            System.out.println("ERROR empty ArrayList");
            return;
        } else {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            Document document = implementation.createDocument(null, name, null);
            document.setXmlVersion("1.0");

            //Main Node
            Element raiz = document.getDocumentElement();
            //Por cada key creamos un item que contendrá la key y el value
            for (int i = 0; i < nombre.size(); i++) {
                //turismo Node
                Element turismoNode = document.createElement("turismo");
                //nombre Node
                Element nombreNode = document.createElement("nombre");
                Text nodeKeyValue = document.createTextNode(nombre.get(i));
                nombreNode.appendChild(nodeKeyValue);
                //combustible Node
                Element combustibleNode = document.createElement("combustible");
                Text nodeValueValue = document.createTextNode(combustible.get(i));
                combustibleNode.appendChild(nodeValueValue);
                //potencia Node
                Element potenciaNode = document.createElement("potencia");
                Text nodePotencia = document.createTextNode(potencia.get(i));
                potenciaNode.appendChild(nodePotencia);
                //precio Node
                Element precioNode = document.createElement("precio");
                Text nodePrecio = document.createTextNode(precio.get(i));
                precioNode.appendChild(nodePrecio);


                //append keyNode and valueNode to itemNode
                turismoNode.appendChild(nombreNode);
                turismoNode.appendChild(combustibleNode);
                turismoNode.appendChild(potenciaNode);
                turismoNode.appendChild(precioNode);

                //append itemNode to raiz
                raiz.appendChild(turismoNode); //pegamos el elemento a la raiz "Documento"
            }
            //Generate XML
            Source source = new DOMSource(document);
            //Indicamos donde lo queremos almacenar
            Result result = new StreamResult(new java.io.File("/sdcard0/download/" + name + ".xml")); //nombre del archivo
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(source, result);
        }
    }






    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}