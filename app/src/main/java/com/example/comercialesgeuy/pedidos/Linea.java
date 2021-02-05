package com.example.comercialesgeuy.pedidos;

public class Linea {

    private int idLinea, idAlbaran, idProducto, cantidad, precioLinea;

    public Linea() {}

    public Linea(int idLinea, int idAlbaran, int idProducto, int cantidad, int precioLinea) {
        this.idLinea = idLinea;
        this.idAlbaran = idAlbaran;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioLinea = precioLinea;
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

}
