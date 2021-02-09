package com.example.comercialesgeuy.cita;

import java.io.Serializable;

public class Cita implements Serializable {

    private String fechaHora, cabecera, texto;
    private int id;

    public Cita() {}

    public Cita (int id, String fechaHora, String cabecera, String texto) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.cabecera = cabecera;
        this.texto = texto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getCabecera() {
        return cabecera;
    }

    public void setCabecera(String cabecera) {
        this.cabecera = cabecera;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return "Fecha y hora: " + fechaHora + "\n" +
                "Título: " + cabecera + "\n" +
                "Información: " + texto;
    }
}
