package com.example.comercialesgeuy.pedidos;

import com.example.comercialesgeuy.pedidos.gestion.RecyclerAdapterPedidoGestion;

import java.io.Serializable;

public class Producto implements Serializable {
    private int id, existencias, cantidadPedida = 0;
    private String codigo, descripcion, img;
    private float prvent;

    public Producto() {}

    public Producto(int id, String codigo, String descripcion,  float prvent, int existencias, String img, int cantidadPedida) {
        this.id = id;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.prvent = prvent;
        this.existencias = existencias;
        this.img = img;
    }

    public int getCantidadPedida() {
        return cantidadPedida;
    }

    public void setCantidadPedidaPlus() {
        this.cantidadPedida++;
    }

    public void setCantidadPedidaMinus() {
        if(this.cantidadPedida > 0) {
            this.cantidadPedida--;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExistencias() {
        return existencias;
    }

    public void setExistencias(int existencias) {
        this.existencias = existencias;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public float getPrvent() {
        return prvent;
    }

    public void setPrvent(float prvent) {
        this.prvent = prvent;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", existencias=" + existencias +
                ", cantidadPedida=" + cantidadPedida +
                ", codigo='" + codigo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", img='" + img + '\'' +
                ", prvent=" + prvent +
                '}';
    }
}
