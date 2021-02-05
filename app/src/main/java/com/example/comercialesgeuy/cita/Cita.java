package com.example.comercialesgeuy.cita;

public class Cita {

    private String fecha, hora, cabecera, texto;
    private int id;

    public Cita() {}

    public Cita (int id, String fecha, String hora, String cabecera, String texto) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.cabecera = cabecera;
        this.texto = texto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
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
