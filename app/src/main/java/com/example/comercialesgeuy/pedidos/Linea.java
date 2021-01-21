package com.example.comercialesgeuy.pedidos;

import com.example.comercialesgeuy.productos.Producto;

public class Linea {

    private Producto producto;
    private int cantidad;
    private double precioLinea;

    public Linea(){}

    public Linea(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioLinea = producto.getPrecioUn() * (double) cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        this.precioLinea = producto.getPrecioUn() * (double) cantidad;
    }

    public double getPrecioLinea() {
        return precioLinea;
    }

    @Override
    public String toString() {
        return "Linea{" +
                "producto=" + producto +
                ", cantidad=" + cantidad +
                ", pr_total=" + precioLinea +
                '}';
    }
}
