package com.example.comercialesgeuy.pedidos;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comercialesgeuy.R;
import com.example.comercialesgeuy.productos.Producto;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class PedidoResumenActivity extends AppCompatActivity {

    private Bundle bundle;
    private String partner;
    private String comercial;
    private String actual;
    private TextView txtPartner, txtComercial, fecha, tot;
    private double precio1, precio2, precio3, precio4, total;
    int cant1, cant2, cant3, cant4;
    private Button editar;
    private Button confirmar;
    private File XMLfile;
    ArrayList<Producto> productOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen_gestion_pedido);

        txtPartner = (TextView) findViewById(R.id.tvPartner);
        txtComercial = (TextView) findViewById(R.id.tvComercial);
        fecha = (TextView) findViewById(R.id.tvFecha);


        tot = (TextView) findViewById(R.id.CantidadTotal);
        editar = (Button) findViewById(R.id.btnEditar);
        confirmar = (Button) findViewById(R.id.btnConfirmar);

        bundle = getIntent().getExtras();

        XMLfile = new File (Environment.getExternalStorageDirectory() + "/GEUY/pedidos.xml");

        partner = bundle.getString("partnerData");
        comercial = bundle.getString("comercialData");
        productOrder = (ArrayList<Producto>) bundle.getSerializable("arrayProductosPedido");

        txtPartner.setText("Partner:    " + partner);
        txtComercial.setText("Comercial:    " + comercial);

        rellenarPantalla();

        actual = DateFormat.getDateTimeInstance().format(new Date());

        fecha.setText(actual);

        editar.setOnClickListener((View v) -> onBackPressed());

        confirmar.setOnClickListener((View v) -> verificarPedido());
    }

    private void verificarPedido() {
        boolean confirm = false;

        if(cant1 > 0){
            confirm = true;
        }
        if(cant2 > 0){
            confirm = true;
        }
        if(cant3 > 0){
            confirm = true;
        }
        if(cant4 > 0){
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
                addElement(doc, partner, comercial, actual, String.valueOf(total), cant1, cant2, cant3, cant4);
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

    private void rellenarLineas(XmlSerializer serializer) {
        if(cant1 > 0){
            rellenarLineasPorUno(serializer, "IBattery-20", cant1);
        }
        if(cant2 > 0){
            rellenarLineasPorUno(serializer, "IBattery-60", cant2);
        }
        if(cant3 > 0){
            rellenarLineasPorUno(serializer, "IBattery+PackSun", cant3);
        }
        if(cant4 > 0){
            rellenarLineasPorUno(serializer, "IBattery-PackHaizea", cant4);
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
            nodeLinea.appendChild(nodeArticulo);

            Element nodeCantidad = doc.createElement("cantidad");
            nodeCantidad.appendChild(doc.createTextNode(String.valueOf(p1)));
            nodeLinea.appendChild(nodeCantidad);
        }
        if(p2 > 0){
            Element nodeArticulo = doc.createElement("articulo");
            nodeArticulo.appendChild(doc.createTextNode("IBattery-60"));
            nodeLinea.appendChild(nodeArticulo);

            Element nodeCantidad = doc.createElement("cantidad");
            nodeCantidad.appendChild(doc.createTextNode(String.valueOf(p2)));
            nodeLinea.appendChild(nodeCantidad);
        }
        if(p3 > 0){
            Element nodeArticulo = doc.createElement("articulo");
            nodeArticulo.appendChild(doc.createTextNode("IBattery+PackSun"));
            nodeLinea.appendChild(nodeArticulo);

            Element nodeCantidad = doc.createElement("cantidad");
            nodeCantidad.appendChild(doc.createTextNode(String.valueOf(p3)));
            nodeLinea.appendChild(nodeCantidad);
        }
        if(p4 > 0){
            Element nodeArticulo = doc.createElement("articulo");
            nodeArticulo.appendChild(doc.createTextNode("IBattery-PackHaizea"));
            nodeLinea.appendChild(nodeArticulo);

            Element nodeCantidad = doc.createElement("cantidad");
            nodeCantidad.appendChild(doc.createTextNode(String.valueOf(p3)));
            nodeLinea.appendChild(nodeCantidad);
        }

        return tipo;
    }

    private static Node createAllElements(Document doc, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }

    @SuppressLint("SetTextI18n")
    private void rellenarPantalla() {

        for(Producto p : productOrder) {
            if (p.getCodigo().equalsIgnoreCase("Bat20")) {
                cant1 = p.getPedidos();
                precio1 = p.getPedidos() * p.getPrecioUn();
                txtCantidad1.setText(String.valueOf(p.getPedidos()));
                txtPrecio1.setText(precio1 + " $");
            }
            if (p.getCodigo().equalsIgnoreCase("Bat60")) {
                cant2 = p.getPedidos();
                precio2 = p.getPedidos() * p.getPrecioUn();
                txtCantidad2.setText(String.valueOf(p.getPedidos()));
                txtPrecio2.setText(precio2 + " $");
            }
            if (p.getCodigo().equalsIgnoreCase("BatSun")) {
                cant3 = p.getPedidos();
                precio3 = p.getPedidos() * p.getPrecioUn();
                txtCantidad3.setText(String.valueOf(p.getPedidos()));
                txtPrecio3.setText(precio3 + " $");
            }
            if (p.getCodigo().equalsIgnoreCase("BatHaiz")) {
                cant4 = p.getPedidos();
                precio4 = p.getPedidos() * p.getPrecioUn();
                txtCantidad4.setText(String.valueOf(p.getPedidos()));
                txtPrecio4.setText(precio4 + " $");
            }
        }
        total = precio1 + precio2 + precio3 + precio4;
        tot.setText(String.format("%.2f", total) + " $");
    }


}