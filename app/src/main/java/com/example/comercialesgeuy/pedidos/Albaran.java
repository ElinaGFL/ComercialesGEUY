package com.example.comercialesgeuy.pedidos;

import java.util.ArrayList;

public class Albaran {

    private int id, idComerc, idPartner;
    private String fechaPedido, fechaEnvio, fechaPago;
    private ArrayList<Linea> lineas;

    public Albaran() {}

    public Albaran(int id, int idComerc, int idPartner, String fechaPedido, String fechaEnvio, String fechaPago) {
        this.id = id;
        this.idComerc = idComerc;
        this.idPartner = idPartner;
        this.fechaPedido = fechaPedido;
        this.fechaEnvio = fechaEnvio;
        this.fechaPago = fechaPago;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdComerc() {
        return idComerc;
    }

    public void setIdComerc(int idComerc) {
        this.idComerc = idComerc;
    }

    public int getIdPartner() {
        return idPartner;
    }

    public void setIdPartner(int idPartner) {
        this.idPartner = idPartner;
    }

    public String getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public String getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(String fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public ArrayList<Linea> getLineas() {
        return lineas;
    }

    public void setLineas(ArrayList<Linea> lineas) {
        this.lineas = lineas;
    }

    @Override
    public String toString() {
        return "Albaran{" +
                "id=" + id +
                ", idComerc=" + idComerc +
                ", idPartner=" + idPartner +
                ", fechaPedido='" + fechaPedido + '\'' +
                ", fechaEnvio='" + fechaEnvio + '\'' +
                ", fechaPago='" + fechaPago + '\'' +
                ", lineas=" + lineas +
                '}';
    }
}
