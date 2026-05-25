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
        if (producto.getPrecio() < 0) {
            return null;
        }
        return productoRepository.save(producto);
    }

    public Producto buscarPorId(int id) {
        return productoRepository.findById(id).orElse(null);
    }

    // Listar productos de un campesino para el dashboard
    public List<Producto> listarPorUsuario(int idUsuario) {
        return productoRepository.findByIdUsuario(idUsuario);
    }

    public void eliminar(int id) {
        productoRepository.deleteById(id);
    }
}