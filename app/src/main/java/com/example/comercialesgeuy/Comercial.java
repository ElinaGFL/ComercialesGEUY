package com.example.comercialesgeuy;

import java.io.Serializable;

public class Comercial implements Serializable {

    private String USR, PWD, nombre, apellidos, empresa, email, telefono, delegacion;
    int id;

    public Comercial(){}

    public Comercial(int id, String nombre, String apellidos, String empresa, String email, String telefono, String delegacion) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.empresa = empresa;
        this.email = email;
        this.telefono = telefono;
        this.delegacion = delegacion;
    }

    public String getUSR() {
        return USR;
    }

    public void setUSR(String USR) {
        this.USR = USR;
    }

    public String getPWD() {
        return PWD;
    }

    public void setPWD(String PWD) {
        this.PWD = PWD;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDelegacion() {
        return delegacion;
    }

    public void setDelegacion(String delegacion) {
        this.delegacion = delegacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return nombre + ' ' + apellidos;
    }
}
