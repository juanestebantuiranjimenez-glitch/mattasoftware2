package com.example.demo.controllers;

import com.example.demo.models.CarritoItem;
import com.example.demo.models.Producto;
import com.example.demo.models.Usuarios;
import com.example.demo.services.ProductoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    private ProductoServices productoService;

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
        
        double subtotal = carrito.stream().mapToDouble(item -> item.getPrecio() * item.getCantidad()).sum();
        Double descuento = (Double) session.getAttribute("descuentoCarrito");
        if (descuento == null) descuento = 0.0;
        
        model.addAttribute("itemsCarrito", carrito);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("descuento", descuento);
        model.addAttribute("total", subtotal - descuento);
        
        return "checkout";
    }

    @PostMapping("/procesar")
    public String procesarCheckout(HttpSession session, RedirectAttributes redirectAttributes) {
        Usuarios usuario = (Usuarios) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/usuarios/login";
        }
        
        @SuppressWarnings("unchecked")
        List<CarritoItem> carrito = (List<CarritoItem>) session.getAttribute("carrito");
        if (carrito == null || carrito.isEmpty()) {
            return "redirect:/carrito";
        }

        // Validación de stock
        for (CarritoItem item : carrito) {
            Producto p = productoService.buscarPorId(item.getProductoId());
            if (p == null || item.getCantidad() <= 0 || item.getCantidad() > p.getCantidad_disponible()) {
                redirectAttributes.addFlashAttribute("error", "Stock insuficiente para el producto: " + (p != null ? p.getNombre() : "Desconocido"));
                return "redirect:/checkout";
            }
        }

        // Descontar stock
        for (CarritoItem item : carrito) {
            Producto p = productoService.buscarPorId(item.getProductoId());
            p.setCantidad_disponible(p.getCantidad_disponible() - item.getCantidad());
            productoService.guardar(p);
        }
        
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
