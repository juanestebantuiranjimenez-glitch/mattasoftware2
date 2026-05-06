package com.example.demo.controllers;

import com.example.demo.models.Usuarios;
import com.example.demo.services.UsuarioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Le dice al Spring ese que esta clase maneja rutas web o (API)
@RequestMapping("/usuarios") // La dirección base va a ser localhost:8080/usuarios
public class UsuarioController {

    @Autowired
    private UsuarioServices usuarioService;

    // Método para ver todos los usuarios en el navegador
    @GetMapping("/listar")
    public List<Usuarios> listar() {
        return usuarioService.obtenerTodos();
    }
}