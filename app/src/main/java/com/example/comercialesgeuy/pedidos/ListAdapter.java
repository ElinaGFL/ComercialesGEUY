package com.example.comercialesgeuy.pedidos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.example.comercialesgeuy.R;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    public ArrayList<Producto> listProductos;
    private Context context;
    private int cont = 0;

    public ListAdapter(Context context, ArrayList<Producto> listProductos) {
        this.context = context;
        this.listProductos = listProductos;
    }

    @Override
    public int getCount() {
        return listProductos.size();
    }

    @Override
    public Producto getItem(int position) {
        return listProductos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row;
        final ListViewHolder listViewHolder;
        if(convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.activity_pedido_producto,parent,false);
            listViewHolder = new ListViewHolder();
            listViewHolder.tvProductName = row.findViewById(R.id.tvProductName);
            listViewHolder.ivProduct = row.findViewById(R.id.ivProduct);
            listViewHolder.tvPrice = row.findViewById(R.id.tvPrice);
            listViewHolder.btnPlus = row.findViewById(R.id.ib_addnew);
            listViewHolder.edTextQuantity = row.findViewById(R.id.editTextQuantity);
            listViewHolder.btnMinus = row.findViewById(R.id.ib_remove);
            row.setTag(listViewHolder);
        } else {
            row = convertView;
            listViewHolder = (ListViewHolder) row.getTag();
        }
        final Producto products = getItem(position);

        listViewHolder.tvProductName.setText(products.getDescripcion());
        listViewHolder.ivProduct.setImageResource(R.mipmap.bateria);
        listViewHolder.tvPrice.setText(products.getPrvent()+" $");
        //listViewHolder.edTextQuantity.setText(products.getExistencias()+" un.");
        listViewHolder.edTextQuantity.setText("0 un.");

        listViewHolder.btnPlus.setOnClickListener(v -> {
            updateQuantity(position, listViewHolder.edTextQuantity, 1);
        });

        listViewHolder.btnMinus.setOnClickListener(v -> {
            updateQuantity(position, listViewHolder.edTextQuantity, -1);
        });

        return row;
    }

    private void updateQuantity(int position, EditText edTextQuantity, int value) {
        Producto productos = getItem(position);

        if(value > 0){
            if(productos.getExistencias() > cont) {
                cont++;
            }
        }else{
            if(cont > 0) {
                cont--;
            }
        }
        edTextQuantity.setText(cont + " un.");
    }
}