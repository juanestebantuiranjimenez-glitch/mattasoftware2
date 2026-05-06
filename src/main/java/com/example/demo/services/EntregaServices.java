package com.example.demo.services;

import com.example.demo.models.Entrega;
import com.example.demo.repositories.EntregaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EntregaServices {

    @Autowired
    private EntregaRepository entregaRepository;

    public List<Entrega> listarEntregas() {
        return entregaRepository.findAll();
    }

    public Entrega crearEntrega(Entrega entrega) {
        // Lógica: Siempre que se crea una entrega, el estado inicial es "En camino"
        entrega.setEstado_entrega("En camino");
        return entregaRepository.save(entrega);
    }
}