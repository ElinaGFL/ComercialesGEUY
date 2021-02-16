package com.example.comercialesgeuy.pedidos.gestion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comercialesgeuy.R;
import com.example.comercialesgeuy.pedidos.Albaran;

import java.util.List;

public class RecyclerAdapterListaPedidos extends RecyclerView.Adapter<RecyclerAdapterListaPedidos.RecyclerHolder> {

    private List<Albaran> items;
    private RecyclerItemClick itemClick;

    public RecyclerAdapterListaPedidos(List<Albaran> items, RecyclerItemClick itemClick) {
        this.items = items;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido_lista,parent,false);
        return new RecyclerHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        Albaran albaran = items.get(position);

        holder.id.setText("Nº " + albaran.getId());
        holder.fecha1.setText(albaran.getFechaPedido());
        holder.txtPartnerPL.setText("Partner: " + albaran.getNombrePartner());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.itemClick(albaran);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public static class RecyclerHolder extends RecyclerView.ViewHolder{
        private TextView id;
        private TextView fecha1;
        private TextView txtPartnerPL;

        public RecyclerHolder(@NonNull View itemView){
            super(itemView);
            id = itemView.findViewById(R.id.nPedido);
            fecha1 = itemView.findViewById(R.id.FechaPedidoPedido);
            txtPartnerPL = itemView.findViewById(R.id.txtPartnerPL);
        }
    }

    public interface RecyclerItemClick {
        void itemClick(Albaran item);
    }

}