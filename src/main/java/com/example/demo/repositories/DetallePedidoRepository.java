package com.example.demo.repositories;

import com.example.demo.models.Detalle_Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetallePedidoRepository extends JpaRepository<Detalle_Pedido, Integer> {
}