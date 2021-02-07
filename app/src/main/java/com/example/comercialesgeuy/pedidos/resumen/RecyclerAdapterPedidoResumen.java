package com.example.comercialesgeuy.pedidos.resumen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comercialesgeuy.R;
import com.example.comercialesgeuy.pedidos.Producto;

import java.util.List;

public class RecyclerAdapterPedidoResumen extends RecyclerView.Adapter<RecyclerAdapterPedidoResumen.ProductViewHolder> {
    List<Producto> productoList;

    RecyclerAdapterPedidoResumen(List<Producto> productoList){
        this.productoList = productoList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_pedido_resumen_linea, parent, false);
        ProductViewHolder pvh = new ProductViewHolder(v);
        return pvh;
    }

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
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imgProduct;
        TextView txtProductName, txtPrice;
        EditText edtCantidad;

        ProductViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            edtCantidad = itemView.findViewById(R.id.edtCantidad);
        }
    }

}
