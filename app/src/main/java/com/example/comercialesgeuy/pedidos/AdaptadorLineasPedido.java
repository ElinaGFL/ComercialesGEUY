package com.example.comercialesgeuy.pedidos;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comercialesgeuy.R;
import com.example.comercialesgeuy.partners.RecyclerAdapter;
import com.example.comercialesgeuy.productos.Producto;

import java.util.List;

public class AdaptadorLineasPedido extends RecyclerView.Adapter<AdaptadorLineasPedido.RecyclerHolder>{
    private List<Producto> produc;

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        holder.titulo.setText(produc.get(position).getDescripcion());
        holder.cantidad.setText(produc.get(position).getPedidos());
        holder.precio.setText((int) produc.get(position).getPrecioUn());
    }

    @Override
    public int getItemCount() {
        return produc.size();
    }

    public static class RecyclerHolder extends RecyclerView.ViewHolder{
        TextView titulo;
        TextView cantidad;
        TextView precio;

        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            titulo=itemView.findViewById(R.id.tituloPedido);
            cantidad=itemView.findViewById(R.id.candidadPedido);
            precio=itemView.findViewById(R.id.precioUnidades);
        }
    }
}
