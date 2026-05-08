package com.example.demo.controllers;

import com.example.demo.models.Entrega;
import com.example.demo.services.EntregaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/entregas")
public class EntregaController {

    @Autowired
    private EntregaServices entregaService;

    @GetMapping("/listar")
    public List<Entrega> listar() {
        return entregaService.listarEntregas();
    }

    @PostMapping("/crear")
    public Entrega crear(@RequestBody Entrega entrega) {
        return entregaService.crearEntrega(entrega);
    }
}