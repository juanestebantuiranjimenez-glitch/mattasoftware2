package com.example.demo.controllers;

import com.example.demo.models.Pago;
import com.example.demo.services.PagoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pagos")
public class PagoController {

    @Autowired
    private PagoServices pagoService;

    @GetMapping("/listar")
    public List<Pago> listar() {
        return pagoService.listarPagos();
    }

    @PostMapping("/registrar")
    public Pago registrar(@RequestBody Pago pago) {
        return pagoService.registrarPago(pago);
    }
}