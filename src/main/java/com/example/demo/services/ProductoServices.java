package com.example.demo.services;

import com.example.demo.models.Producto;
import com.example.demo.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoServices {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    public Producto guardar(Producto producto) {
        // REGLA DE NEGOCIO: No permitir productos con precio negativo
        if (producto.getPrecio() < 0) {
            return null; 
        }
        return productoRepository.save(producto);
    }

    // Método para buscar por ID (útil para ver detalles de un producto)
    public Producto buscarPorId(int id) {
        return productoRepository.findById(id).orElse(null);
    }
}