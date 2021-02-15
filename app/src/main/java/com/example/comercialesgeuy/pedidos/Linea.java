package com.example.comercialesgeuy.pedidos;

public class Linea {

    private int idLinea, idAlbaran, idProducto, cantidad, precioLinea;
    private String nombre;


    public Linea() {}

    public Linea(int idLinea, int idAlbaran, int idProducto, int cantidad, int precioLinea,String nombre) {
        this.idLinea = idLinea;
        this.idAlbaran = idAlbaran;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioLinea = precioLinea;
        this.nombre = nombre;
    }

    public String getNombre() {return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(int idLinea) {
        this.idLinea = idLinea;
    }

    public int getIdAlbaran() {
        return idAlbaran;
    }

    public void setIdAlbaran(int idAlbaran) {
        this.idAlbaran = idAlbaran;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getPrecioLinea() {
        return precioLinea;
    }

    public void setPrecioLinea(int precioLinea) {
        this.precioLinea = precioLinea;
    }

    @Override
    public String toString() {
        return "Linea{" +
                "idLinea=" + idLinea +
                ", idAlbaran=" + idAlbaran +
                ", idProducto=" + idProducto +
                ", cantidad=" + cantidad +
                ", precioLinea=" + precioLinea +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
