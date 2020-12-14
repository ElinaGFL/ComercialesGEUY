package com.example.comercialesgeuy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.comercialesgeuy.adapter.RecyclerAdapter;
import com.example.comercialesgeuy.model.DatosPartners;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class layoutpartners extends AppCompatActivity {
   private RecyclerView rvlista;
   private RecyclerAdapter adapter;
   private List<DatosPartners> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partners);


    }

    private void initViews(){
        rvlista=findViewById(R.id.cvpartners);

    }



    private void initValues(){
        LinearLayoutManager manager=new LinearLayoutManager(this);
        rvlista.setLayoutManager(manager);


    }

    private void leerXML(){
        try {
            DocumentBuilderFactory dbf= DocumentBuilderFactory.newInstance();
            DocumentBuilder db= dbf.newDocumentBuilder();

            Document doc = db.parse(getResources().openRawResource(R.raw.Partner));

            Element raiz = doc.getDocumentElement();



        }catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

    }
}

