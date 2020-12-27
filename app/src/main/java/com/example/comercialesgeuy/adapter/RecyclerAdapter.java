package com.example.comercialesgeuy.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comercialesgeuy.R;
import com.example.comercialesgeuy.model.DatosPartners;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> {

    private List<DatosPartners> items;

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

    public static class RecyclerHolder extends RecyclerView.ViewHolder{
        private TextView nombre;
        private TextView apellidos;
        private TextView telefono;
        private TextView correo;

        public RecyclerHolder(@NonNull View itemView){
            super(itemView);
            nombre= itemView.findViewById(R.id.nombrePartner);
            apellidos=itemView.findViewById(R.id.apellidoPartner);
            telefono= itemView.findViewById(R.id.telefonoPartner);
            correo= itemView.findViewById(R.id.emailPartner);
        }
    }
}
