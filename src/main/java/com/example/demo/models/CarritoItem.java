package com.example.demo.models;

import java.io.Serializable;

public class CarritoItem implements Serializable {
    private static final long serialVersionUID = 1L;

    private int productoId;
    private String nombre;
    private String imagen;
    private String vendedor;
    private String unidad;
    private double precio;
    private int cantidad;
    private int stockDisponible;

    public CarritoItem() {
    }

    public CarritoItem(Producto producto, int cantidad) {
        this.productoId = producto.getId_producto();
        this.nombre = producto.getNombre();
        this.imagen = producto.getImagen();
        this.precio = producto.getPrecio();
        this.cantidad = cantidad;
        this.stockDisponible = producto.getCantidad_disponible();
        this.unidad = "kg";
        this.vendedor = producto.getUsuario() != null ? producto.getUsuario().getNombre() : "Campesino";
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getStockDisponible() {
        return stockDisponible;
    }

    public void setStockDisponible(int stockDisponible) {
        this.stockDisponible = stockDisponible;
    }
}
