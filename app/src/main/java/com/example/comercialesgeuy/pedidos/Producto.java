package com.example.comercialesgeuy.pedidos;

public class Producto {
    private String codigo, descripcion, imagen;
    private int existencias;
    private double precioUn;

    public Producto(){}

    public Producto(String codigo, String descripcion, String imagen, int existencias, double precioUn) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.existencias = existencias;
        this.precioUn = precioUn;
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
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
                ", imagen='" + imagen + '\'' +
                ", existencias=" + existencias +
                ", precioUn=" + precioUn +
                '}';
    }
}
