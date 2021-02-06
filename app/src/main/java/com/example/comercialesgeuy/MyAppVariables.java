package com.example.comercialesgeuy;

import android.app.Application;

public class MyAppVariables extends Application {

    private int comercIdConect = 1;

    public int getComercIdConect() {
        return comercIdConect;
    }

    public void setComercIdConect(int comercIdConect) {
        this.comercIdConect = comercIdConect;
    }
}
