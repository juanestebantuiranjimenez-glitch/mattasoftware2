package com.example.demo.services;

import com.example.demo.models.Pedido;
import com.example.demo.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PedidoServices {

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido crearPedido(Pedido pedido) {
        // REGLA DE NEGOCIO: Todo pedido nuevo comienza en estado "Pendiente"
        if (pedido.getEstado() == null || pedido.getEstado().isEmpty()) {
            pedido.setEstado("Pendiente");
        }
        return pedidoRepository.save(pedido);
    }
}