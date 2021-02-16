package com.example.comercialesgeuy.pedidos.resumen;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comercialesgeuy.R;
import com.example.comercialesgeuy.pedidos.Producto;

import java.util.List;

public class RecyclerAdapterPedidoResumen extends RecyclerView.Adapter<RecyclerAdapterPedidoResumen.ProductViewHolder> {
    private final List<Producto> productoList;

    public RecyclerAdapterPedidoResumen(List<Producto> productoList){
        this.productoList = productoList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido_resumen, parent, false);
        return new ProductViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        holder.txtProductName.setText(productoList.get(position).getCodigo());
        holder.txtPrice.setText(productoList.get(position).getPrvent() * productoList.get(position).getCantidadPedida() +" $");
        holder.edtCantidad.setText(productoList.get(position).getCantidadPedida()+"un");
    }

    public Producto getItem(int position) {
        return productoList.get(position);
    }

    @Override
    public int getItemCount() {
        return productoList.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView txtProductName, txtPrice;
        EditText edtCantidad;

        ProductViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            edtCantidad = itemView.findViewById(R.id.edtCantidad);
        }
    }

}
