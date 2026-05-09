package com.example.demo.models;
import jakarta.persistence.*;

@Entity 
@Table(name = "USUARIO") 
public class Usuarios {
    

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id_usuario;
    private String nombre;
    private String correo;
    private String telefono;
    private String contrasena;
    private String tipo_usuario; 
    private String ubicacion;

    public Usuarios() {
    }

    public Usuarios(int id_usuario, String nombre, String correo, String telefono, String contrasena, String tipo_usuario, String ubicacion) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.contrasena = contrasena;
        this.tipo_usuario = tipo_usuario;
        this.ubicacion = ubicacion;
    }

    
    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    
}