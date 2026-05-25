package com.example.demo.controllers;

import com.example.demo.models.Pedido;
import com.example.demo.services.PedidoServices; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoServices pedidoService; 

    @GetMapping("/listar")
    public List<Pedido> listar(jakarta.servlet.http.HttpSession session) {
        if (session.getAttribute("usuario") == null) return null;
        return pedidoService.listarPedidos();
    }

    @PostMapping("/crear")
    public Pedido crear(@RequestBody Pedido pedido, jakarta.servlet.http.HttpSession session) {
        if (session.getAttribute("usuario") == null) return null;
        return pedidoService.crearPedido(pedido);
    }
}