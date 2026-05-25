package com.example.demo.controllers;

import com.example.demo.models.CarritoItem;
import com.example.demo.models.Usuarios;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    @GetMapping
    public String mostrarCheckout(HttpSession session, Model model) {
        Usuarios usuario = (Usuarios) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/usuarios/login";
        }
        
        @SuppressWarnings("unchecked")
        List<CarritoItem> carrito = (List<CarritoItem>) session.getAttribute("carrito");
        if (carrito == null || carrito.isEmpty()) {
            return "redirect:/carrito";
        }
        
        double subtotal = carrito.stream().mapToDouble(item -> item.getProducto().getPrecio() * item.getCantidad()).sum();
        Double descuento = (Double) session.getAttribute("descuentoCarrito");
        if (descuento == null) descuento = 0.0;
        
        model.addAttribute("itemsCarrito", carrito);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("descuento", descuento);
        model.addAttribute("total", subtotal - descuento);
        
        return "checkout";
    }

    @PostMapping("/procesar")
    public String procesarCheckout(HttpSession session) {
        Usuarios usuario = (Usuarios) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/usuarios/login";
        }
        
        // Aquí iría la lógica para guardar el Pedido en la base de datos
        // usando el PedidoController o PedidoService, pero simulamos el éxito.
        
        // Vaciar el carrito
        session.removeAttribute("carrito");
        session.setAttribute("cantidadCarrito", 0);
        session.removeAttribute("descuentoCarrito");
        
        return "redirect:/checkout/exito";
    }
    
    @GetMapping("/exito")
    public String exito() {
        return "pago-exito";
    }
}
