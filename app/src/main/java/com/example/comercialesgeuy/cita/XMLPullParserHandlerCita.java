package com.example.comercialesgeuy.cita;

import android.content.Context;
import android.os.Environment;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLPullParserHandlerCita {

    private List<Cita> ListaCitas = new ArrayList<>();
    private Cita cita = null;
    private String tag = null;

    private static final String FECHA = "fecha";
    private static final String TITULO = "titulo";
    private static final String TEXTO = "texto";

    public List<Cita> parseXML() {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            InputStream stream = new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/GEUY/citas.xml"));
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

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return ListaCitas;
    }

    private void handleStartTag(String name) {
        if (name.equals("cita")) {
            cita = new Cita();
            ListaCitas.add(cita);
        } else {
            tag = name;
        }
    }

    private void handleText(String text) {
        String xmlText = text;
        if (cita != null && tag != null) {
            if (tag.equals(FECHA)) {
                cita.setFecha(xmlText);
            } else if (tag.equals(TITULO)) {
                cita.setCabecera(xmlText);
            } else if (tag.equals(TEXTO)) {
                cita.setTexto(xmlText);
            }
        }
    }
}