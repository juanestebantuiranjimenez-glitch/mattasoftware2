package com.example.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "CALIFICACION")
public class Calificacion {

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_calificacion;
    private int puntuacion;
    private String comentario;
    private int id_cliente;
    private int id_campesino;

    
    public Calificacion() {
    }

    public Calificacion(int id_calificacion, int puntuacion, String comentario, int id_cliente, int id_campesino) {
        this.id_calificacion = id_calificacion;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
        this.id_cliente = id_cliente;
        this.id_campesino = id_campesino;
    }
    
    public int getId_calificacion() {
        return id_calificacion;
    }
    public void setId_calificacion(int id_calificacion) {
        this.id_calificacion = id_calificacion;
    }
    public int getPuntuacion() {
        return puntuacion;
    }
    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }
    public String getComentario() {
        return comentario;
    }
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    public int getId_cliente() {
        return id_cliente;
    }
    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }
    public int getId_campesino() {
        return id_campesino;
    }
    public void setId_campesino(int id_campesino) {
        this.id_campesino = id_campesino;
    }
}
