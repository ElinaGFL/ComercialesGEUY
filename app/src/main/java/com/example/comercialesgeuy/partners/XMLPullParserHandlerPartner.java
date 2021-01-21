package com.example.comercialesgeuy.partners;

import android.os.Environment;

import com.example.comercialesgeuy.partners.Partner;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLPullParserHandlerPartner {

    private List<Partner> ListaPartners = new ArrayList<>();
    private Partner partner;
    private String tag;

    public List<Partner> parseXML() {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            InputStream stream = new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/GEUY/partners.xml"));
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
        return ListaPartners;
    }

    private void handleStartTag(String name) {
        if (name.equals("partner")) {
            partner = new Partner();
            ListaPartners.add(partner);
        } else {
            tag = name;
        }
    }

    private void handleText(String text) {
        String xmlText = text;
        if (partner != null && tag != null) {
            if (tag.equals("nombre")) {
                partner.setNombre(xmlText);
            } else if (tag.equals("apellidos")) {
                partner.setApellidos(xmlText);
            } else if (tag.equals("telefono")) {
                partner.setTelefono(xmlText);
            } else if (tag.equals("correo")) {
                partner.setCorreo(xmlText);
            }
        }
    }
}
