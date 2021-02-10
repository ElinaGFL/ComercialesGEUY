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

public class RecyclerAdapterPedidoLista extends RecyclerView.Adapter<RecyclerAdapterPedidoLista.RecyclerHolder> {

    private List<Albaran> items;
    private RecyclerItemClick itemClick;

    public RecyclerAdapterPedidoLista(List<Albaran> items, RecyclerItemClick itemClick) {
        this.items = items;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recycler_adapter_pedido_lista,parent,false);
        return new RecyclerHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        Albaran pedido = items.get(position);

        holder.id.setText(String.valueOf(pedido.getId()));
        holder.fecha1.setText(pedido.getFechaEnvio());
        holder.fecha2.setText(pedido.getFechaPedido());
        holder.fecha3.setText(pedido.getFechaPago());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.itemClick(pedido);
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
        private TextView fecha2;
        private TextView fecha3;

        public RecyclerHolder(@NonNull View itemView){
            super(itemView);
            id = itemView.findViewById(R.id.nPedido);
            fecha1 = itemView.findViewById(R.id.FechaPedidoPedido);
            fecha2 = itemView.findViewById(R.id.FechaEnvioPedido);
            fecha3 = itemView.findViewById(R.id.FechaPagoPedido);
        }
    }

    public interface RecyclerItemClick {
        void itemClick(Albaran item);
    }

}
