package com.example.demo.controllers;

import com.example.demo.models.Producto;
import com.example.demo.services.ProductoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/productos") // Esta será la dirección: localhost:8080/productos
public class ProductoController {

    @Autowired
    private ProductoServices productoService;

    @GetMapping("/listar")
    public List<Producto> listar() {
        return productoService.listarTodos();
    }
}