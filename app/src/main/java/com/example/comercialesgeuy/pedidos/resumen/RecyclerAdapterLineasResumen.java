package com.example.comercialesgeuy.pedidos.resumen;

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
import com.example.comercialesgeuy.pedidos.Producto;

import java.util.List;

public class RecyclerAdapterLineasResumen extends RecyclerView.Adapter<RecyclerAdapterLineasResumen.ProductViewHolder> {
    List<Linea> lineasList;

    RecyclerAdapterLineasResumen(List<Linea> lineasList){
        this.lineasList = lineasList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_pedido_resumen_linea, parent, false);
        ProductViewHolder pvh = new ProductViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {

        holder.txtListaName.setText(String.valueOf(lineasList.get(position).getNombre()));
        holder.txtPrice.setText(String.valueOf(lineasList.get(position).getPrecioLinea()) +" $");
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
        ImageView imgProduct;
        TextView txtListaName, txtPrice;
        EditText edtCantidad;

        ProductViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtListaName = itemView.findViewById(R.id.txtProductName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            edtCantidad = itemView.findViewById(R.id.edtCantidad);
        }
    }

}
