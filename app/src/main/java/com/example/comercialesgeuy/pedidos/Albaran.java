package com.example.comercialesgeuy.pedidos;

import java.util.ArrayList;

public class Albaran {

    private String partner, comercial, fechaPedido;
    private ArrayList<Linea> lineas;

    public Albaran() {}

    public Albaran(String partner, String comercial, String fechaPedido, ArrayList<Linea> lineas) {
        this.partner = partner;
        this.comercial = comercial;
        this.fechaPedido = fechaPedido;
        this.lineas = lineas;
    }

    public String getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getComercial() {
        return comercial;
    }

    public void setComercial(String comercial) {
        this.comercial = comercial;
    }

    public ArrayList<Linea> getLineas() {
        return lineas;
    }

    public void setLineas(ArrayList<Linea> lineas) {
        this.lineas = lineas;
    }

}
