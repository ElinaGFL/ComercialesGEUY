package com.example.comercialesgeuy.adapter;

import android.annotation.SuppressLint;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.example.comercialesgeuy.R;
import com.example.comercialesgeuy.model.DatosPartners;


import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> implements View.OnClickListener {

    private List<DatosPartners> items;
    private View.OnClickListener listener;

    public RecyclerAdapter(List<DatosPartners> items) {

        this.items = items;
    }



    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.partners_list_view,parent,false);

        return new RecyclerHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        DatosPartners d= items.get(position);
        holder.nombre.setText(d.getNombre());
        holder.apellidos.setText(d.getApellidos());
        holder.telefono.setText(d.getTelefono());
        holder.correo.setText(d.getCorreo());



    }





    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public void setOnclickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }

    }


    public static class RecyclerHolder extends RecyclerView.ViewHolder{
        private TextView nombre;
        private TextView apellidos;
        TextView telefono;
        private TextView correo;


        @SuppressLint("WrongViewCast")
        public RecyclerHolder(@NonNull View itemView){
            super(itemView);
            nombre= itemView.findViewById(R.id.nombrePartner);
            apellidos=itemView.findViewById(R.id.apellidoPartner);
            telefono= itemView.findViewById(R.id.telefonoPartner);
            correo= itemView.findViewById(R.id.emailPartner);




        }
    }
}
