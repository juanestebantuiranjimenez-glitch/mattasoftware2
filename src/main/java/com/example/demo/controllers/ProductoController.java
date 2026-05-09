package com.example.demo.controllers;

import com.example.demo.models.Producto;
import com.example.demo.services.ProductoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; // CAMBIO: Ahora es @Controller
import org.springframework.ui.Model; // Para pasar datos al HTML
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller // CAMBIO: Quitamos el @RestController para poder usar HTML
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoServices productoService;

    @GetMapping("/listar")
    public String listar(Model model) {
        List<Producto> productos = productoService.listarTodos();
        
        // "listaProductos" es el nombre que usamos en el HTML (th:each)
        model.addAttribute("listaProductos", productos);
        
        return "productos/lista"; // Esto busca el archivo templates/productos/lista.html
    }
}