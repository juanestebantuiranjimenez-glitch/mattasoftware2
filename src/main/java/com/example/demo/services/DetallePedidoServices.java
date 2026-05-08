package com.example.demo.services;

import com.example.demo.models.Detalle_Pedido;
import com.example.demo.models.Producto;
import com.example.demo.repositories.DetallePedidoRepository;
import com.example.demo.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetallePedidoServices {

    @Autowired
    private DetallePedidoRepository detalleRepository;

    @Autowired
    private ProductoRepository productoRepository; 

    public Detalle_Pedido agregarItem(Detalle_Pedido detalle) {
        // 1. Buscamos el producto usando el id_producto
        Producto producto = productoRepository.findById(detalle.getId_producto()).orElse(null);

        if (producto != null) {
            // 2. Restamos la cantidad comprada al stock actual
            int nuevoStock = producto.getCantidad_disponible() - detalle.getCantidad();

            if (nuevoStock >= 0) {
                // 3. Actualizamos el stock en la tabla de productos
                producto.setCantidad_disponible(nuevoStock);
                productoRepository.save(producto); 
                
                // 4. Guardamos el registro de la compra
                return detalleRepository.save(detalle);
            }
        }
        return null; // Si no hay stock suficiente o el producto no existe
    }
}