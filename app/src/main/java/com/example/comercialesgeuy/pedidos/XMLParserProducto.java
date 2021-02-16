package com.example.comercialesgeuy.pedidos;

import android.os.Environment;

import com.example.comercialesgeuy.pedidos.Producto;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLParserProducto {

    private List<Producto> productoList = new ArrayList<>();
    private Producto producto;
    private String tag;

    public List<Producto> parseXML(InputStream stream) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();

            //InputStream stream = new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/GEUY/productos.xml"));
            parser.setInput(stream, null);

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    handleStartTag(parser.getName());
                } else if (eventType == XmlPullParser.END_TAG) {
                    tag = null;
                } else if (eventType == XmlPullParser.TEXT) {
                    handleText(parser.getText());
                }
                eventType = parser.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return productoList;
    }

    private void handleStartTag(String name) {
        if (name.equals("producto")) {
            producto = new Producto();
            productoList.add(producto);
        } else {
            tag = name;
        }
    }

    private void handleText(String text) {
        String xmlText = text;
        if (producto != null && tag != null) {
            switch (tag) {
                case "codigo":
                    producto.setCodigo(xmlText);
                    break;
                case "descripcion":
                    producto.setDescripcion(xmlText);
                    break;
                case "prvent":
                    producto.setPrvent(Float.parseFloat(xmlText));
                    break;
                case "existencias":
                    producto.setExistencias(Integer.parseInt(xmlText));
                    break;
                case "img":
                    producto.setImg(xmlText);
                    break;
            }
        }
    }
}
