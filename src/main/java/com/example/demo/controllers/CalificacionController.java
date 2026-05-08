package com.example.demo.controllers;

import com.example.demo.models.Calificacion;
import com.example.demo.services.CalificacionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/calificaciones")
public class CalificacionController {

    @Autowired
    private CalificacionServices calificacionService;

    @GetMapping("/listar")
    public List<Calificacion> listar() {
        return calificacionService.listarCalificaciones();
    }

    @PostMapping("/guardar")
    public Calificacion guardar(@RequestBody Calificacion calificacion) {
        return calificacionService.guardarCalificacion(calificacion);
    }
}