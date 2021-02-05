package com.example.comercialesgeuy.pedidos;

import java.io.Serializable;

public class Producto implements Serializable {
    private int id, existencias;
    private String codigo, descripcion, img;
    private float prvent;

    public Producto() {}

    public Producto(int id, String codigo, String descripcion,  float prvent, int existencias, String img) {
        this.id = id;
        this.existencias = existencias;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.prvent = prvent;
        this.img = img;
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
}
