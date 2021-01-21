package com.example.comercialesgeuy.partners;

public class Partner {

        private String nombre, apellidos, correo, telefono;

        public Partner(){}

        public Partner(String nombre, String apellidos, String correo, String telefono){
                this.nombre = nombre;
                this.apellidos = apellidos;
                this.correo = correo;
                this.telefono = telefono;
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

        /*
        @Override
        public String toString() {
                return "Partner {" +
                        "nombre='" + nombre + '\'' +
                        ", apellidos='" + apellidos + '\'' +
                        ", correo='" + correo + '\'' +
                        ", telefono='" + telefono + '\'' +
                        '}';
        }
        */
}
