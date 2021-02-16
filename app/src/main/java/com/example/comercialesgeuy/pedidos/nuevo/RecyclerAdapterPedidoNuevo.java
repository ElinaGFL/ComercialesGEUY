package com.example.comercialesgeuy.pedidos.nuevo;

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

public class RecyclerAdapterPedidoNuevo extends RecyclerView.Adapter<RecyclerAdapterPedidoNuevo.ProductViewHolder> {
    List<Producto> productoList;

    RecyclerAdapterPedidoNuevo(List<Producto> productoList){
        this.productoList = productoList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido_gestion, parent, false);
        ProductViewHolder pvh = new ProductViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        holder.txtProductName.setText(productoList.get(position).getCodigo());
        holder.txtPrice.setText(productoList.get(position).getPrvent() +" $");
        holder.edtCantidad.setText("0");
        //personViewHolder.personPhoto.setImageResource(persons.get(i).photoId);

        holder.imbAdd.setOnClickListener(v -> {
            updateQuantity(position, holder.edtCantidad, 1);
        });

        holder.imbRemove.setOnClickListener(v -> {
            updateQuantity(position, holder.edtCantidad, -1);
        });
    }

    public Producto getItem(int position) {
        return productoList.get(position);
    }

    private void updateQuantity(int position, EditText edTextQuantity, int value) {
        /*
        Producto producto = getItem(position);
        String temp = edTextQuantity.getText().toString();
        int val = Integer.parseInt(temp);

        if(value > 0){
            if(producto.getExistencias() > val) {
                val++;
                edTextQuantity.setText(String.valueOf(val));
            }
        }else{
            if(val > 0) {
                val--;
                edTextQuantity.setText(String.valueOf(val));
            }
        }
         */
        Producto producto = getItem(position);

        if(value > 0){
            if(producto.getExistencias() > producto.getCantidadPedida()) {
                producto.setCantidadPedidaPlus();
                edTextQuantity.setText(String.valueOf(producto.getCantidadPedida()));
            }
        }else{
            if(producto.getCantidadPedida() > 0) {
                producto.setCantidadPedidaMinus();
                edTextQuantity.setText(String.valueOf(producto.getCantidadPedida()));
            }
        }
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
        TextView txtProductName, txtPrice;
        EditText edtCantidad;
        ImageButton imbAdd, imbRemove;

        ProductViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            edtCantidad = itemView.findViewById(R.id.edtCantidad);
            imbAdd = itemView.findViewById(R.id.imbAdd);
            imbRemove = itemView.findViewById(R.id.imbRemove);
        }
    }

}
