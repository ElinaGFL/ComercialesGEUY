package com.example.comercialesgeuy.pedidos.gestion;

import android.os.Environment;

import com.example.comercialesgeuy.pedidos.Producto;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLPullParserHandlerProducto {

    private List<Producto> listaProductos = new ArrayList<>();
    private Producto producto;
    private String tag;

    public List<Producto> parseXML() {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            InputStream stream = new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/GEUY/productos.xml"));
            xpp.setInput(stream, null);

            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    handleStartTag(xpp.getName());
                } else if (eventType == XmlPullParser.END_TAG) {
                    tag = null;
                } else if (eventType == XmlPullParser.TEXT) {
                    handleText(xpp.getText());
                }
                eventType = xpp.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProductos;
    }

    private void handleStartTag(String name) {
        if (name.equals("producto")) {
            producto = new Producto();
            listaProductos.add(producto);
        } else {
            tag = name;
        }
    }

    private void handleText(String text) {
        String xmlText = text;
        if (producto != null && tag != null) {
            if (tag.equals("codigo")) {
                producto.setCodigo(xmlText);
            } else if (tag.equals("descripcion")) {
                producto.setDescripcion(xmlText);
            } else if (tag.equals("precioUn")) {
                producto.setPrecioUn(Double.parseDouble(xmlText));
            } else if (tag.equals("existencias")) {
                producto.setExistencias(Integer.parseInt(xmlText));
            }
        }
    }
}
