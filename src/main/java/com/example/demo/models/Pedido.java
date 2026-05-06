package com.example.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table(name = "PEDIDO")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_pedido;
    private String fecha; 
    private String estado;
    private double total; 
    private int id_cliente;

    
    public Pedido() {
    }
    
    public Pedido(int id_pedido, String fecha, String estado, double total, int id_cliente) {
        this.id_pedido = id_pedido;
        this.fecha = fecha;
        this.estado = estado;
        this.total = total;
        this.id_cliente = id_cliente;
    }


    public int getId_pedido() {
        return id_pedido;
    }


    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }


    public String getFecha() {
        return fecha;
    }


    public void setFecha(String fecha) {
        this.fecha = fecha;
    }


    public String getEstado() {
        return estado;
    }


    public void setEstado(String estado) {
        this.estado = estado;
    }


    public double getTotal() {
        return total;
    }


    public void setTotal(double total) {
        this.total = total;
    }


    public int getId_cliente() {
        return id_cliente;
    }


    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }


    
}


