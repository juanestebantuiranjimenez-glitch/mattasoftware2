package com.example.demo.services;

import com.example.demo.models.Pago;
import com.example.demo.repositories.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PagoServices {

    @Autowired
    private PagoRepository pagoRepository;

    public List<Pago> listarPagos() {
        return pagoRepository.findAll();
    }

    public Pago registrarPago(Pago pago) {
        // Lógica: Si el método de pago está vacío, podemos poner uno por defecto
        if (pago.getMetodo_pago() == null) {
            pago.setMetodo_pago("Efectivo"); //por el momento efectivo
        }
        return pagoRepository.save(pago);
    }
}