package com.example.comercialesgeuy;

import android.app.Application;

public class MyAppVariables extends Application {

    Comercial comercial;

    public Comercial getComercial() {
        return comercial;
    }

    public int getComercialId() { return comercial.getId(); }

    public String getComercialNombre() {
        return comercial.getNombre();
    }

    public void setComercial(Comercial comercial) {
        this.comercial = comercial;
    }

}
