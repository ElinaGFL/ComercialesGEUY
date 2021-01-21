package com.example.comercialesgeuy.pedidos;

import android.os.Environment;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLPullParserHandlerPedido {

    private List<Albaran> ListaAlbaranes = new ArrayList<>();
    private Albaran albaran;
    private String tag;

    public List<Albaran> parseXML() {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            InputStream stream = new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/GEUY/pedidos.xml"));
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
        return ListaAlbaranes;
    }

    private void handleStartTag(String name) {
        if (name.equals("pedido")) {
            albaran = new Albaran();
            ListaAlbaranes.add(albaran);
        } else {
            tag = name;
        }
    }

    private void handleText(String text) {
        String xmlText = text;
        if (albaran != null && tag != null) {
            if (tag.equals("partner")) {
                albaran.setPartner(xmlText);
            } else if (tag.equals("comercial")) {
                albaran.setComercial(xmlText);
            }
        }
    }
}
