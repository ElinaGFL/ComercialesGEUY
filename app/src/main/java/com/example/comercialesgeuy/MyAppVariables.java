package com.example.comercialesgeuy;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyAppVariables extends Application {

    private int comercIdConect = 1;
    private String comercNombre = "ANER";

    DBSQLite dbSQLite;
    SQLiteDatabase database;

    public int getComercIdConect() {
        return comercIdConect;
    }

    public void setComercIdConect(int comercIdConect) {
        this.comercIdConect = comercIdConect;
    }

    public String getComercNombre() {
        return comercNombre;
    }

    public void setComercNombre() {
        dbSQLite = new DBSQLite(this);
        database = dbSQLite.getWritableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM COMERCIALES WHERE _id = " + this.comercIdConect, null);

        if(cursor.moveToFirst()){
            this.comercNombre = cursor.getString(cursor.getColumnIndex("USR"));
        }

        cursor.close();
    }

}
