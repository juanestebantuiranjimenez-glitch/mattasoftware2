package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index() {
        return "home"; // Esto abrirá el home.html al entrar a localhost:8080
    }

    @GetMapping("/campesinos")
    public String campesinos() {
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
}