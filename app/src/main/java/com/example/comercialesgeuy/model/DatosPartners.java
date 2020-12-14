package com.example.comercialesgeuy.model;

public class DatosPartners {
        String nombre;
        String apellidos;
        String correo;
        String telefono;
        DatosPartners(String nom, String aped, String email, String telf){
                nombre=nom;
                apellidos=aped;
                correo =email;
                telefono=telf;
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


        public void setNombre(String nombre) {
                this.nombre = nombre;
        }

        public String getCorreo() {
                return correo;
        }


        public String getTelefono() {
                return telefono;
        }

}
