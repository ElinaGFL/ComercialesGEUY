package com.example.comercialesgeuy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class layoutpartners extends AppCompatActivity {
    RecyclerView partners;
    ArrayList<String> listPartners;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partners);
        partners=(RecyclerView)findViewById(R.id.rcvPartiners);
        partners.setLayoutManager(new LinearLayoutManager(this));

        listPartners=new ArrayList<String>();
        for(int i=0;i<=50;i++){
            listPartners.add("Datos #"+i +"");
        }

        DatosPartners adaptador=new DatosPartners(listPartners);
        partners.setAdapter(adaptador);

    }
    public class DatosPartners extends RecyclerView.Adapter<DatosPartners.ViewHolderDatos>{
        ArrayList<String> listPartners;

        public DatosPartners(ArrayList<String> listPartners) {
            this.listPartners = listPartners;
        }

        @NonNull
        @Override
        public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.partners,null,false);
            return new ViewHolderDatos(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
            holder.asignarDatos(listPartners.get(position));
        }

        @Override
        public int getItemCount() {
            return listPartners.size();
        }

        public class ViewHolderDatos extends RecyclerView.ViewHolder {

            TextView datos;
            public ViewHolderDatos(@NonNull View itemView) {
                super(itemView);
                datos=itemView.findViewById(R.id.idPartners);
            }

            public void asignarDatos(String s) {
                datos.setText(s);
            }
        }
    }
}

