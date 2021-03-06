package com.example.comercialesgeuy.antiguo;

import android.os.Environment;

import com.example.comercialesgeuy.cita.Cita;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLPullParserHandlerCita {

    private List<Cita> ListaCitas = new ArrayList<>();
    private Cita cita;
    private String tag;

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

        } catch (Exception e) {
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
            if (tag.equals("fecha")) {
                cita.setFechaHora(xmlText);
            } else if (tag.equals("titulo")) {
                cita.setCabecera(xmlText);
            } else if (tag.equals("texto")) {
                cita.setTexto(xmlText);
            }
        }
    }
}