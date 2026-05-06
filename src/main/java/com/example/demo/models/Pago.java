package com.example.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table(name = "PAGO")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_pago;
    private String metodo_pago;
    private String estado_pago;
    private String fecha;
    private int id_pedido;
    

    public Pago() {
    }
    
    public Pago(int id_pago, String metodo_pago, String estado_pago, String fecha, int id_pedido) {
        this.id_pago = id_pago;
        this.metodo_pago = metodo_pago;
        this.estado_pago = estado_pago;
        this.fecha = fecha;
        this.id_pedido = id_pedido;
    }
    public int getId_pago() {
        return id_pago;
    }
    public void setId_pago(int id_pago) {
        this.id_pago = id_pago;
    }
    public String getMetodo_pago() {
        return metodo_pago;
    }
    public void setMetodo_pago(String metodo_pago) {
        this.metodo_pago = metodo_pago;
    }
    public String getEstado_pago() {
        return estado_pago;
    }
    public void setEstado_pago(String estado_pago) {
        this.estado_pago = estado_pago;
    }
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public int getId_pedido() {
        return id_pedido;
    }
    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }
}
