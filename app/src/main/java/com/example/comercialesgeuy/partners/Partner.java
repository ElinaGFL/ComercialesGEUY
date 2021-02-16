package com.example.comercialesgeuy.partners;

import java.io.Serializable;

public class Partner implements Serializable {

        private String nombre, apellidos, correo, telefono, poblacion, cif;
        private int id;

        public Partner(){}

        public Partner(int id, String nombre, String apellidos, String correo, String telefono, String poblacion, String cif){
                this.id = id;
                this.nombre = nombre;
                this.apellidos = apellidos;
                this.correo = correo;
                this.telefono = telefono;
                this.poblacion = poblacion;
                this.cif = cif;
        }

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public void setNombre(String nombre) {
                this.nombre = nombre;
        }

        public void setApellidos(String apellidos) {
                this.apellidos = apellidos;
        }

        public void setPoblacion(String poblacion) {
                this.poblacion = poblacion;
        }

        public void setCif(String cif) {
                this.cif = cif;
        }

        public void setCorreo(String correo) {
                this.correo = correo;
        }

        public void setTelefono(String telefono) {
                this.telefono = telefono;
        }

        public String getNombre(){
                return nombre;
        }

        public String getApellidos(){
                return apellidos;
        }

        public String getCorreo() {
                return correo;
        }

        public String getTelefono() {
                return telefono;
        }

        public String getPoblacion() {
                return poblacion;
        }

        public String getCif() {
                return cif;
        }

        @Override
        public String toString() {
                return nombre + ' ' + apellidos;
        }
}
