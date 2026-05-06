package com.example.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "ENTREGA")
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_entrega;
    private String direccion;
    private String estado_entrega;
    private String fecha_entrega;
    private int id_pedido;
    

    public Entrega() {
    }
    
    public Entrega(int id_entrega, String direccion, String estado_entrega, String fecha_entrega, int id_pedido) {
        this.id_entrega = id_entrega;
        this.direccion = direccion;
        this.estado_entrega = estado_entrega;
        this.fecha_entrega = fecha_entrega;
        this.id_pedido = id_pedido;
    }
    public int getId_entrega() {
        return id_entrega;
    }
    public void setId_entrega(int id_entrega) {
        this.id_entrega = id_entrega;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getEstado_entrega() {
        return estado_entrega;
    }
    public void setEstado_entrega(String estado_entrega) {
        this.estado_entrega = estado_entrega;
    }
    public String getFecha_entrega() {
        return fecha_entrega;
    }
    public void setFecha_entrega(String fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
    }
    public int getId_pedido() {
        return id_pedido;
    }
    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }
}
