package com.example.demo.controllers;

import com.example.demo.models.CarritoItem;
import com.example.demo.models.Producto;
import com.example.demo.services.ProductoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private ProductoServices productoService;

    // Obtener el carrito de la sesión o crear uno nuevo
    @SuppressWarnings("unchecked")
    private List<CarritoItem> obtenerCarrito(HttpSession session) {
        List<CarritoItem> carrito = (List<CarritoItem>) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new ArrayList<>();
            session.setAttribute("carrito", carrito);
        }
        return carrito;
    }

    // Calcular el total de items en el carrito
    private int calcularTotalItems(List<CarritoItem> carrito) {
        return carrito.stream().mapToInt(CarritoItem::getCantidad).sum();
    }

    // Calcular el subtotal del carrito
    private double calcularSubtotal(List<CarritoItem> carrito) {
        return carrito.stream().mapToDouble(item -> item.getPrecio() * item.getCantidad()).sum();
    }

    @GetMapping
    public String verCarrito(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/usuarios/login";
        }
        List<CarritoItem> carrito = obtenerCarrito(session);
        double subtotal = calcularSubtotal(carrito);
        
        model.addAttribute("itemsCarrito", carrito);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("envio", 0); // Simulamos envío gratis por ahora
        
        Double descuento = (Double) session.getAttribute("descuentoCarrito");
        if (descuento == null) descuento = 0.0;
        
        model.addAttribute("descuento", descuento);
        model.addAttribute("total", subtotal - descuento);

        return "carrito";
    }

    @PostMapping("/agregar")
    @ResponseBody
    public Map<String, Object> agregarAlCarrito(@RequestParam int productoId, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        if (session.getAttribute("usuario") == null) {
            response.put("exito", false);
            response.put("error", "No autenticado");
            return response;
        }
        List<CarritoItem> carrito = obtenerCarrito(session);
        
        Producto producto = productoService.buscarPorId(productoId);
        if (producto == null) {
            response.put("exito", false);
            response.put("error", "Producto no encontrado");
            return response;
        }
        if (producto.getCantidad_disponible() <= 0) {
            response.put("exito", false);
            response.put("error", "Producto sin stock disponible");
            return response;
        }

        boolean encontrado = false;
        for (CarritoItem item : carrito) {
            if (item.getProductoId() == productoId) {
                int nuevaCantidad = item.getCantidad() + 1;
                if (nuevaCantidad > producto.getCantidad_disponible()) {
                    response.put("exito", false);
                    response.put("error", "No hay más stock disponible");
                    return response;
                }
                item.setCantidad(nuevaCantidad);
                item.setStockDisponible(producto.getCantidad_disponible());
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            carrito.add(new CarritoItem(producto, 1));
        }
        
        int totalItems = calcularTotalItems(carrito);
        session.setAttribute("cantidadCarrito", totalItems); // Para el navbar
        response.put("totalItems", totalItems);
        response.put("exito", true);
        return response;
    }

    @PostMapping("/actualizar")
    @ResponseBody
    public Map<String, Object> actualizarCarrito(@RequestParam int productoId, @RequestParam int delta, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        if (session.getAttribute("usuario") == null) {
            response.put("exito", false);
            response.put("error", "No autenticado");
            return response;
        }
        List<CarritoItem> carrito = obtenerCarrito(session);
        
        CarritoItem itemAEliminar = null;
        CarritoItem itemEncontrado = null;
        for (CarritoItem item : carrito) {
            if (item.getProductoId() == productoId) {
                itemEncontrado = item;
                break;
            }
        }

        if (itemEncontrado == null) {
            response.put("exito", false);
            response.put("error", "Producto no encontrado en el carrito");
            return response;
        }

        int nuevaCantidad = itemEncontrado.getCantidad() + delta;
        if (nuevaCantidad <= 0) {
            itemAEliminar = itemEncontrado;
            response.put("eliminado", true);
        } else {
            Producto producto = productoService.buscarPorId(productoId);
            if (producto == null) {
                response.put("exito", false);
                response.put("error", "Producto no encontrado");
                return response;
            }
            if (nuevaCantidad > producto.getCantidad_disponible()) {
                response.put("exito", false);
                response.put("error", "Stock insuficiente");
                return response;
            }
            itemEncontrado.setCantidad(nuevaCantidad);
            itemEncontrado.setStockDisponible(producto.getCantidad_disponible());
            response.put("eliminado", false);
            response.put("cantidad", nuevaCantidad);
            response.put("precioLinea", nuevaCantidad * itemEncontrado.getPrecio());
        }
        
        if (itemAEliminar != null) {
            carrito.remove(itemAEliminar);
        }
        
        int totalItems = calcularTotalItems(carrito);
        session.setAttribute("cantidadCarrito", totalItems);
        
        double subtotal = calcularSubtotal(carrito);
        Double descuento = (Double) session.getAttribute("descuentoCarrito");
        if (descuento == null) descuento = 0.0;
        
        response.put("totalItems", totalItems);
        response.put("subtotal", subtotal);
        response.put("total", subtotal - descuento);
        response.put("exito", true);
        
        return response;
    }

    @PostMapping("/eliminar")
    @ResponseBody
    public Map<String, Object> eliminarDelCarrito(@RequestParam int productoId, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        if (session.getAttribute("usuario") == null) {
            response.put("exito", false);
            return response;
        }
        List<CarritoItem> carrito = obtenerCarrito(session);
        carrito.removeIf(item -> item.getProductoId() == productoId);
        
        int totalItems = calcularTotalItems(carrito);
        session.setAttribute("cantidadCarrito", totalItems);
        
        response.put("exito", true);
        return response;
    }

    @PostMapping("/cupon")
    @ResponseBody
    public Map<String, Object> aplicarCupon(@RequestParam String codigo, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        if ("MATTA2025".equalsIgnoreCase(codigo)) {
            session.setAttribute("descuentoCarrito", 10000.0);
            response.put("valido", true);
            response.put("mensaje", "Descuento de $10,000 aplicado");
        } else {
            response.put("valido", false);
            response.put("mensaje", "Cupón no válido");
        }
        return response;
    }
}
