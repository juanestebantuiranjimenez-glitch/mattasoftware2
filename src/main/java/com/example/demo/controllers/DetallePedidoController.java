package com.example.demo.controllers;

import com.example.demo.models.Detalle_Pedido;
import com.example.demo.services.DetallePedidoServices; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/detalles")
public class DetallePedidoController {

    @Autowired
    private DetallePedidoServices detalleService; 

    @PostMapping("/agregar")
    public Detalle_Pedido agregar(@RequestBody Detalle_Pedido detalle) {
        return detalleService.agregarItem(detalle);
    }
}