package com.example.comercialesgeuy.pedidos;

import com.example.comercialesgeuy.comerciales.Comercial;
import com.example.comercialesgeuy.partners.Partner;

import java.util.ArrayList;

public class Albaran {

    private String fecha, partner, comercial;
    private ArrayList<Linea> lineas;

    public Albaran() {}

    public Albaran(String fecha, String partner, String comercial) {
        this.fecha = fecha;
        this.partner = partner;
        this.comercial = comercial;
        this.lineas = new ArrayList<>();
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
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
