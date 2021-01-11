package com.example.comercialesgeuy.cita;

public class Cita {

    private String fecha, cabecera, texto;

    public Cita() {}

    public Cita(String fecha, String cabecera, String texto) {
        this.fecha = fecha;
        this.cabecera = cabecera;
        this.texto = texto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
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
        return "Fecha: " + fecha + "\n" +
                "Título: " + cabecera + "\n" +
                "Info: " + texto;
    }
}
