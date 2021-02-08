package com.example.comercialesgeuy.partners;

import java.io.Serializable;

public class Partner implements Serializable {

        private String nombre, apellidos, correo, telefono;
        int id;

        public Partner(){}

        public Partner(int id, String nombre, String apellidos, String correo, String telefono){
                this.id = id;
                this.nombre = nombre;
                this.apellidos = apellidos;
                this.correo = correo;
                this.telefono = telefono;
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

        @Override
        public String toString() {
                return nombre + ' ' + apellidos;
        }
}
