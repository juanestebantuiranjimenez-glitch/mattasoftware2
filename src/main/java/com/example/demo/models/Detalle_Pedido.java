package com.example.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "DETALLE_PEDIDO")
public class Detalle_Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_detalle;
    private int id_pedido;
    private int id_producto;
    private int cantidad;
    private double total_parcial;
    
    public Detalle_Pedido() {
    }
    
    public Detalle_Pedido(int id_detalle, int id_pedido, int id_producto, int cantidad, double total_parcial) {
        this.id_detalle = id_detalle;
        this.id_pedido = id_pedido;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.total_parcial = total_parcial;
    }
    public int getId_detalle() {
        return id_detalle;
    }
    public void setId_detalle(int id_detalle) {
        this.id_detalle = id_detalle;
    }
    public int getId_pedido() {
        return id_pedido;
    }
    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }
    public int getId_producto() {
        return id_producto;
    }
    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public double getTotal_parcial() {
        return total_parcial;
    }
    public void setTotal_parcial(double total_parcial) {
        this.total_parcial = total_parcial;
    }



}