package com.example.demo.controllers;

import com.example.demo.services.UsuarioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private UsuarioServices usuarioService;

    @GetMapping("/")
    public String index() {
        return "home";
    }

    @GetMapping("/campesinos")
    public String campesinos(Model model) {
        // Obtener todos los usuarios y filtrar solo los campesinos
        java.util.List<com.example.demo.models.Usuarios> campesinos = usuarioService.obtenerTodos()
            .stream()
            .filter(u -> "CAMPESINO".equalsIgnoreCase(u.getRol()))
            .collect(java.util.stream.Collectors.toList());
        model.addAttribute("campesinos", campesinos);
        return "campesinos";
    }

    @GetMapping({"/cursos", "/como-funciona"})
    public String cursos() {
        return "cursos";
    }

    @GetMapping("/acerca-de")
    public String acercaDe() {
        return "acerca-de";
    }

    @GetMapping("/contacto")
    public String contacto() {
        return "contacto";
    }

    @GetMapping("/terminos")
    public String terminos() {
        return "terminos";
    }
}