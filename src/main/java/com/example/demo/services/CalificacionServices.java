package com.example.demo.services;

import com.example.demo.models.Calificacion;
import com.example.demo.repositories.CalificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CalificacionServices {

    @Autowired
    private CalificacionRepository calificacionRepository;

    public List<Calificacion> listarCalificaciones() {
        return calificacionRepository.findAll();
    }

    public Calificacion guardarCalificacion(Calificacion calificacion) {
        // Lógica: La puntuación no puede ser mayor a 5 ni menor a 1
        if (calificacion.getPuntuacion() > 5) calificacion.setPuntuacion(5);
        if (calificacion.getPuntuacion() < 1) calificacion.setPuntuacion(1);
        
        return calificacionRepository.save(calificacion);
    }
}