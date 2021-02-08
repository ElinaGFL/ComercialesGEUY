package com.example.comercialesgeuy.partners;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comercialesgeuy.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> {

    private List<Partner> items;
    private RecyclerItemClick itemClick;

    public RecyclerAdapter(List<Partner> items, RecyclerItemClick itemClick) {
        this.items = items;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.partners_list_view,parent,false);
        return new RecyclerHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        Partner partner = items.get(position);

        holder.nombre.setText(partner.getNombre());
        holder.apellidos.setText(partner.getApellidos());
        holder.telefono.setText(partner.getTelefono());
        holder.correo.setText(partner.getCorreo());
        holder.poblacion.setText(partner.getPoblacion());
        holder.cif.setText(partner.getCif());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.itemClick(partner);
            }
        });
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
        private TextView poblacion;
        private TextView cif;

        public RecyclerHolder(@NonNull View itemView){
            super(itemView);
            nombre = itemView.findViewById(R.id.nombrePartner);
            apellidos = itemView.findViewById(R.id.apellidoPartner);
            telefono = itemView.findViewById(R.id.telefonoPartner);
            correo = itemView.findViewById(R.id.emailPartner);
            poblacion = itemView.findViewById(R.id.poblacionPartner);
            cif = itemView.findViewById(R.id.CIFPartner);
        }
    }

    public interface RecyclerItemClick {
        void itemClick(Partner item);
    }

}
