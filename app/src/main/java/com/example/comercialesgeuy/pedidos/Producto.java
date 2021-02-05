package com.example.comercialesgeuy.pedidos;

import java.io.Serializable;

public class Producto implements Serializable {
    private String codigo, descripcion;
    public int existencias, pedidos = 0;
    private double precioUn;

    public Producto(){}

    public Producto(String codigo, double precioUn) {
        this.codigo = codigo;
        this.precioUn = precioUn;
    }

    public Producto(String codigo, String descripcion, int existencias, double precioUn) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.existencias = existencias;
        this.precioUn = precioUn;
    }

    public Producto(String codigo, String descripcion, int existencias, double precioUn, int pedidos) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.existencias = existencias;
        this.precioUn = precioUn;
        this.pedidos = pedidos;
    }

    public int getPedidos() {
        return pedidos;
    }

    public void setPedidos(int pedidos) {
        this.pedidos = pedidos;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getExistencias() {
        return existencias;
    }

    public void setExistencias(int existencias) {
        this.existencias = existencias;
    }

    public double getPrecioUn() {
        return precioUn;
    }

    public void setPrecioUn(double precioUn) {
        this.precioUn = precioUn;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "codigo='" + codigo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", existencias=" + existencias +
                ", precioUn=" + precioUn +
                '}';
    }
}
