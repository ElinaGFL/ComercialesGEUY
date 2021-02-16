package com.example.comercialesgeuy.pedidos.gestion;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comercialesgeuy.R;
import com.example.comercialesgeuy.pedidos.Linea;

import java.util.List;

public class RecyclerAdapterPedidoLineas extends RecyclerView.Adapter<RecyclerAdapterPedidoLineas.ProductViewHolder> {
    List<Linea> lineasList;

    RecyclerAdapterPedidoLineas(List<Linea> lineasList){
        this.lineasList = lineasList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido_resumen, parent, false);
        ProductViewHolder pvh = new ProductViewHolder(v);
        return pvh;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        holder.txtListaName.setText(String.valueOf(lineasList.get(position).getNombre()));
        holder.txtPrice.setText(lineasList.get(position).getPrecioLinea() +" $");
        holder.edtCantidad.setText(lineasList.get(position).getCantidad()+"un");
    }

    public Linea getItem(int position) {
        return lineasList.get(position);
    }

    @Override
    public int getItemCount() {
        return lineasList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView txtListaName, txtPrice;
        EditText edtCantidad;

        ProductViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            txtListaName = itemView.findViewById(R.id.txtProductName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            edtCantidad = itemView.findViewById(R.id.edtCantidad);
        }
    }

}
