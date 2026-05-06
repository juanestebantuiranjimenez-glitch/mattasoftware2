package com.example.demo.services;

import com.example.demo.models.Detalle_Pedido;
import com.example.demo.repositories.DetallePedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetallePedidoServices {

    @Autowired
    private DetallePedidoRepository detalleRepository;

    public Detalle_Pedido agregarItem(Detalle_Pedido detalle) {
        // REGLA DE NEGOCIO: Calcular el total parcial (cantidad * precio)
        // Nota: En un paso más pro, traeríamos el precio del ProductoService
        return detalleRepository.save(detalle);
    }
}